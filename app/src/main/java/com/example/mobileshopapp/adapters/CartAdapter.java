package com.example.mobileshopapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileshopapp.R;
import com.example.mobileshopapp.models.ShopItem;

import java.util.ArrayList;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartItemViewHolder> {

    private final ArrayList<ShopItem> userCart;
    private final CartClickListener cartClickListener;

    public CartAdapter(ArrayList<ShopItem> userCart, CartClickListener cartClickListener) {
        this.userCart = userCart;
        this.cartClickListener = cartClickListener;
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
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_recycler_view, parent, false);
        return new CartAdapter.CartItemViewHolder(view);
    }

    /**
     *  Put string, image, and listener to appropriate attribute.
     *  Essentially this connects the data to the views.
     *
     * @param holder   variable containing the current item
     * @param position  position of the current item relative to the recycler view
     */
    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartItemViewHolder holder, int position) {
        ShopItem item = userCart.get(position);

        holder.itemName.setText(item.getName());
        holder.itemPrice.setText(String.format(Locale.ENGLISH, "₱%.2f", item.getPrice()));
        holder.currentQuantity.setText(String.format(Locale.ENGLISH, "%d", item.getNumInCart()));
        holder.subtotal.setText(String.format(Locale.ENGLISH, " ₱%.2f", (item.getPrice() * item.getNumInCart())));

        holder.decrementCount.setOnClickListener(view -> {
            ShopItem item1 = userCart.get(position);
            int numInCart = item1.getNumInCart();

            if (numInCart == 0) {
                Toast toast = Toast.makeText(view.getContext(), "Item count can't be negative", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                item1.setNumInCart(numInCart - 1);
                cartClickListener.updateCart(item1);

                holder.subtotal.setText(String.format(Locale.ENGLISH, " ₱%.2f", (item1.getPrice() * item1.getNumInCart())));
                holder.currentQuantity.setText(String.format(Locale.ENGLISH, "%d", item1.getNumInCart()));
            }
        });

        holder.incrementCount.setOnClickListener(view -> {
            ShopItem item12 = userCart.get(position);
            item12.setNumInCart(item12.getNumInCart() + 1);
            cartClickListener.updateCart(item12);

            holder.subtotal.setText(String.format(Locale.ENGLISH, " ₱%.2f", (item12.getPrice() * item12.getNumInCart())));
            holder.currentQuantity.setText(String.format(Locale.ENGLISH, "%d", item12.getNumInCart()));
        });
    }

    /**
     *
     * @return  the number of items in the RecyclerView
     */
    @Override
    public int getItemCount() {
        return userCart.size();
    }
}
