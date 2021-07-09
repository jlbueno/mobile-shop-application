package com.example.mobileshopapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.mobileshopapp.adapters.ShopListAdapter;
import com.example.mobileshopapp.models.Shop;
import com.example.mobileshopapp.models.ShopItem;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements ShopListAdapter.ShopListClickListener {
    ArrayList<Shop> shopList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setTitle("Shop List");
        }

        shopList = new ArrayList<>();

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
        startActivity(intent);
    }

    private void loadShops() {
        String json = loadJSONFromAsset(this);

        Gson gson = new Gson();
        Shop[] shops = gson.fromJson(json, Shop[].class);
        shopList = new ArrayList<>(Arrays.asList(shops));
    }

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.data);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}