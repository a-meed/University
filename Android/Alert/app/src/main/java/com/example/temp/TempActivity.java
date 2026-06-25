package com.example.temp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TempActivity extends AppCompatActivity {

    TextView textView;
    Button clear;

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        textView = findViewById(R.id.getText);
        clear = findViewById(R.id.clearData);

        SharedPreferences sp = getSharedPreferences("MyPref", MODE_PRIVATE);
        boolean api35 = getIntent().getBooleanExtra("api35", false);
        String fullName = sp.getString("full_name", "Dear User");
        int apiCode = getIntent().getIntExtra("ApiCode", 0);
        String Api35 = api35 ? "smart phone" : "oldest phone";

        textView.setText("Welcome back " + fullName + "\n\n Android SDK: " + apiCode + "\n\n You have " + Api35);

        clear.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.apply();
            Intent i = new Intent(TempActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        });
    }
}