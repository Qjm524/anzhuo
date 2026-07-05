package com.example.foodordering;

public class FoodItem {
    private int imageResId;
    private String name;
    private String description;
    private double price;

    public FoodItem(int imageResId, String name, String description, double price) {
        this.imageResId = imageResId;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}