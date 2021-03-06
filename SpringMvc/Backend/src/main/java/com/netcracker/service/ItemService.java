package com.netcracker.service;

import com.netcracker.model.Item;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

public interface ItemService {
//    List<Item> getAllItems();
//    Item getItemById(BigInteger itemId);
//    List<Item> getItemsByCategory (String category);
    List<Item> getAllItems(Locale locale);
    Item getItemById(BigInteger itemId, Locale locale);
    List<Item> getItemsByCategory (String category, Locale locale);
    void removeItemById(BigInteger itemId) throws SQLException;
    List<String>getAllCategories();
    void saveItem(Item item, String nameRu, String nameUk, String descriptionRu, String descriptionUk);
    void updateItem(Item item, String nameRu, String nameUk, String descriptionRu, String descriptionUk);
}
