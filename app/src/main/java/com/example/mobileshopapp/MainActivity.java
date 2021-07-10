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
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ShopListAdapter.ShopListClickListener {

    private final int REQUEST_CODE = 1;

    ArrayList<Shop> shopList;
    private ArrayList<ShopItem> userCart;
    private float totalItemPrice;

    private ArrayList<String> deliveryInfo;

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

    @Override
    public void onItemClick(Shop shop) {
        Intent intent = new Intent(this, ShopInventory.class);
        intent.putExtra("Shop", shop);
        for(ShopItem item:userCart) {
            System.out.printf("----------------------%s: %d------------------------\n", item.getName(), item.getNumInCart());
        }
        intent.putExtra("userCart", userCart);
        intent.putExtra("totalItemPrice", totalItemPrice);
        intent.putExtra("deliveryInfo", deliveryInfo);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void loadShops() {
        String json = loadJSONFromAsset();

        Gson gson = new Gson();
        Shop[] shops = gson.fromJson(json, Shop[].class);
        shopList = new ArrayList<>(Arrays.asList(shops));
    }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("userCart")) {
                userCart = (ArrayList<ShopItem>) (data.getSerializableExtra("userCart"));
                for(ShopItem item:userCart) {
                    System.out.printf("%s: %d\n", item.getName(), item.getNumInCart());
                }
                totalItemPrice = data.getFloatExtra("totalItemPrice", 0);
                if (data.hasExtra("deliveryInfo")) {
                    deliveryInfo = data.getStringArrayListExtra("deliveryInfo");
                }
            }
        }
    }
}