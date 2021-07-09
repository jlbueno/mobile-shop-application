package com.example.mobileshopapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileshopapp.R;
import com.example.mobileshopapp.models.ShopItem;

import java.util.ArrayList;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartItemViewHolder> {

    private ArrayList<ShopItem> userCart;
    private final CartClickListener cartClickListener;

    public CartAdapter(ArrayList<ShopItem> userCart, CartClickListener cartClickListener) {
        this.userCart = userCart;
        this.cartClickListener = cartClickListener;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_recycler_view, parent, false);
        return new CartAdapter.CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartItemViewHolder holder, int position) {
        ShopItem item = userCart.get(position);

        holder.itemName.setText(item.getName());
        holder.itemPrice.setText(String.format(Locale.ENGLISH, "₱%.2f", item.getPrice()));
        holder.currentQuantity.setText(String.format(Locale.ENGLISH, "%d", item.getNumInCart()));
        holder.subtotal.setText(String.format(Locale.ENGLISH, " ₱%.2f", (item.getPrice() * item.getNumInCart())));

        holder.decrementCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopItem item = userCart.get(position);
                int numInCart = item.getNumInCart();

                if (numInCart == 1) {
                    item.setNumInCart(0);
                    cartClickListener.removeFromCart(item);

                    holder.itemView.setVisibility(View.GONE);
                } else {
                    item.setNumInCart(numInCart - 1);
                    cartClickListener.updateCart(item);

                    holder.subtotal.setText(String.format(Locale.ENGLISH, " ₱%.2f", (item.getPrice() * item.getNumInCart())));
                    holder.currentQuantity.setText(String.format(Locale.ENGLISH, "%d", item.getNumInCart()));
                }
            }
        });

        holder.incrementCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopItem item = userCart.get(position);
                item.setNumInCart(item.getNumInCart() + 1);
                cartClickListener.updateCart(item);

                holder.subtotal.setText(String.format(Locale.ENGLISH, " ₱%.2f", (item.getPrice() * item.getNumInCart())));
                holder.currentQuantity.setText(String.format(Locale.ENGLISH, "%d", item.getNumInCart()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return userCart.size();
    }

    static class CartItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemPrice;
        ImageView incrementCount;
        ImageView decrementCount;
        TextView currentQuantity;
        TextView subtotal;

        public CartItemViewHolder(View view) {
            super(view);
            itemName = view.findViewById(R.id.cart_item_name);
            itemPrice = view.findViewById(R.id.cart_item_price);
            incrementCount = view.findViewById(R.id.cart_item_increment);
            decrementCount = view.findViewById(R.id.cart_item_decrement);
            currentQuantity = view.findViewById(R.id.cart_current_item_quantity);
            subtotal = view.findViewById(R.id.cart_item_subtotal);
        }

    }

    public interface CartClickListener {
        void updateCart(ShopItem item);

        void removeFromCart(ShopItem item);
    }

}
