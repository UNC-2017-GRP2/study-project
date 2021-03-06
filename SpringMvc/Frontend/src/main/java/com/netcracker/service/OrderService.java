package com.netcracker.service;

import com.netcracker.model.Address;
import com.netcracker.model.Item;
import com.netcracker.model.Order;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public interface OrderService {
    BigInteger totalOrder(ArrayList<Item> items);
    void checkout(BigInteger orderId, ArrayList<Item> items, String username, Address orderAddress, String inputPhone, long paymentType, Boolean isPaid, BigInteger changeFrom) throws SQLException;
    //List<Order> getAllFreeOrders();
    List<Order> getAllFreeOrders(Locale locale);
    //List<Order> getAllOrdersByUser(String username);
    Order getOrderById(BigInteger orderId);
    //List<Order> getAllOrdersByCourier(String username);
    List<Order> getAllOrdersByCourier(String username, Locale locale);
    //List<Order> getCompletedOrdersByCourier(String username);
    List<Order> getCompletedOrdersByCourier(String username, Locale locale);
    //List<Order> getNotCompletedOrdersByCourier(String username);
    List<Order> getNotCompletedOrdersByCourier(String username, Locale locale);
    List<Order> getNotCompletedOrdersByCourierId(BigInteger courierId, Locale locale);
    void changeOrderStatus(BigInteger orderId, long statusId);
    void setCourier(BigInteger orderId, String username);
    void setCourier(BigInteger orderId, BigInteger courierId);
    List<Order> getAllOrders(Locale locale);
    List<Order> getOrdersByUsername(String username, Locale locale);
    BigInteger getObjectId();
    void removeOrderById(BigInteger orderId);
    void updateOrderPaid(BigInteger orderId, int isPaid);
}