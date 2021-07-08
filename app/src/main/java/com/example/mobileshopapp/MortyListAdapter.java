package com.example.mobileshopapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class MortyListAdapter extends RecyclerView.Adapter<MortyListAdapter.MortyViewHolder> {

    class MortyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final MortyListAdapter mAdapter;
        public final TextView nameView;
        public final Button addToCart;
        public final Button removeFromCart;
        private int itemCount;


        public MortyViewHolder(View itemView, MortyListAdapter adapter) {
            super(itemView);
            nameView = itemView.findViewById(R.id.shop_item_name);
            addToCart  = itemView.findViewById(R.id.add_to_cart);
            removeFromCart = itemView.findViewById(R.id.remove_from_cart);
            this.mAdapter = adapter;

            itemCount = 0;

            itemView.setOnClickListener(this);
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemCount = 1;
                    int position = getLayoutPosition();
                    Morty item = mortyList.get(position);
                    Log.d("Testing", String.format("%s added to cart", item.getName()));
                    addToCart.setVisibility(View.INVISIBLE);
                    removeFromCart.setVisibility(View.VISIBLE);
                    MainActivity.addItemToCart(MortyViewHolder.this);
                }
            });

            removeFromCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemCount = 0;
                    int position = getLayoutPosition();
                    Morty item = mortyList.get(position);
                    Log.d("Testing", String.format("%s removed from cart", item.getName()));
                    removeFromCart.setVisibility(View.INVISIBLE);
                    addToCart.setVisibility(View.VISIBLE);
                    MainActivity.removeItemFromCart(MortyViewHolder.this);
                }
            });
        }

        public String getItemName() {
            int position = getLayoutPosition();
            Morty element = mortyList.get(position);
            return element.getName();
        }

        public Integer getItemCount() {
            return itemCount;
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            Morty element = mortyList.get(position);

            System.out.println(element.getName());

        }
    }

    private final LinkedList<Morty> mortyList;

    private LayoutInflater inflater;

    public MortyListAdapter(Context context, LinkedList<Morty> mortyList) {
        inflater = LayoutInflater.from(context);
        this.mortyList = mortyList;
    }

    @NonNull
    @Override
    public MortyListAdapter.MortyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.shop_item, parent, false);
        return new MortyViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MortyListAdapter.MortyViewHolder holder, int position) {
        Morty currentItem = mortyList.get(position);
        holder.nameView.setText(String.format("%s: %d", currentItem.getName(), currentItem.getPrice()));
    }

    @Override
    public int getItemCount() {
        return mortyList.size();
    }
}
