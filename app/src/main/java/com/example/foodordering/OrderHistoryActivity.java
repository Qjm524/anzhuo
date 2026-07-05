package com.example.foodordering;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

public class OrderHistoryActivity extends AppCompatActivity {
    private RecyclerView orderRecyclerView;
    private OrderAdapter orderAdapter;
    private TextView noOrdersText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.order_history);
        toolbar.setNavigationOnClickListener(v -> finish());

        orderRecyclerView = findViewById(R.id.orderRecyclerView);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(AppData.getOrderList());
        orderRecyclerView.setAdapter(orderAdapter);

        noOrdersText = findViewById(R.id.noOrdersText);
        noOrdersText.setText(R.string.no_orders);

        updateUI();
    }

    private void updateUI() {
        if (AppData.getOrderList().isEmpty()) {
            orderRecyclerView.setVisibility(View.GONE);
            noOrdersText.setVisibility(View.VISIBLE);
        } else {
            orderRecyclerView.setVisibility(View.VISIBLE);
            noOrdersText.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        orderAdapter.notifyDataSetChanged();
        updateUI();
    }
}