package com.example.mobileshopapp.models;

import java.io.Serializable;

public class ShopItem implements Serializable {
    private String name;
    private float price;

    public ShopItem(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }
}
