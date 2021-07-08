package com.example.mobileshopapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class MortyListAdapter extends RecyclerView.Adapter<MortyListAdapter.MortyViewHolder> {

    class MortyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView nameView;
        final MortyListAdapter mAdapter;

        public MortyViewHolder(View itemView, MortyListAdapter adapter) {
            super(itemView);
            nameView = itemView.findViewById(R.id.shop_item);
            this.mAdapter = adapter;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            Morty element = mortyList.get(position);
            element.setName(String.format("Clicked! Morty %d", position));
            mortyList.set(position, element);
            mAdapter.notifyDataSetChanged();

            Context context = v.getContext();

            Intent intent = new Intent(context, MortyDetail.class);
            intent.putExtra("mortyObject", element);
            context.startActivity(intent);
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
