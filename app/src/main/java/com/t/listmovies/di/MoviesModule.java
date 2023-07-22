package com.t.listmovies.di;

import com.t.listmovies.data.local.dao.MoviesDao;
import com.t.listmovies.data.local.database.MoviesDatabase;
import com.t.listmovies.data.remote.movies.MoviesRepo;
import com.t.listmovies.data.remote.movies.MoviesRepoImpl;
import com.t.listmovies.data.remote.services.MoviesService;
import com.t.listmovies.util.AppExecutors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(SingletonComponent.class)
public class MoviesModule {
    @Provides
    public MoviesService provideMoviesService(Retrofit mRetrofit) {
        return mRetrofit
                .create(MoviesService.class);
    }

    @Provides
    public MoviesDao provideMoviesDao(MoviesDatabase mDatabase) {
        return mDatabase.moviesDAO();
    }

    @Singleton
    @Provides
    public MoviesRepo provideMoviesRepo(AppExecutors mExecutors, MoviesService mService, MoviesDao mDao) {
        return new MoviesRepoImpl(mExecutors, mService, mDao);
    }
}
