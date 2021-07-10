package com.example.mobileshopapp.adapters;

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

import java.util.ArrayList;
import java.util.Locale;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder> {

    private final ArrayList<ShopItem> inventory;
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

        holder.itemName.setText(inventory.get(position).getName());
        holder.itemPrice.setText(String.format(Locale.ENGLISH, "₱ %.2f", inventory.get(position).getPrice()));

        Glide.with(holder.thumbnail).load(item.getThumbnail()).into(holder.thumbnail);

        if(item.getNumInCart()>0) {
            holder.quantityControl.setVisibility(View.VISIBLE);
            holder.addToCart.setVisibility(View.GONE);
            holder.currentQuantity.setText(String.format(Locale.ENGLISH, "%d", item.getNumInCart()));
        }

        holder.addToCart.setOnClickListener(view -> {
            ShopItem item1 = inventory.get(position);
            item1.setNumInCart(1);
            inventoryClickListener.addToCart(item1);

            holder.quantityControl.setVisibility(View.VISIBLE);
            holder.addToCart.setVisibility(View.GONE);
            holder.currentQuantity.setText(String.format(Locale.ENGLISH, "%d", item1.getNumInCart()));
        });

        holder.decrementCount.setOnClickListener(view -> {
            ShopItem item12 = inventory.get(position);
            int numInCart = item12.getNumInCart();
            if (numInCart == 1) {
                item12.setNumInCart(0);
                inventoryClickListener.removeFromCart(item12);

                holder.quantityControl.setVisibility(View.GONE);
                holder.addToCart.setVisibility(View.VISIBLE);
            } else {
                item12.setNumInCart(numInCart - 1);
                inventoryClickListener.updateCart(item12);

                holder.currentQuantity.setText(String.format(Locale.ENGLISH, "%d", item12.getNumInCart()));
            }
        });

        holder.incrementCount.setOnClickListener(view -> {
            ShopItem item13 = inventory.get(position);
            item13.setNumInCart(item13.getNumInCart() + 1);
            inventoryClickListener.updateCart(item13);

            holder.currentQuantity.setText(String.format(Locale.ENGLISH, "%d", item13.getNumInCart()));
        });


    }

    @Override
    public int getItemCount() {
        return inventory.size();
    }

}
