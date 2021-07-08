package com.example.mobileshopapp;

import java.io.Serializable;

public class Morty implements Serializable {
    private String name;
    private int price;

    Morty(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

}
