package com.example.mobileshopapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.mobileshopapp.adapters.ShopListAdapter;
import com.example.mobileshopapp.models.Shop;
import com.example.mobileshopapp.models.ShopItem;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements ShopListAdapter.ShopListClickListener {

    private final int REQUEST_CODE = 1;

    ArrayList<Shop> shopList;

    private ArrayList<ShopItem> userCart;
    private ArrayList<String> deliveryInfo;
    private float totalItemPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setTitle("Shop List");
        }

        userCart = new ArrayList<>();
        shopList = new ArrayList<>();
        deliveryInfo = new ArrayList<>();

        loadShops();
        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ShopListAdapter adapter = new ShopListAdapter(shopList, this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Creates an intent and puts the extras then starts the ShopInventory activity
     *
     * @param shop The shop chosen by the user.
     */
    @Override
    public void onItemClick(Shop shop) {
        Intent intent = new Intent(this, ShopInventory.class);
        intent.putExtra("Shop", shop);
        intent.putExtra("userCart", userCart);
        intent.putExtra("totalItemPrice", totalItemPrice);
        intent.putExtra("deliveryInfo", deliveryInfo);
        startActivityForResult(intent, REQUEST_CODE);
    }

    /**
     * Get the shop and the items in the inventory using Gson
     */
    private void loadShops() {
        String json = loadJSONFromAsset();

        Gson gson = new Gson();
        Shop[] shops = gson.fromJson(json, Shop[].class);
        shopList = new ArrayList<>(Arrays.asList(shops));
    }

    /**
     * Get a string representation of the data in the raw resource file at res > raw > data.json
     *
     * @return the json value parsed to its String equivalent
     */
    public String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = getResources().openRawResource(R.raw.data);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     * Retrieve the data from the ShopInventory Activity
     *
     * @param requestCode
     * @param resultCode
     * @param data        data from the ShopInventory containing the user's cart and total price
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            assert data != null;
            userCart = (ArrayList<ShopItem>) (data.getSerializableExtra("userCart"));
            totalItemPrice = data.getFloatExtra("totalItemPrice", 0);
            deliveryInfo = data.getStringArrayListExtra("deliveryInfo");
        }
    }
}