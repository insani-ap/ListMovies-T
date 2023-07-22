package com.t.listmovies.data.local.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.t.listmovies.data.local.dao.GenresDao;
import com.t.listmovies.data.local.dao.MoviesDao;
import com.t.listmovies.model.genres.GenresLocalModel;
import com.t.listmovies.model.movies.MoviesLocalModel;

@Database(entities = {MoviesLocalModel.class, GenresLocalModel.class}, version = 1)
public abstract class MoviesDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "Movies.db";

    public abstract MoviesDao moviesDAO();
    public abstract GenresDao genresDAO();
}
