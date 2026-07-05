package com.example.foodordering;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> cartItems;
    private OnCartUpdateListener listener;

    public interface OnCartUpdateListener {
        void onCartUpdated();
    }

    public CartAdapter(List<CartItem> cartItems, OnCartUpdateListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        FoodItem food = item.getFoodItem();

        holder.cartFoodImage.setImageResource(food.getImageResId());
        holder.cartFoodName.setText(food.getName());
        holder.cartFoodPrice.setText(String.format(holder.itemView.getContext().getString(R.string.price), food.getPrice()));
        holder.quantityText.setText(String.valueOf(item.getQuantity()));

        holder.decreaseBtn.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                notifyItemChanged(position);
                if (listener != null) {
                    listener.onCartUpdated();
                }
            }
        });

        holder.increaseBtn.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            notifyItemChanged(position);
            if (listener != null) {
                listener.onCartUpdated();
            }
        });

        holder.removeBtn.setOnClickListener(v -> {
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
            if (listener != null) {
                listener.onCartUpdated();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView cartFoodImage;
        TextView cartFoodName, cartFoodPrice, quantityText;
        Button decreaseBtn, increaseBtn, removeBtn;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cartFoodImage = itemView.findViewById(R.id.cartFoodImage);
            cartFoodName = itemView.findViewById(R.id.cartFoodName);
            cartFoodPrice = itemView.findViewById(R.id.cartFoodPrice);
            quantityText = itemView.findViewById(R.id.quantityText);
            decreaseBtn = itemView.findViewById(R.id.decreaseBtn);
            increaseBtn = itemView.findViewById(R.id.increaseBtn);
            removeBtn = itemView.findViewById(R.id.removeBtn);
        }
    }
}