package com.example.mobileshopapp;

public class Morty {
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

}
