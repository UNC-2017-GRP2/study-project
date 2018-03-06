package com.netcracker.controller;

import com.netcracker.model.Item;
import com.netcracker.model.User;
import com.netcracker.service.ItemService;
import com.netcracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class TestController
{
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;

    @RequestMapping(value = {"/test"}, method = RequestMethod.GET)
        public ModelAndView homeValue(ModelAndView model, @RequestParam(value = "value", required = false) String value, Locale
        locale, Principal principal, HttpSession httpSession) throws IOException {
            if (principal != null){
                User user  = userService.getByUsername(principal.getName());
                if (httpSession.getAttribute("username") == null){
                    httpSession.setAttribute("username",principal.getName());
                }
                if (!user.getRole().equals("ROLE_COURIER")){
                    if (httpSession.getAttribute("basketItems") == null){
                        httpSession.setAttribute("basketItems", new ArrayList<Item>());
                    }
                }
                if (httpSession.getAttribute("userAddresses") == null){
                    httpSession.setAttribute("userAddresses", user.getAddresses());
                }
                if (httpSession.getAttribute("userPhone") == null){
                    httpSession.setAttribute("userPhone", user.getPhoneNumber());
                }
                if (user.getRole().equals("ROLE_COURIER")) {
                    model.setViewName("redirect:/my-orders");
                    return model;
                }
            }

            List<Item> currentItems = itemService.getItemsByCategory(value, locale);
            if(value == null){
                model.addObject("value", "Pizza");
            }else{
                model.addObject("value", value);
            }
            if (currentItems == null){
                currentItems = itemService.getItemsByCategory("Pizza", locale);

            }
            model.addObject("items", currentItems);

            //model.addObject("notification", null);
            model.setViewName("home");
            return model;
        }
    }

