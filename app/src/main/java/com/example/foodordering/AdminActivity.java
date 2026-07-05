package com.example.foodordering;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class AdminActivity extends AppCompatActivity {
    private RecyclerView foodRecyclerView;
    private AdminFoodAdapter foodAdapter;
    private Button addFoodBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.admin_panel);
        toolbar.setNavigationOnClickListener(v -> finish());

        foodRecyclerView = findViewById(R.id.foodRecyclerView);
        foodRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodAdapter = new AdminFoodAdapter(AppData.getFoodList(), this);
        foodRecyclerView.setAdapter(foodAdapter);

        addFoodBtn = findViewById(R.id.addFoodBtn);
        addFoodBtn.setOnClickListener(v -> showAddFoodDialog());
    }

    private void showAddFoodDialog() {
        EditFoodDialog dialog = new EditFoodDialog(this, null, (name, description, price) -> {
            FoodItem newFood = new FoodItem(R.drawable.food_hongshaorou, name, description, price);
            AppData.addFoodItem(newFood);
            foodAdapter.notifyItemInserted(AppData.getFoodList().size() - 1);
            Toast.makeText(this, R.string.food_added, Toast.LENGTH_SHORT).show();
        });
        dialog.show();
    }

    public void editFood(int position) {
        FoodItem food = AppData.getFoodList().get(position);
        EditFoodDialog dialog = new EditFoodDialog(this, food, (name, description, price) -> {
            food.setName(name);
            food.setDescription(description);
            food.setPrice(price);
            foodAdapter.notifyItemChanged(position);
            Toast.makeText(this, R.string.food_updated, Toast.LENGTH_SHORT).show();
        });
        dialog.show();
    }

    public void deleteFood(int position) {
        AppData.removeFoodItem(position);
        foodAdapter.notifyItemRemoved(position);
        foodAdapter.notifyItemRangeChanged(position, AppData.getFoodList().size());
        Toast.makeText(this, R.string.food_deleted, Toast.LENGTH_SHORT).show();
    }
}