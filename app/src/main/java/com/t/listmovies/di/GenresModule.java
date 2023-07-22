package com.t.listmovies.di;

import com.t.listmovies.data.local.dao.GenresDao;
import com.t.listmovies.data.local.database.MoviesDatabase;
import com.t.listmovies.data.remote.genres.GenresRepo;
import com.t.listmovies.data.remote.genres.GenresRepoImpl;
import com.t.listmovies.util.AppExecutors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class GenresModule {
    @Provides
    public GenresDao provideGenresDao(MoviesDatabase mDatabase) {
        return mDatabase.genresDAO();
    }

    @Singleton
    @Provides
    public GenresRepo provideMoviesRepo(AppExecutors mExecutors, GenresDao mDao) {
        return new GenresRepoImpl(mExecutors, mDao);
    }
}
