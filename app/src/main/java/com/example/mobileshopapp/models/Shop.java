package com.example.mobileshopapp.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Shop implements Serializable {
    private final String name;

    public Shop(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public ArrayList<ShopItem> getItems() {
        ArrayList<ShopItem> items = new ArrayList<>();

        items.add(new ShopItem("Spaghetti", 10));
        items.add(new ShopItem("Sundae", 5));
        items.add(new ShopItem("Bread", 3));
        items.add(new ShopItem("Chicken", 20));
        items.add(new ShopItem("Spaghetti1", 10));
        items.add(new ShopItem("Sundae1", 5));
        items.add(new ShopItem("Bread1", 3));
        items.add(new ShopItem("Chicken1", 20));
        items.add(new ShopItem("Spaghetti2", 10));
        items.add(new ShopItem("Sundae2", 5));
        items.add(new ShopItem("Bread2", 3));
        items.add(new ShopItem("Chicken2", 20));
        items.add(new ShopItem("Spaghetti3", 10));
        items.add(new ShopItem("Sundae3", 5));
        items.add(new ShopItem("Bread3", 3));
        items.add(new ShopItem("Chicken3", 20));
        return items;
    }
}
