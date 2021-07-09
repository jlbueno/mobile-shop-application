package com.example.mobileshopapp.models;

import java.io.Serializable;

public class ShopItem implements Serializable {
    private String name;
    private float price;
    private int numInCart;

    public ShopItem(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Integer getNumInCart() {
        return numInCart;
    }

    public float getPrice() {
        return price;
    }

    public void setNumInCart(Integer numInCart) {
        this.numInCart = numInCart;
    }
}
