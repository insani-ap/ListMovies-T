package com.t.listmovies.data.remote.services;

import com.t.listmovies.model.movies.MoviesRemoteModel;
import com.t.listmovies.model.response.BaseResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface MoviesService {
    @GET("3/discover/movie")
    Call<BaseResponse<MoviesRemoteModel>> getMovies(@QueryMap HashMap<String, Object> param);
}
