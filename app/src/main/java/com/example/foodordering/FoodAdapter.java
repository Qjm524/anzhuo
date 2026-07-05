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

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<FoodItem> foodItems;

    public FoodAdapter(List<FoodItem> foodItems) {
        this.foodItems = foodItems;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodItem food = foodItems.get(position);
        holder.foodImage.setImageResource(food.getImageResId());
        holder.foodName.setText(food.getName());
        holder.foodDescription.setText(food.getDescription());
        holder.foodPrice.setText(String.format(holder.itemView.getContext().getString(R.string.price), food.getPrice()));

        holder.addToCartBtn.setOnClickListener(v -> {
            AppData.addToCart(food);
        });
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView foodName, foodDescription, foodPrice;
        Button addToCartBtn;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.foodImage);
            foodName = itemView.findViewById(R.id.foodName);
            foodDescription = itemView.findViewById(R.id.foodDescription);
            foodPrice = itemView.findViewById(R.id.foodPrice);
            addToCartBtn = itemView.findViewById(R.id.addToCartBtn);
        }
    }
}