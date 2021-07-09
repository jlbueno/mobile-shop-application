package com.example.mobileshopapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mobileshopapp.adapters.InventoryAdapter;
import com.example.mobileshopapp.models.Shop;
import com.example.mobileshopapp.models.ShopItem;

import java.util.ArrayList;

public class ShopInventory extends AppCompatActivity implements InventoryAdapter.InventoryClickListener {

    private InventoryAdapter inventoryAdapter;
    private ArrayList<ShopItem> inventory;
    private Button viewCartButton;
    private ArrayList<ShopItem> userCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_inventory);

        Shop shop = (Shop) getIntent().getSerializableExtra("Shop");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(shop.getName());

        userCart = new ArrayList<>();

        inventory = shop.getItems();

        RecyclerView recyclerView = findViewById(R.id.shop_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        inventoryAdapter = new InventoryAdapter(inventory, this);
        recyclerView.setAdapter(inventoryAdapter);

        viewCartButton = findViewById(R.id.view_cart);
        viewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to cart
            }
        });
    }


    @Override
    public void addToCart(ShopItem item) {
        Log.d("Testing", String.format("Added %s to cart", item.getName()));
        userCart.add(item);
        Log.d("Testing", String.format("Successfully added %s to cart", item.getName()));

        for (ShopItem cartItem : userCart) {
            Log.d("Testing", String.format("%s: %d", cartItem.getName(), cartItem.getNumInCart()));
        }
    }

    @Override
    public void updateCart(ShopItem item) {
        if (userCart.contains(item)) {
            Integer index = userCart.indexOf(item);
            userCart.set(index, item);

            for (ShopItem cartItem : userCart) {
                Log.d("Testing", String.format("%s: %d", cartItem.getName(), cartItem.getNumInCart()));
            }
        }
    }

    @Override
    public void removeFromCart(ShopItem item) {
        Log.d("Testing", String.format("Removed %s from cart", item.getName()));
        userCart.remove(item);
        for (ShopItem cartItem : userCart) {
            Log.d("Testing", String.format("%s: %d", cartItem.getName(), cartItem.getNumInCart()));
        }
    }
}