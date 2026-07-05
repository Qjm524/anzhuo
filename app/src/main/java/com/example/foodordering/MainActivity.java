package com.example.foodordering;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private Button adminBtn;
    private TextView userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.menu);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodAdapter = new FoodAdapter(AppData.getFoodList());
        recyclerView.setAdapter(foodAdapter);

        userInfo = findViewById(R.id.userInfo);
        adminBtn = findViewById(R.id.adminBtn);

        updateUserInfo();

        adminBtn.setOnClickListener(v -> startActivity(new Intent(this, AdminActivity.class)));

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_menu);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_cart) {
                startActivity(new Intent(this, CartActivity.class));
                return true;
            } else if (id == R.id.nav_orders) {
                startActivity(new Intent(this, OrderHistoryActivity.class));
                return true;
            } else if (id == R.id.nav_ai) {
                startActivity(new Intent(this, AIRecommendActivity.class));
                return true;
            }
            return false;
        });
    }

    private void updateUserInfo() {
        if (UserManager.isLoggedIn(this)) {
            String username = UserManager.getUsername(this);
            if (UserManager.isAdmin(this)) {
                userInfo.setText(String.format("管理员：%s", username));
                adminBtn.setVisibility(View.VISIBLE);
            } else if (UserManager.isNormalUser(this)) {
                userInfo.setText(String.format("用户：%s", username));
                adminBtn.setVisibility(View.GONE);
            } else {
                userInfo.setText("游客模式");
                adminBtn.setVisibility(View.GONE);
            }
        } else {
            userInfo.setText("游客模式");
            adminBtn.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUserInfo();
        foodAdapter.notifyDataSetChanged();
    }
}