package com.t.listmovies.data.remote.genres;

import com.t.listmovies.data.local.dao.GenresDao;
import com.t.listmovies.util.AppExecutors;

public class GenresRepoImpl implements GenresRepo {
    private AppExecutors mExecutors;
    private GenresDao mDao;

    public GenresRepoImpl(AppExecutors mExecutors, GenresDao mDao) {
        this.mExecutors = mExecutors;
        this.mDao = mDao;
    }
}
