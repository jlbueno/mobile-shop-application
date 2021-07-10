package com.example.mobileshopapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileshopapp.adapters.InventoryAdapter;
import com.example.mobileshopapp.models.Shop;
import com.example.mobileshopapp.models.ShopItem;

import java.util.ArrayList;
import java.util.Locale;

public class ShopInventory extends AppCompatActivity implements InventoryAdapter.InventoryClickListener {

    private final int REQUEST_CODE = 1;

    private ArrayList<ShopItem> userCart;
    ArrayList<ShopItem> inventory;
    private float totalItemPrice;
    private TextView subtotal;

    private ArrayList<String> deliveryInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_inventory);

        Intent intent = getIntent();

        Shop shop = (Shop) intent.getSerializableExtra("Shop");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(shop.getName());
            actionBar.show();
        }

        deliveryInfo = new ArrayList();
        if (intent.hasExtra("deliveryInfo")) {
            deliveryInfo = intent.getStringArrayListExtra("deliveryInfo");
        }

        userCart = new ArrayList<>();
        inventory = shop.getShopItems();
        totalItemPrice = 0;
        subtotal = findViewById(R.id.shop_subtotal);

        subtotal.setText(String.format(Locale.ENGLISH, "₱ %.2f", totalItemPrice));

        RecyclerView recyclerView = findViewById(R.id.shop_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        InventoryAdapter inventoryAdapter = new InventoryAdapter(inventory, this);
        recyclerView.setAdapter(inventoryAdapter);

        Button viewCartButton = findViewById(R.id.view_cart);
        viewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userCart.size() <= 0) {
                    Toast toast = Toast.makeText(ShopInventory.this, "Your cart is empty", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), Cart.class);
                    intent.putExtra("userCart", userCart);
                    intent.putExtra("totalItemPrice", totalItemPrice);
                    intent.putExtra("deliveryInfo", deliveryInfo);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });
    }


    @Override
    public void addToCart(ShopItem item) {
        Log.d("Testing", String.format("Added %s to cart", item.getName()));
        userCart.add(item);
        totalItemPrice = totalItemPrice + item.getPrice();
        subtotal.setText(String.format(Locale.ENGLISH, "₱ %.2f", totalItemPrice));
        Log.d("Testing", String.format("Successfully added %s to cart", item.getName()));

        for (ShopItem cartItem : userCart) {
            Log.d("Testing", String.format("%s: %d", cartItem.getName(), cartItem.getNumInCart()));
        }
    }

    @Override
    public void updateCart(ShopItem item) {
        if (userCart.contains(item)) {
            int index = userCart.indexOf(item);
            userCart.set(index, item);

            totalItemPrice = 0;
            for (ShopItem cartItem : userCart) {
                Log.d("Testing", String.format("%s: %d", cartItem.getName(), cartItem.getNumInCart()));
                totalItemPrice = totalItemPrice + (cartItem.getPrice() * cartItem.getNumInCart());
            }
            subtotal.setText(String.format(Locale.ENGLISH, "₱ %.2f", totalItemPrice));
        }
    }

    @Override
    public void removeFromCart(ShopItem item) {
        Log.d("Testing", String.format("Removed %s from cart", item.getName()));
        userCart.remove(item);
        totalItemPrice = totalItemPrice - item.getPrice();
        subtotal.setText(String.format(Locale.ENGLISH, "₱ %.2f", totalItemPrice));
        for (ShopItem cartItem : userCart) {
            Log.d("Testing", String.format("%s: %d", cartItem.getName(), cartItem.getNumInCart()));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent data = new Intent();
                data.putExtra("userCart", userCart);
                data.putExtra("totalItemPrice", totalItemPrice);
                data.putExtra("deliveryInfo", deliveryInfo);
                setResult(RESULT_OK, data);
                finish();
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("userCart")) {
                System.out.println("---------------UPDATING CART--------------------");
                userCart = (ArrayList<ShopItem>) (data.getSerializableExtra("userCart"));
                totalItemPrice = data.getFloatExtra("totalItemPrice", 0);
                subtotal.setText(String.format(Locale.ENGLISH, "₱ %.2f", totalItemPrice));
                if (data.hasExtra("deliveryInfo")) {
                    deliveryInfo = data.getStringArrayListExtra("deliveryInfo");
                }
            }
        }
    }
}