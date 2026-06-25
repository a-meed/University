package com.example.alphamusic;

import androidx.room.*;

import java.util.List;

@Dao
public interface DAO {
    @Query("SELECT * FROM MusicLink")
    List<MusicLink> getAllLinks();

    @Insert
    void insert(MusicLink musicLink);

    @Update
    void update(MusicLink musicLink);

    @Delete
    void delete(MusicLink musicLink);
}
