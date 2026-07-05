package com.example.foodordering;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnCartUpdateListener {
    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private TextView totalPriceText;
    private TextView emptyCartText;
    private Button placeOrderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.cart);
        toolbar.setNavigationOnClickListener(v -> finish());

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(AppData.getCartList(), this);
        cartRecyclerView.setAdapter(cartAdapter);

        totalPriceText = findViewById(R.id.totalPriceText);
        emptyCartText = findViewById(R.id.emptyCartText);
        placeOrderBtn = findViewById(R.id.placeOrderBtn);

        updateUI();

        placeOrderBtn.setOnClickListener(v -> {
            placeOrder();
        });
    }

    private void updateUI() {
        double total = AppData.getCartTotal();
        totalPriceText.setText(String.format("%s %s", getString(R.string.total),
                String.format(getString(R.string.price), total)));

        if (AppData.getCartList().isEmpty()) {
            cartRecyclerView.setVisibility(View.GONE);
            emptyCartText.setVisibility(View.VISIBLE);
            placeOrderBtn.setEnabled(false);
        } else {
            cartRecyclerView.setVisibility(View.VISIBLE);
            emptyCartText.setVisibility(View.GONE);
            placeOrderBtn.setEnabled(true);
        }
    }

    private void placeOrder() {
        if (AppData.getCartList().isEmpty()) {
            return;
        }

        String orderId = "ORD" + System.currentTimeMillis();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        double total = AppData.getCartTotal();

        List<CartItem> orderItems = new ArrayList<>(AppData.getCartList());
        Order order = new Order(orderId, date, orderItems, total);

        AppData.addOrder(order);
        AppData.clearCart();

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra("orderId", orderId);
        intent.putExtra("total", total);
        startActivity(intent);
    }

    @Override
    public void onCartUpdated() {
        updateUI();
        cartAdapter.notifyDataSetChanged();
    }
}