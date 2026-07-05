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

public class AdminFoodAdapter extends RecyclerView.Adapter<AdminFoodAdapter.AdminFoodViewHolder> {
    private List<FoodItem> foodItems;
    private AdminActivity activity;

    public AdminFoodAdapter(List<FoodItem> foodItems, AdminActivity activity) {
        this.foodItems = foodItems;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdminFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_food, parent, false);
        return new AdminFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminFoodViewHolder holder, int position) {
        FoodItem food = foodItems.get(position);
        holder.foodImage.setImageResource(food.getImageResId());
        holder.foodName.setText(food.getName());
        holder.foodDescription.setText(food.getDescription());
        holder.foodPrice.setText(String.format("¥%.1f", food.getPrice()));

        holder.editBtn.setOnClickListener(v -> activity.editFood(position));
        holder.deleteBtn.setOnClickListener(v -> activity.deleteFood(position));
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    static class AdminFoodViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView foodName, foodDescription, foodPrice;
        Button editBtn, deleteBtn;

        public AdminFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.foodImage);
            foodName = itemView.findViewById(R.id.foodName);
            foodDescription = itemView.findViewById(R.id.foodDescription);
            foodPrice = itemView.findViewById(R.id.foodPrice);
            editBtn = itemView.findViewById(R.id.editBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}