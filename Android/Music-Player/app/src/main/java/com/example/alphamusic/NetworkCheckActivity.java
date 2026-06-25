package com.example.alphamusic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class NetworkCheckActivity extends AppCompatActivity {
    TextView netStatusText;
    ImageView netStatusImage;
    LinearLayout netStatusLayout;
    Button netStatusButton;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_netstat);

        netStatusText = findViewById(R.id.netStatTxt);
        netStatusImage = findViewById(R.id.netStatImg);
        netStatusLayout = findViewById(R.id.bground);
        netStatusButton = findViewById(R.id.btnRetry);

        switching(checkNetwork());
        netStatusButton.setOnClickListener(view -> {
            switching(checkNetwork());
        });
    }

    @SuppressLint("SetTextI18n")
    private boolean checkNetwork() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable()) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressLint("SetTextI18n")
    private void switching(boolean isConnected) {
        if (isConnected) {
            netStatusImage.setImageResource(R.mipmap.connected);
            netStatusText.setText("Internet Connection Available");
            netStatusLayout.setBackgroundColor(Color.parseColor("#53EDA2"));
            netStatusButton.setEnabled(false);
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(NetworkCheckActivity.this, OnlineListActivity.class);
                startActivity(intent);
            }, 1000);
        } else {
            netStatusImage.setImageResource(R.mipmap.disconnected);
            netStatusText.setText("Internet Connection Failed!\n\nConnect to an Internet Connection and then Try Again");
            netStatusLayout.setBackgroundColor(Color.parseColor("#54B5EC"));
        }
    }
}