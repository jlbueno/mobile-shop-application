package com.example.mobileshopapp.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Shop implements Serializable {
    private final String name;
    private String url;
    ArrayList<ShopItem> inventory;

    public Shop(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<ShopItem> getShopItems() {
        return inventory;
    }

    public String getThumbnail() {
        return url;
    }
}
