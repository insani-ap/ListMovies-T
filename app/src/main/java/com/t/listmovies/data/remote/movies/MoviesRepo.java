package com.t.listmovies.data.remote.movies;

import androidx.lifecycle.LiveData;

import com.t.listmovies.model.movies.MoviesDomainModel;
import com.t.listmovies.model.movies.MoviesRemoteModel;
import com.t.listmovies.model.response.BaseCallback;

import java.util.List;

public interface MoviesRepo {
    LiveData<List<MoviesDomainModel>> getLocalMovies();
    void getRemoteMovies(int pageNumber, BaseCallback mCallback);
    void saveRemoteMovies(List<MoviesRemoteModel> movies);
}
