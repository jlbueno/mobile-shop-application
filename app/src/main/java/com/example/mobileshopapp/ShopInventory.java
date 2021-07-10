package com.example.mobileshopapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
    private ArrayList<ShopItem> inventory;
    private ArrayList<String> deliveryInfo;
    private float totalItemPrice;

    InventoryAdapter inventoryAdapter;
    private TextView subtotal;


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

        inventory = shop.getShopItems();
        initializeRecyclerView();
        getValuesFromIntent(intent);

        subtotal = findViewById(R.id.shop_subtotal);
        subtotal.setText(String.format(Locale.ENGLISH, "₱ %.2f", totalItemPrice));
        Button viewCartButton = findViewById(R.id.view_cart);

        viewCartButton.setOnClickListener(view -> {
            if (userCart.size() <= 0) {
                Toast toast = Toast.makeText(ShopInventory.this, "Your cart is empty", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Intent intent1 = new Intent(getApplicationContext(), Cart.class);
                intent1.putExtra("userCart", userCart);
                intent1.putExtra("totalItemPrice", totalItemPrice);
                intent1.putExtra("deliveryInfo", deliveryInfo);
                startActivityForResult(intent1, REQUEST_CODE);
            }
        });
    }

    /**
     *  Create an adapter and initialize the recycler view
     */
    private void initializeRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.shop_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        inventoryAdapter = new InventoryAdapter(inventory, this);
        recyclerView.setAdapter(inventoryAdapter);
    }

    /**
     *  Retrieve data from the MainActivity. Useful when the user moves between activities.
     *
     * @param intent    the intent containing the values from the main activity (Shop choosing screen)
     */
    private void getValuesFromIntent(Intent intent) {
        if (intent.hasExtra("deliveryInfo")) {
            deliveryInfo = intent.getStringArrayListExtra("deliveryInfo");
        } else {
            deliveryInfo = new ArrayList<>();
        }

        if(intent.hasExtra("totalItemPrice")) {
            totalItemPrice = intent.getFloatExtra("totalItemPrice", 0);
        } else {
            totalItemPrice = 0;
        }

        if(intent.hasExtra("userCart")) {
            userCart = (ArrayList<ShopItem>) intent.getSerializableExtra("userCart");
            updateItemViews(userCart, inventory);
        } else {
            userCart = new ArrayList<>();
        }

        if (intent.hasExtra("deliveryInfo")) {
            deliveryInfo = intent.getStringArrayListExtra("deliveryInfo");
        } else {
            deliveryInfo = new ArrayList<>();
        }

        if(intent.hasExtra("totalItemPrice")) {
            totalItemPrice = intent.getFloatExtra("totalItemPrice", 0);
        }

        if(intent.hasExtra("userCart")) {
            userCart = (ArrayList<ShopItem>) intent.getSerializableExtra("userCart");
            updateItemViews(userCart, inventory);
        } else {
            userCart = new ArrayList<>();
        }
    }

    /**
     *  Add the item to the cart
     *
     * @param item  the item that will be added
     */
    @Override
    public void addToCart(ShopItem item) {
        userCart.add(item);
        totalItemPrice = totalItemPrice + item.getPrice();
    }

    /**
     *  Update the item in the cart by replacing it with the newer item
     *
     * @param item  the item that will be updated
     */
    @Override
    public void updateCart(ShopItem item) {
        if (userCart.contains(item)) {
            int index = userCart.indexOf(item);
            userCart.set(index, item);

            totalItemPrice = 0;
            for (ShopItem cartItem : userCart) {
                totalItemPrice = totalItemPrice + (cartItem.getPrice() * cartItem.getNumInCart());
            }
            subtotal.setText(String.format(Locale.ENGLISH, "₱ %.2f", totalItemPrice));
        }
    }

    /**
     *  Remove item from the user's cart
     *
     * @param item  the item that will be removed from the userCart
     */
    @Override
    public void removeFromCart(ShopItem item) {
        userCart.remove(item);
        totalItemPrice = totalItemPrice - item.getPrice();
        subtotal.setText(String.format(Locale.ENGLISH, "₱ %.2f", totalItemPrice));
    }

    /**
     *  Update the view to be consistent with the current state of the userCart and inventory
     *
     * @param userCart  array list that contains the items that the user selected
     * @param inventory inventory of the shop
     */
    private void updateItemViews(ArrayList<ShopItem> userCart, ArrayList<ShopItem> inventory) {
        for (ShopItem inventoryItem : inventory) {
            for (ShopItem item : userCart) {
                if (item.getName().equals(inventoryItem.getName())) {
                    int index = inventory.indexOf(inventoryItem);
                    inventory.set(index, item);
                } else {
                    inventoryItem.setNumInCart(0);
                }
            }
        }
        inventoryAdapter.notifyDataSetChanged();
    }

    /**
     * Retrieve the data from the Cart Activity
     *
     * @param requestCode
     * @param resultCode
     * @param data          data from the Cart containing the user's cart and total price
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            assert data != null;
            if (data.hasExtra("userCart")) {
                userCart = (ArrayList<ShopItem>) (data.getSerializableExtra("userCart"));
                totalItemPrice = data.getFloatExtra("totalItemPrice", 0);
                subtotal.setText(String.format(Locale.ENGLISH, "₱ %.2f", totalItemPrice));
            }
            if (data.hasExtra("deliveryInfo")) {
                deliveryInfo = data.getStringArrayListExtra("deliveryInfo");
            }
        }
        updateItemViews(userCart, inventory);
    }

    /**
     *  Create Intent to pass data to the parent activity when using the Up button in Ancestral Navigation
     *
     * @param item  the item chosen by the user from the action bar
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
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
}