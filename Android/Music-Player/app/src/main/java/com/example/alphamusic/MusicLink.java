package com.example.alphamusic;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MusicLink {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String url;

    public MusicLink(String url) {
        this.url = url;
    }
}