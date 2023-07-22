package com.t.listmovies.modul.movies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.t.listmovies.data.remote.movies.MoviesRepo;
import com.t.listmovies.model.movies.MoviesDomainModel;
import com.t.listmovies.util.Preferences;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ListMoviesViewModel extends ViewModel {
    private final MoviesRepo mRepo;

    private LiveData<List<MoviesDomainModel>> movies;
    public LiveData<List<MoviesDomainModel>> getMovies() {
        return movies;
    }

    @Inject
    public ListMoviesViewModel(MoviesRepo mRepo, Preferences mPreferences) {
        super();
        this.mRepo = mRepo;
    }

    public void getLocalMovies() {
        movies = mRepo.getLocalMovies();
    }
}
