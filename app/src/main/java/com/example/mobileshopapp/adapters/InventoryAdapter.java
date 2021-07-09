package com.example.mobileshopapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileshopapp.R;
import com.example.mobileshopapp.models.Shop;
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
        // ImageView thumbnail;

        public InventoryViewHolder(View view) {
            super(view);
            itemName = view.findViewById(R.id.item_name);
            itemPrice = view.findViewById(R.id.item_price);
        }
    }

    public interface InventoryClickListener{
        public void addToCart(ShopItem item);
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

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                inventoryClickListener.addToCart(inventory.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return inventory.size();
    }

}
