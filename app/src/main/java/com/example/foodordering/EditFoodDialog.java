package com.example.foodordering;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditFoodDialog extends Dialog {
    private EditText nameInput;
    private EditText descriptionInput;
    private EditText priceInput;
    private Button saveBtn;
    private Button cancelBtn;
    private OnSaveListener listener;

    public interface OnSaveListener {
        void onSave(String name, String description, double price);
    }

    public EditFoodDialog(Context context, FoodItem food, OnSaveListener listener) {
        super(context);
        this.listener = listener;
        initDialog(food);
    }

    private void initDialog(FoodItem food) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_edit_food);

        nameInput = findViewById(R.id.nameInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        priceInput = findViewById(R.id.priceInput);
        saveBtn = findViewById(R.id.saveBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        if (food != null) {
            nameInput.setText(food.getName());
            descriptionInput.setText(food.getDescription());
            priceInput.setText(String.valueOf(food.getPrice()));
        }

        saveBtn.setOnClickListener(v -> handleSave());
        cancelBtn.setOnClickListener(v -> dismiss());
    }

    private void handleSave() {
        String name = nameInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();
        String priceStr = priceInput.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(getContext(), R.string.enter_food_name, Toast.LENGTH_SHORT).show();
            return;
        }

        if (priceStr.isEmpty()) {
            Toast.makeText(getContext(), R.string.enter_price, Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), R.string.invalid_price, Toast.LENGTH_SHORT).show();
            return;
        }

        if (listener != null) {
            listener.onSave(name, description, price);
        }
        dismiss();
    }
}