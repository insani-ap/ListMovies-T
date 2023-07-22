package com.t.listmovies.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.t.listmovies.model.movies.MoviesDomainModel;
import com.t.listmovies.model.movies.MoviesLocalModel;

import java.util.List;

@Dao
public interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MoviesLocalModel movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MoviesLocalModel> movies);

    @Update
    void update(MoviesLocalModel movie);

    @Delete
    void delete(MoviesLocalModel movie);

    @Query("SELECT moviesId, originalTitle, releaseDate FROM movie ORDER BY popularity LIMIT 10")
    LiveData<List<MoviesDomainModel>> getObservableMovies();
}
