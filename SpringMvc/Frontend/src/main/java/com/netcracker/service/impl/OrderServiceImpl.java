package com.netcracker.service.impl;

import com.netcracker.config.Constant;
import com.netcracker.model.Address;
import com.netcracker.model.Item;
import com.netcracker.model.Order;
import com.netcracker.service.OrderService;
import com.netcracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class OrderServiceImpl implements OrderService {

    final String ORDER_BASE_URL = Constant.BASE_URL_REST + "/orders";
    @Autowired UserService userService;

    @Override
    public BigInteger totalOrder(ArrayList<Item> items) {

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<List<Item>> request = new HttpEntity<>(items);

        ResponseEntity<BigInteger> itemResponse =
                restTemplate.exchange(ORDER_BASE_URL+"/total",
                        HttpMethod.POST, request, new ParameterizedTypeReference<BigInteger>() {
                        });
        BigInteger result = itemResponse.getBody();

        return result;
    }


    @Override
    public List<Order> getOrdersByUsername(String username, Locale locale) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Order>> itemResponse =
                restTemplate.exchange(ORDER_BASE_URL+"/locale/"+ locale.getLanguage() +"/user/" + username + "/",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Order>>() {
                        });
        List<Order> result = itemResponse.getBody();

        return result;
    }

    @Override
    public void removeOrderById(BigInteger orderId) {
        RestTemplate restTemplate = new RestTemplate();

        Object itemResponse =
                restTemplate.exchange(ORDER_BASE_URL+"/" + orderId + "/",
                        HttpMethod.DELETE, null, new ParameterizedTypeReference<Object>() {
                        });
    }

    /*
    @Override
    public List<Order> getAllFreeOrders() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Order>> itemResponse =
                restTemplate.exchange(ORDER_BASE_URL+"/orders/",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Order>>() {
                        });
        List<Order> result = itemResponse.getBody();

        return result;
    }

  /*  @Override
    public List<Order> getAllOrdersByUser(String username) {
        BigInteger userId = userRepository.getUserByUsername(username).getUserId();
        List<Order> allOrders = orderRepository.getAllOrders();
        List<Order> allOrdersByUser = new ArrayList<>();
        for (Order newOrder : allOrders) {
            if (newOrder.getUserId().equals(userId)) {
                allOrdersByUser.add(newOrder);
            }
        }
        return allOrdersByUser;
    }*/

    @Override
    public BigInteger getObjectId(){
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<BigInteger> itemResponse =
                restTemplate.exchange(ORDER_BASE_URL+"/object/id",
                        HttpMethod.GET, null, new ParameterizedTypeReference<BigInteger>() {
                        });
        BigInteger result = itemResponse.getBody();
        return result;
    }


    @Override
    public void checkout(BigInteger orderId, ArrayList<Item> items, String username, Address orderAddress, String inputPhone, long paymentType, Boolean isPaid) throws SQLException {
        Order order = new Order(orderId, userService.getByUsername(username).getUserId(), totalOrder(items), null, orderAddress, inputPhone, items, null, String.valueOf(paymentType), isPaid);
        RestTemplate restTemplate = new RestTemplate();

        /*
        HttpHeaders headers=new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<?> request = new HttpEntity<Object>(body,headers);
/*
        OrderCheckout order = new OrderCheckout(items,username,orderAddress,inputPhone);
        HttpEntity<OrderCheckout> request = new HttpEntity<>(order);


        //TODO: разобраться, что именно отправляется и почему это не принимается сервером
        restTemplate.exchange(ORDER_BASE_URL+"/checkout",
                HttpMethod.POST, request, Void.class );
        */
        HttpEntity<Order> request= new HttpEntity<>(order);
        /*
        RequestEntity<Order> request = RequestEntity
                .post(URI.create(ORDER_BASE_URL+"/checkout"))
                .accept(MediaType.APPLICATION_JSON)
                .body(order);
                */
        restTemplate.exchange(ORDER_BASE_URL+"/checkout/",
                HttpMethod.POST,request, Object.class);
    }

    @Override
    public List<Order> getAllOrders(Locale locale) {RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Order>> itemResponse =
                restTemplate.exchange(ORDER_BASE_URL+"/locale/"+locale.getLanguage() + "/",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Order>>() {
                        });
        List<Order> result = itemResponse.getBody();

        return result;
    }
    //public List<Order> getAllOrders() {
    //    return orderRepository.getAllOrders();
    //}


    @Override
    //public List<Order> getAllFreeOrders() {
    public List<Order> getAllFreeOrders(Locale locale) {
        List<Order> allOrders = getAllOrders(locale);
        List<Order> allFreeOrders = new ArrayList<>();
        for (Order newOrder : allOrders) {
            if (newOrder.getStatus().equals("Created") || newOrder.getStatus().equals("Without courier")) {
                allFreeOrders.add(newOrder);
            }
        }

        return allFreeOrders;
    }

    @Override
    //public List<Order> getAllOrdersByCourier(String username) {
    public List<Order> getAllOrdersByCourier(String username, Locale locale) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Order>> itemResponse =
                restTemplate.exchange(ORDER_BASE_URL+"/courier/" + username + "/",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Order>>() {
                        });
        List<Order> result = itemResponse.getBody();

        return result;
    }

    @Override
    //public List<Order> getCompletedOrdersByCourier(String username) {
    public List<Order> getCompletedOrdersByCourier(String username, Locale locale) {
        BigInteger userId = userService.getByUsername(username).getUserId();
        List<Order> allOrders = getAllOrders(locale);
        List<Order> completedOrders = new ArrayList<>();
        for (Order newOrder : allOrders) {
            if (newOrder.getCourierId().equals(userId) && newOrder.getStatus().equals("Delivered") || newOrder.getStatus().equals("Not delivered") || newOrder.getStatus().equals("Cancelled")) {
                completedOrders.add(newOrder);
            }
        }
        return completedOrders;
    }

    @Override
    //public List<Order> getNotCompletedOrdersByCourier(String username) {
    public List<Order> getNotCompletedOrdersByCourier(String username, Locale locale) {
        BigInteger userId = userService.getByUsername(username).getUserId();
        List<Order> allOrders = getAllOrders(locale);
        List<Order> withCourier = new ArrayList<>();
        for (Order newOrder : allOrders) {
            if (newOrder.getCourierId().equals(userId) && newOrder.getStatus().equals("Linked with courier")) {
                withCourier.add(newOrder);
            }
        }
        return withCourier;
    }


  /*  @Override
    public List<Order> getAllOrdersByUser(String username) {
        BigInteger userId = userRepository.getUserByUsername(username).getUserId();
        List<Order> allOrders = orderRepository.getAllOrders();
        List<Order> allOrdersByUser = new ArrayList<>();
        for (Order newOrder : allOrders) {
            if (newOrder.getUserId().equals(userId)) {
                allOrdersByUser.add(newOrder);
            }
        }
        return allOrdersByUser;
    }*/

    @Override
    public Order getOrderById(BigInteger orderId) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Order> itemResponse =
                restTemplate.exchange(ORDER_BASE_URL+"/" + orderId + "/",
                        HttpMethod.GET, null, new ParameterizedTypeReference<Order>() {
                        });
        Order result = itemResponse.getBody();

        return result;
    }

    @Override
    public void changeOrderStatus(BigInteger orderId, long statusId) {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.exchange(ORDER_BASE_URL+"/" + orderId + "/status/" + statusId+ "/",
                HttpMethod.POST, null, new ParameterizedTypeReference<List<Order>>() {
                });
    }

    @Override
    public void setCourier(BigInteger orderId, String username) {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.exchange(ORDER_BASE_URL+"/" + orderId + "/courier/" + username + "/" ,
                HttpMethod.POST, null, new ParameterizedTypeReference<List<Order>>() {
                });
    }
}