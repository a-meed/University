package com.example.alphalogin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button swt_login = findViewById(R.id.signin_try);
        Button register = findViewById(R.id.signup_try);
        EditText username = findViewById(R.id.user_name);
        EditText password = findViewById(R.id.user_password);
        EditText re_password = findViewById(R.id.user_repassword);

        swt_login.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        register.setOnClickListener(v -> {
            if (username.getText().toString().length() > 8 && password.getText().toString().length() > 7 && re_password.getText().toString().length() > 7) {
                if (password.getText().toString().equals(re_password.getText().toString())) {
                    Data data = new Data();
                    data.username = username.getText().toString();
                    data.password = re_password.getText().toString();
                    Toast.makeText(RegisterActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, ContactsActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegisterActivity.this, "Username and password are required", Toast.LENGTH_SHORT).show();
            }
        });
    }
}