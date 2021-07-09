package com.example.mobileshopapp.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileshopapp.R;
import com.example.mobileshopapp.models.ShopItem;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder> {

    private ArrayList<ShopItem> inventory;
    private final InventoryClickListener inventoryClickListener;

    public InventoryAdapter(ArrayList<ShopItem> inventory, InventoryClickListener inventoryClickListener) {
        this.inventory = inventory;
        this.inventoryClickListener = inventoryClickListener;
    }

    static class InventoryViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemPrice;
        LinearLayout quantityControl;
        TextView currentQuantity;
        Button addToCart;
        ImageView incrementCount;
        ImageView decrementCount;
        ImageView thumbnail;

        public InventoryViewHolder(View view) {
            super(view);
            itemName = view.findViewById(R.id.item_name);
            itemPrice = view.findViewById(R.id.item_price);
            addToCart = view.findViewById(R.id.add_to_cart);
            quantityControl = view.findViewById(R.id.item_quantity_controls);
            currentQuantity = view.findViewById(R.id.current_item_quantity);
            incrementCount = view.findViewById(R.id.increment_button);
            decrementCount = view.findViewById(R.id.decrement_button);
            thumbnail = view.findViewById(R.id.item_thumbnail);
        }
    }

    public interface InventoryClickListener {
        void addToCart(ShopItem item);

        void updateCart(ShopItem item);

        void removeFromCart(ShopItem item);
    }

    @NonNull
    @Override
    public InventoryAdapter.InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_recycler_view, parent, false);
        return new InventoryAdapter.InventoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull InventoryViewHolder holder, int position) {
        ShopItem item = inventory.get(position);

        System.out.println(String.format("%s: %s", item.getName(), item.getThumbnail()));

        holder.itemName.setText(inventory.get(position).getName());
        holder.itemPrice.setText(String.format(Locale.ENGLISH, "₱ %.2f", inventory.get(position).getPrice()));

        Glide.with(holder.thumbnail).load(item.getThumbnail()).into(holder.thumbnail);
//        try {
//            URL url = new URL(inventory.get(position).getThumbnail());
//            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            holder.thumbnail.setImageBitmap(bmp);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopItem item = inventory.get(position);
                item.setNumInCart(1);
                inventoryClickListener.addToCart(item);

                holder.quantityControl.setVisibility(View.VISIBLE);
                holder.addToCart.setVisibility(View.GONE);
                holder.currentQuantity.setText(String.format(Locale.ENGLISH, "%d", item.getNumInCart()));
            }
        });

        holder.decrementCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopItem item = inventory.get(position);
                int numInCart = item.getNumInCart();
                if (numInCart == 1) {
                    // Remove item from cart.
                    item.setNumInCart(0);
                    inventoryClickListener.removeFromCart(item);

                    holder.quantityControl.setVisibility(View.GONE);
                    holder.addToCart.setVisibility(View.VISIBLE);
                } else {
                    // Decrement count
                    item.setNumInCart(numInCart - 1);
                    inventoryClickListener.updateCart(item);

                    holder.currentQuantity.setText(String.format(Locale.ENGLISH, "%d", item.getNumInCart()));
                }
            }
        });

        holder.incrementCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopItem item = inventory.get(position);
                item.setNumInCart(item.getNumInCart() + 1);
                inventoryClickListener.updateCart(item);

                holder.currentQuantity.setText(String.format(Locale.ENGLISH, "%d", item.getNumInCart()));
            }
        });


    }

    @Override
    public int getItemCount() {
        return inventory.size();
    }

}
