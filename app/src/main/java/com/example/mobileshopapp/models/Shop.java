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
}
