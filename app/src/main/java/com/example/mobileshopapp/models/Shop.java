package com.example.mobileshopapp.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Shop implements Serializable {
    private String name;
    private ArrayList<ShopItem> items;

    public Shop(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public ArrayList<ShopItem> getItems() {
        items = new ArrayList<>();

        System.out.println("Got the items");

        items.add(new ShopItem("Spaghetti", 10));
        items.add(new ShopItem("Sundae", 5));
        items.add(new ShopItem("Bread", 3));
        items.add(new ShopItem("Chicken", 20));
        return items;
    }
}
