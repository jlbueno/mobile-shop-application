package com.example.mobileshopapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileshopapp.adapters.CartAdapter;
import com.example.mobileshopapp.models.ShopItem;

import java.util.ArrayList;
import java.util.Locale;

public class Cart extends AppCompatActivity implements CartAdapter.CartClickListener {

    private ArrayList<ShopItem> userCart;
    float totalItemPrice;
    TextView totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setTitle("Order Summary");
        }

        Intent intent = getIntent();
        userCart = (ArrayList<ShopItem>) intent.getSerializableExtra("userCart");
        totalItemPrice = intent.getFloatExtra("totalItemPrice", 0);

        RecyclerView recyclerView = findViewById(R.id.cart_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CartAdapter adapter = new CartAdapter(userCart, this);
        recyclerView.setAdapter(adapter);

        totalPrice = findViewById(R.id.total_price);
        totalPrice.setText(String.format(Locale.ENGLISH, "Total: ₱ %.2f", totalItemPrice));

        Button checkout = findViewById(R.id.checkout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if user has an item in their cart before checking out.
                if(userCart.size() > 0) {
                    Intent intent = new Intent(getApplicationContext(), DeliveryInformation.class);

                    // These extras can be removed because it will not be used in this case.
                    intent.putExtra("userCart", userCart);
                    intent.putExtra("totalItemPrice", totalItemPrice);

                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Your cart is empty", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    @Override
    public void updateCart(ShopItem item) {
        if (userCart.contains(item)) {
            int index = userCart.indexOf(item);
            userCart.set(index, item);
            updateTotalItemPrice();
        }
    }

    @Override
    public void removeFromCart(ShopItem item) {
        userCart.remove(item);
        updateTotalItemPrice();
    }

    private void updateTotalItemPrice() {
        totalItemPrice = 0;
        for (ShopItem item : userCart) {
            totalItemPrice = totalItemPrice + (item.getNumInCart() * item.getPrice());
        }

        totalPrice.setText(String.format(Locale.ENGLISH, "Total: ₱ %.2f", totalItemPrice));
    }
}