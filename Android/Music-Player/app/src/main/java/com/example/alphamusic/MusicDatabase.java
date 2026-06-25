package com.example.alphamusic;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MusicLink.class}, version = 1)
public abstract class MusicDatabase extends RoomDatabase {
    public abstract DAO musicDao();
}