package com.example.mobileshopapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileshopapp.R;
import com.example.mobileshopapp.models.ShopItem;

import java.util.ArrayList;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder> {

    private ArrayList<ShopItem> inventory;
    private InventoryClickListener inventoryClickListener;

    public InventoryAdapter(ArrayList<ShopItem> inventory, InventoryClickListener inventoryClickListener) {
        this.inventory = inventory;
        this.inventoryClickListener = inventoryClickListener;
    }

    public void updateInventory(ArrayList<ShopItem> inventory) {
        this.inventory = inventory;
        notifyDataSetChanged();
    }

    static class InventoryViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemPrice;
        LinearLayout quantityControl;
        TextView currentQuantity;
        Button addToCart;
        Button incrementCount;
        Button decrementCount;
        // ImageView thumbnail;

        public InventoryViewHolder(View view) {
            super(view);
            itemName = view.findViewById(R.id.item_name);
            itemPrice = view.findViewById(R.id.item_price);
            addToCart = view.findViewById(R.id.add_to_cart);
            quantityControl = view.findViewById(R.id.item_quantity_controls);
            currentQuantity = view.findViewById(R.id.current_item_quantity);
            incrementCount = view.findViewById(R.id.increment_button);
            decrementCount = view.findViewById(R.id.decrement_button);
        }
    }

    public interface InventoryClickListener {
        public void addToCart(ShopItem item);

        public void updateCart(ShopItem item);

        public void removeFromCart(ShopItem item);
    }

    @NonNull
    @Override
    public InventoryAdapter.InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_recycler_view, parent, false);
        return new InventoryAdapter.InventoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull InventoryViewHolder holder, int position) {
        holder.itemName.setText(inventory.get(position).getName());
        holder.itemPrice.setText(String.format("₱ %.2f", inventory.get(position).getPrice()));

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopItem item = inventory.get(position);
                item.setNumInCart(1);
                inventoryClickListener.addToCart(item);

                holder.quantityControl.setVisibility(View.VISIBLE);
                holder.addToCart.setVisibility(View.GONE);
                holder.currentQuantity.setText(Integer.toString(item.getNumInCart()));
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

                    holder.currentQuantity.setText(Integer.toString(item.getNumInCart()));
                }
            }
        });

        holder.incrementCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopItem item = inventory.get(position);
                item.setNumInCart(item.getNumInCart() + 1);
                inventoryClickListener.updateCart(item);

                holder.currentQuantity.setText(Integer.toString(item.getNumInCart()));
            }
        });


    }

    @Override
    public int getItemCount() {
        return inventory.size();
    }

}
