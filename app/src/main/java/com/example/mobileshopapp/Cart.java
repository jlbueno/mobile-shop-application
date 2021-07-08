package com.example.mobileshopapp;

import java.util.HashMap;

public class Cart {

    private static Cart USERCART = null;
    private HashMap<Morty, Integer> cart = new HashMap<Morty, Integer>();

    private Cart() {
    }

    public static Cart getInstance() {
        if (USERCART == null) {
            USERCART = new Cart();
        }
        return USERCART;
    }

}
