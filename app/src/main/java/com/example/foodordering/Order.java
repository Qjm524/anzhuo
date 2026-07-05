package com.example.foodordering;

import java.util.List;

public class Order {
    private String orderId;
    private String date;
    private List<CartItem> items;
    private double total;

    public Order(String orderId, String date, List<CartItem> items, double total) {
        this.orderId = orderId;
        this.date = date;
        this.items = items;
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getDate() {
        return date;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public String getItemsSummary() {
        StringBuilder sb = new StringBuilder();
        for (CartItem item : items) {
            sb.append(item.getFoodItem().getName())
              .append(" x")
              .append(item.getQuantity())
              .append("\n");
        }
        return sb.toString().trim();
    }
}