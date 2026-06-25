package com.example.alphalogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button swt_register = findViewById(R.id.signup_try);
        Button login = findViewById(R.id.signin_try);
        EditText username = findViewById(R.id.user_name);
        EditText password = findViewById(R.id.user_password);

        swt_register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        login.setOnClickListener(v -> {
            if (username.getText().toString().length() > 7 && password.getText().toString().length() > 7) {
                Data data = new Data();
                if (username.getText().toString().equals(data.username) && password.getText().toString().equals(data.password)) {
                    Intent intent = new Intent(LoginActivity.this, ContactsActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Username or Password Incorrect", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "Username or password are required", Toast.LENGTH_SHORT).show();
            }
        });
    }
}