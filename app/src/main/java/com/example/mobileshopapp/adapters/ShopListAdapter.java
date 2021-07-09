package com.example.mobileshopapp.adapters;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileshopapp.R;
import com.example.mobileshopapp.models.Shop;
import com.example.mobileshopapp.models.ShopItem;


import java.util.ArrayList;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ShopViewHolder> {

    private ArrayList<Shop> shopList;
    private ShopListClickListener shopListClickListener;

    public ShopListAdapter(ArrayList<Shop> shopList, ShopListClickListener shopListClickListener) {
        this.shopList = shopList;
        this.shopListClickListener = shopListClickListener;
    }

    public void updateShopList(ArrayList<Shop> shopList) {
        this.shopList = shopList;
        notifyDataSetChanged();
    }

    static class ShopViewHolder extends RecyclerView.ViewHolder {
        TextView shopName;
//        ImageView thumbnail;

        public ShopViewHolder(View view) {
            super(view);
            shopName = view.findViewById(R.id.shop_name);
        }
    }

    public interface ShopListClickListener {
        public void onItemClick(Shop shop);
    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent, false);
        return new ShopViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
//        Shop currentShop = shopList.get(position);
        holder.shopName.setText(shopList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopListClickListener.onItemClick(shopList.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        return shopList.size();
    }
}
