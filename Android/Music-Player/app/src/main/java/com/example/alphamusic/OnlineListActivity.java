package com.example.alphamusic;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.io.IOException;
import java.util.List;

public class OnlineListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnPrev, btnStop, btnNext, btnEdit, btnDelete;
    MediaPlayer mediaPlayer;
    MusicDatabase db;
    List<MusicLink> musicLinks;
    int currentIndex = -1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_online);

        recyclerView = findViewById(R.id.recyclerView);
        btnPrev = findViewById(R.id.btnPrev);
        btnStop = findViewById(R.id.btnStop);
        btnNext = findViewById(R.id.btnNext);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        db = Room.databaseBuilder(getApplicationContext(), MusicDatabase.class, "music_db").allowMainThreadQueries().build();

        musicLinks = db.musicDao().getAllLinks();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new OnlineAdapter(musicLinks, new OnlineAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MusicLink link, int position) {
                playMusic(link.url);
                currentIndex = position;
            }

            @Override
            public void onEditClick(MusicLink link, int position) {
                showEditDialog(link, position);
            }

            @Override
            public void onDeleteClick(MusicLink link, int position){
                deleteLink(link, position);
            }
        }));

        musicLinks = db.musicDao().getAllLinks();
        if (musicLinks.isEmpty()) {
            SharedPreferences sp = getSharedPreferences("Links", MODE_PRIVATE);
            boolean shownBefore = sp.getBoolean("emptyShown", false);

            if (!shownBefore) {
                Toast.makeText(getApplicationContext(), "Not link saved yet!", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("emptyShown", true);
                editor.apply();
            }
        }

        btnNext.setOnClickListener(v -> {
            if (currentIndex < musicLinks.size() - 1) {
                currentIndex++;
                playMusic(musicLinks.get(currentIndex).url);
            }
        });

        btnPrev.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                playMusic(musicLinks.get(currentIndex).url);
            }
        });

        btnStop.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                btnStop.setText("▶");
            } else {
                mediaPlayer.start();
                btnStop.setText("⏸");
            }
        });
    }

    private void playMusic(String url) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        } else {
            mediaPlayer.reset();
        }

        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(mp -> {
                mediaPlayer.start();
            });
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(OnlineListActivity.this, "An error!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showEditDialog(MusicLink link, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(OnlineListActivity.this);
        builder.setTitle("Edit Link");

        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
        input.setText(link.url);
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newLink = input.getText().toString().trim();
            if (!newLink.isEmpty()) {
                link.url = newLink;
                db.musicDao().update(link);
                musicLinks.set(position, link);

                recyclerView.getAdapter().notifyItemChanged(position);
                Toast.makeText(OnlineListActivity.this, "Link updated!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(OnlineListActivity.this, "Link not updated!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void deleteLink(MusicLink link, int position) {
        db.musicDao().delete(link);
        musicLinks.remove(position);

        recyclerView.getAdapter().notifyItemRemoved(position);

        Toast.makeText(OnlineListActivity.this, "Link deleted!", Toast.LENGTH_SHORT).show();

        if (currentIndex == position && mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }

        if (currentIndex >= position) {
            currentIndex--;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}