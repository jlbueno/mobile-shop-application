package com.example.mobileshopapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileshopapp.R;
import com.example.mobileshopapp.models.Shop;


import java.util.ArrayList;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ShopViewHolder> {

    private final ArrayList<Shop> shopList;
    private final ShopListClickListener shopListClickListener;

    public ShopListAdapter(ArrayList<Shop> shopList, ShopListClickListener shopListClickListener) {
        this.shopList = shopList;
        this.shopListClickListener = shopListClickListener;
    }

    static class ShopViewHolder extends RecyclerView.ViewHolder {
        TextView shopName;
        private final ImageView shopThumbnail;

        public ShopViewHolder(View view) {
            super(view);
            shopName = view.findViewById(R.id.shop_name);
            shopThumbnail = view.findViewById(R.id.shop_thumbnail);
        }
    }

    public interface ShopListClickListener {
        void onItemClick(Shop shop);
    }


    /**
     *  Return the ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recycler_view, parent, false);
        return new ShopViewHolder(view);
    }

    /**
     *  Put string, image, and listener to appropriate attribute.
     *  Essentially this connects the data to the views.
     *
     * @param holder   variable containing the current item
     * @param position  position of the current item relative to the recycler view
     */
    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        holder.shopName.setText(shopList.get(position).getName());
        Glide.with(holder.shopThumbnail).load(shopList.get(position).getThumbnail()).into(holder.shopThumbnail);

        holder.itemView.setOnClickListener(view -> shopListClickListener.onItemClick(shopList.get(position)));
    }

    /**
     *
     * @return  the number of items in the RecyclerView
     */
    @Override
    public int getItemCount() {
        return shopList.size();
    }
}
