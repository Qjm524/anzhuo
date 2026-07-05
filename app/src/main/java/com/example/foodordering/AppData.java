package com.example.foodordering;

import java.util.ArrayList;
import java.util.List;

public class AppData {
    private static List<FoodItem> foodList;
    private static List<CartItem> cartList = new ArrayList<>();
    private static List<Order> orderList = new ArrayList<>();

    public static List<FoodItem> getFoodList() {
        if (foodList == null) {
            foodList = new ArrayList<>();
            foodList.add(new FoodItem(R.drawable.food_hongshaorou, "红烧肉", "精选五花肉，肥而不腻", 38.0));
            foodList.add(new FoodItem(R.drawable.food_gongbaojiding, "宫保鸡丁", "经典川菜，香辣可口", 28.0));
            foodList.add(new FoodItem(R.drawable.food_qingzhengluyu, "清蒸鲈鱼", "鲜嫩爽滑，营养丰富", 45.0));
            foodList.add(new FoodItem(R.drawable.food_mapodoufu, "麻婆豆腐", "麻辣鲜香，下饭神器", 18.0));
            foodList.add(new FoodItem(R.drawable.food_tangcupaigu, "糖醋排骨", "酸甜可口，老少皆宜", 32.0));
            foodList.add(new FoodItem(R.drawable.food_yuxiangrousi, "鱼香肉丝", "酸甜微辣，香气扑鼻", 26.0));
            foodList.add(new FoodItem(R.drawable.food_huiguorou, "回锅肉", "传统川菜，肥而不腻", 30.0));
            foodList.add(new FoodItem(R.drawable.food_suanrongxilan, "蒜蓉西兰花", "清爽健康，营养均衡", 22.0));
        }
        return foodList;
    }

    public static List<CartItem> getCartList() {
        return cartList;
    }

    public static void addToCart(FoodItem food) {
        for (CartItem item : cartList) {
            if (item.getFoodItem().getName().equals(food.getName())) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        cartList.add(new CartItem(food, 1));
    }

    public static void removeFromCart(int position) {
        cartList.remove(position);
    }

    public static double getCartTotal() {
        double total = 0;
        for (CartItem item : cartList) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public static void clearCart() {
        cartList.clear();
    }

    public static List<Order> getOrderList() {
        return orderList;
    }

    public static void addOrder(Order order) {
        orderList.add(order);
    }

    public static int getCartItemCount() {
        return cartList.size();
    }

    public static void addFoodItem(FoodItem food) {
        if (foodList == null) {
            foodList = new ArrayList<>();
        }
        foodList.add(food);
    }

    public static void removeFoodItem(int position) {
        if (foodList != null && position >= 0 && position < foodList.size()) {
            foodList.remove(position);
        }
    }
}