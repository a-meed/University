package com.example.alphamusic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OnlineActivity extends AppCompatActivity {

    EditText edtLink;
    Button btnSave, btnShow;
    MusicDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);

        edtLink = findViewById(R.id.edtLink);
        btnSave = findViewById(R.id.btnSave);
        btnShow = findViewById(R.id.btnShow);

        db = Room.databaseBuilder(getApplicationContext(), MusicDatabase.class, "music_db").allowMainThreadQueries().build();

        btnSave.setOnClickListener(v -> {
            String link = edtLink.getText().toString().trim();
            if (!link.isEmpty()) {
                db.musicDao().insert(new MusicLink(link));
                Toast.makeText(this, "Link Saved Successfully.", Toast.LENGTH_SHORT).show();
                edtLink.setText("");
            } else {
                Toast.makeText(this, "Please Enter a Link!", Toast.LENGTH_SHORT).show();
            }
        });

        btnShow.setOnClickListener(v -> {
            SharedPreferences sp = getSharedPreferences("Links", Context.MODE_PRIVATE);
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sp.edit();
            Intent intent = new Intent(OnlineActivity.this, NetworkCheckActivity.class);
            startActivity(intent);
        });
    }
}

