package com.example.mobileshopapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileshopapp.adapters.CartAdapter;
import com.example.mobileshopapp.models.ShopItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class Cart extends AppCompatActivity implements CartAdapter.CartClickListener {
    private final int REQUEST_CODE = 1;

    private ArrayList<ShopItem> userCart;
    float totalItemPrice;
    TextView totalPrice;

    private ArrayList<String> deliveryInfo;

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

        deliveryInfo = new ArrayList<>();
        if(intent.hasExtra("deliveryInfo")) {
            deliveryInfo = intent.getStringArrayListExtra("deliveryInfo");
        }

        RecyclerView recyclerView = findViewById(R.id.cart_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CartAdapter adapter = new CartAdapter(userCart, this);
        recyclerView.setAdapter(adapter);

        totalPrice = findViewById(R.id.total_price);
        totalPrice.setText(String.format(Locale.ENGLISH, "Total: ₱ %.2f", totalItemPrice));

        Button checkout = findViewById(R.id.checkout);
        checkout.setOnClickListener(v -> {
            if (userCart.size() > 0) {
                Intent intent1 = new Intent(getApplicationContext(), DeliveryInformation.class);

                intent1.putExtra("userCart", userCart);
                intent1.putExtra("totalItemPrice", totalItemPrice);
                if (deliveryInfo.size() > 0) {
                    intent1.putExtra("deliveryInfo", deliveryInfo);
                }

                startActivityForResult(intent1, REQUEST_CODE);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Your cart is empty", Toast.LENGTH_SHORT);
                toast.show();
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

    public void removeFromCart(ShopItem item) {
        userCart.remove(item);
        updateTotalItemPrice();
    }

    public void updateTotalItemPrice() {
        totalItemPrice = 0;
        for (ShopItem item : userCart) {
            totalItemPrice = totalItemPrice + (item.getNumInCart() * item.getPrice());
        }

        totalPrice.setText(String.format(Locale.ENGLISH, "Total: ₱ %.2f", totalItemPrice));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent data = new Intent();
                removeItemsWithZeroQuantity(userCart);
                data.putExtra("userCart", userCart);
                data.putExtra("totalItemPrice", totalItemPrice);
                data.putExtra("deliveryInfo", deliveryInfo);
                setResult(RESULT_OK, data);
                finish();
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }

    private void removeItemsWithZeroQuantity(ArrayList<ShopItem> userCart) {
        Iterator<ShopItem> iterator = userCart.iterator();
        while(iterator.hasNext()) {
            if(iterator.next().getNumInCart()==0) {
                iterator.remove();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {

            deliveryInfo.add(data.getStringExtra("fullName"));
            deliveryInfo.add(data.getStringExtra("contactNumber"));
            deliveryInfo.add(data.getStringExtra("address"));
            deliveryInfo.add(data.getStringExtra("baranggay"));
            deliveryInfo.add(data.getStringExtra("landmark"));
            deliveryInfo.add(data.getStringExtra("notes"));
        }
    }
}