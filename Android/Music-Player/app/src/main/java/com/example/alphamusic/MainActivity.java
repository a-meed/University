package com.example.alphamusic;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button btnPlayPause, btnNext, btnPrev, btnOnline;
    List<Music> musicList = new ArrayList<>();
    MediaPlayer mediaPlayer;
    int currentIndex = -1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        btnPlayPause = findViewById(R.id.playBtn);
        btnNext = findViewById(R.id.nexBtn);
        btnPrev = findViewById(R.id.prevBtn);
        btnOnline = findViewById(R.id.onlineMusic);

        checkPermission();

        btnPlayPause.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btnPlayPause.setText("▶");
                } else {
                    mediaPlayer.start();
                    btnPlayPause.setText("⏸");
                }
            }
        });

        btnNext.setOnClickListener(v -> playMusic(currentIndex + 1));
        btnPrev.setOnClickListener(v -> playMusic(currentIndex - 1));

        btnOnline.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, OnlineActivity.class);
            startActivity(intent);
        });
    }

    void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_AUDIO}, 1);
        } else {
            loadMusic();
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, String @NotNull [] permissions, int @NotNull [] grantResults) {
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadMusic();
        } else {
            Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
        }
    }

    void loadMusic() {
        ContentResolver resolver = getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        Cursor cursor = resolver.query(uri, null, selection, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                musicList.add(new Music(title, path));
            } while (cursor.moveToNext());
            cursor.close();
        }

        MusicAdapter adapter = new MusicAdapter(musicList, this::playMusic);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    void playMusic(int index) {
        if (index < 0 || index >= musicList.size()) return;
        currentIndex = index;

        if (mediaPlayer != null) mediaPlayer.release();

        mediaPlayer = MediaPlayer.create(this, Uri.parse(musicList.get(index).getPath()));
        mediaPlayer.start();
        btnPlayPause.setText("⏸");

        mediaPlayer.setOnCompletionListener(mp -> playMusic(currentIndex + 1));
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) mediaPlayer.release();
        super.onDestroy();
    }
}