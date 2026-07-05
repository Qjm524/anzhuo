package com.example.foodordering;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameInput;
    private EditText passwordInput;
    private RadioGroup userTypeGroup;
    private Button loginBtn;
    private Button guestBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        userTypeGroup = findViewById(R.id.userTypeGroup);
        loginBtn = findViewById(R.id.loginBtn);
        guestBtn = findViewById(R.id.guestBtn);

        loginBtn.setOnClickListener(v -> handleLogin());
        guestBtn.setOnClickListener(v -> enterAsGuest());
    }

    private void handleLogin() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (username.isEmpty()) {
            Toast.makeText(this, R.string.enter_username, Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, R.string.enter_password, Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedId = userTypeGroup.getCheckedRadioButtonId();
        int userType = UserManager.USER_TYPE_NORMAL;

        if (selectedId == R.id.radioAdmin) {
            if (!username.equals("admin") || !password.equals("admin123")) {
                Toast.makeText(this, R.string.admin_login_fail, Toast.LENGTH_SHORT).show();
                return;
            }
            userType = UserManager.USER_TYPE_ADMIN;
        }

        UserManager.login(this, userType, username);
        Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void enterAsGuest() {
        UserManager.login(this, UserManager.USER_TYPE_GUEST, "guest");
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}