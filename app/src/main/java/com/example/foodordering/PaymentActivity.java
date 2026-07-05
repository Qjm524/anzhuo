package com.example.foodordering;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

public class PaymentActivity extends AppCompatActivity {
    private TextView orderIdText;
    private TextView totalPriceText;
    private ImageView qrCodeImage;
    private Button paySuccessBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.payment);
        toolbar.setNavigationOnClickListener(v -> finish());

        orderIdText = findViewById(R.id.orderIdText);
        totalPriceText = findViewById(R.id.totalPriceText);
        qrCodeImage = findViewById(R.id.qrCodeImage);
        paySuccessBtn = findViewById(R.id.paySuccessBtn);

        String orderId = getIntent().getStringExtra("orderId");
        double total = getIntent().getDoubleExtra("total", 0);

        orderIdText.setText(String.format(getString(R.string.order_id), orderId));
        totalPriceText.setText(String.format(getString(R.string.total_price), total));

        paySuccessBtn.setOnClickListener(v -> {
            Toast.makeText(this, R.string.payment_success, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, OrderHistoryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}