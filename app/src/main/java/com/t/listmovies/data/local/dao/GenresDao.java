package com.t.listmovies.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import com.t.listmovies.model.genres.GenresLocalModel;

import java.util.List;

@Dao
public interface GenresDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GenresLocalModel genre);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<GenresLocalModel> genres);

    @Update
    void update(GenresLocalModel genre);

    @Delete
    void delete(GenresLocalModel genre);
}
