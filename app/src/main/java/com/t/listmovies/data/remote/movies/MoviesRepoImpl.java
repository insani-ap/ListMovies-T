package com.t.listmovies.data.remote.movies;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.t.listmovies.data.local.dao.MoviesDao;
import com.t.listmovies.data.remote.services.MoviesService;
import com.t.listmovies.model.movies.MoviesDomainModel;
import com.t.listmovies.model.movies.MoviesLocalModel;
import com.t.listmovies.model.movies.MoviesRemoteModel;
import com.t.listmovies.model.response.BaseCallback;
import com.t.listmovies.model.response.BaseResponse;
import com.t.listmovies.util.AppExecutors;
import com.t.listmovies.util.Const;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepoImpl implements MoviesRepo {
    private final AppExecutors mExecutors;
    private final MoviesService mService;
    private final MoviesDao mDao;

    public MoviesRepoImpl(AppExecutors mExecutors, MoviesService mService, MoviesDao mDao) {
        this.mExecutors = mExecutors;
        this.mService = mService;
        this.mDao = mDao;
    }

    @Override
    public LiveData<List<MoviesDomainModel>> getLocalMovies() {
        return mDao.getObservableMovies();
    }

    @Override
    public void getRemoteMovies(int pageNumber, BaseCallback mCallback) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("api_key", Const.KEY);
        param.put("page", pageNumber);

        Call<BaseResponse<MoviesRemoteModel>> request = mService.getMovies(param);
        request.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse<MoviesRemoteModel>> call, @NonNull Response<BaseResponse<MoviesRemoteModel>> response) {
                if (response.isSuccessful()) {
                    BaseResponse<MoviesRemoteModel> responseBody = response.body();
                    if (responseBody != null) {
                        mExecutors.diskIO().execute(() -> {
                            saveRemoteMovies(responseBody.results);
                            mCallback.onSuccess();
                        });
                    } else {
                        mCallback.onError();
                    }
                } else {
                    mCallback.onError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse<MoviesRemoteModel>> call, @NonNull Throwable t) {
                mCallback.onError();
            }
        });
    }

    @Override
    public void saveRemoteMovies(List<MoviesRemoteModel> movies) {
        mDao.insertAll(convertRemoteToLocal(movies));
    }

    private List<MoviesLocalModel> convertRemoteToLocal(List<MoviesRemoteModel> response) {
        //Best case: Insert Genre Id First in Genre Table
        List<MoviesLocalModel> result = new ArrayList<>();
        for (MoviesRemoteModel movieRemote : response) {
            result.add(new MoviesLocalModel(
                    movieRemote.getAdult(),
                    movieRemote.getBackdropPath(),
                    -1,
                    movieRemote.getId(),
                    movieRemote.getOriginalLanguage(),
                    movieRemote.getOriginalTitle(),
                    movieRemote.getOverview(),
                    movieRemote.getPopularity(),
                    movieRemote.getPosterPath(),
                    movieRemote.getReleaseDate(),
                    movieRemote.getTitle(),
                    movieRemote.getVideo(),
                    movieRemote.getVoteAverage(),
                    movieRemote.getVoteCount()
                    ));
        }

        return result;
    }
}
