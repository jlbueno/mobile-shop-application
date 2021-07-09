package com.example.mobileshopapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mobileshopapp.adapters.ShopListAdapter;
import com.example.mobileshopapp.models.Shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ShopListAdapter.ShopListClickListener {
    ArrayList<Shop> shopList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Shop List");

        shopList = new ArrayList<>();

        shopList.add(new Shop("Jollibee"));
        shopList.add(new Shop("McDonald\'s"));
        shopList.add(new Shop("KFC"));


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ShopListAdapter adapter = new ShopListAdapter(shopList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(Shop shop) {

    }
}