package com.t.listmovies.modul.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.t.listmovies.data.local.database.MoviesDatabase;
import com.t.listmovies.databinding.ActivitySplashBinding;
import com.t.listmovies.modul.BaseActivity;
import com.t.listmovies.modul.movies.ListMoviesActivity;
import com.t.listmovies.util.AppExecutors;
import com.t.listmovies.util.NetworkService;
import com.t.listmovies.util.Preferences;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashActivity extends BaseActivity {
    private ActivitySplashBinding mBinding;
    private final AppExecutors mExecutors = new AppExecutors();

    @Inject
    MoviesDatabase mDatabase;
    @Inject
    Preferences mPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        cleanUpEverything();
        new Handler(Looper.getMainLooper()).postDelayed(this::initializeConnection, 2000);
    }

    private void cleanUpEverything() {
        mExecutors.diskIO().execute(() -> {
            mDatabase.clearAllTables();
            mPreferences.setPageSize(1);
        });
    }

    private void  initializeConnection() {
        mExecutors.diskIO().execute(() -> {
            if (NetworkService.getConnectivityStatus(getApplicationContext())) {
                mExecutors.mainThread().execute(() -> {
                    startActivity(new Intent(SplashActivity.this, ListMoviesActivity.class));
                    finish();
                });
            } else {
                mExecutors.mainThread().execute(() -> {
                    Snackbar mBar = Snackbar.make(mBinding.getRoot(), "Perangkat tidak memiliki koneksi internet", BaseTransientBottomBar.LENGTH_INDEFINITE);
                    mBar.setAction("PERIKSA KEMBALI", view -> {
                        mBar.dismiss();
                        initializeConnection();
                    });
                    mBar.show();
                });
            }
        });
    }
}
