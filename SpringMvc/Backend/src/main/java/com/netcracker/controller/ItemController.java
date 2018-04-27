package com.netcracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.netcracker.model.Item;
import com.netcracker.service.ItemService;

import java.math.BigInteger;
import java.util.List;
import java.util.Locale;

@RestController
//@SessionAttributes(value = {"username", "basketItems"})
@RequestMapping("/{locale}/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Item> findAll(@PathVariable String locale){
        Locale thisLocale = new Locale(locale);
        return itemService.getAllItems(thisLocale);
    }



    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Item findItem(
            @PathVariable BigInteger id,
            @PathVariable String locale
    ){
        Locale thisLocale = new Locale(locale);
        return itemService.getItemById(id,thisLocale);
    }
//    TODO: реализовать добавление, изменение и замену, если это будет необходимо



    @RequestMapping(value = "/category/{category}", method = RequestMethod.GET)
    public List<Item> getItemsByCategory (
            @PathVariable String category,
            @PathVariable String locale
    ){
        Locale thisLocale = new Locale(locale);
        return itemService.getItemsByCategory(category, thisLocale);
    }

/*
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Long create(@RequestBody Item resource) {
        Preconditions.checkNotNull(resource);
        return ervice.create(resource);
    }*/
/*

    @RequestMapping(value = { "/items"}, method = RequestMethod.GET)
    public ModelAndView itemsPage(@ModelAttribute String username) throws IOException {
        ModelAndView model = new ModelAndView("session_username");
        model.addObject(username);
        model.addObject("message","Заказ для пользователя ");
        model.addObject("add","Добавить в корзину");
        model.addObject("rub","\u20BD");
        model.setViewName("items");

        List<Item> allItems = itemService.getAllItems();
        if (allItems != null && allItems.size()!=0){
            model.addObject("items",allItems);
        }
        return model;
    }
    @RequestMapping(value = { "/items/{id}"}, method = RequestMethod.POST)
    public String addItemInBasket(@PathVariable BigInteger id, @ModelAttribute ArrayList<Item> basketItems, HttpSession httpSession) throws IOException {
        Item item = itemService.getItemById(id);
        if (item != null){
            if (httpSession.getAttribute("basketItems") == null){
                httpSession.setAttribute("basketItems", new ArrayList<Item>());
            }
            List<Item> curItems = (List<Item>) httpSession.getAttribute("basketItems");
            curItems.add(item);
            httpSession.setAttribute("basketItems", curItems);

        }
        return "redirect:/items";
    }
*/


}
