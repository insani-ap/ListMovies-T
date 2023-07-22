package com.t.listmovies.modul.movies;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.t.listmovies.databinding.ActivityListMoviesBinding;
import com.t.listmovies.model.movies.MoviesDomainModel;
import com.t.listmovies.modul.BaseActivity;
import com.t.listmovies.service.MoviesService;
import com.t.listmovies.util.Const;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ListMoviesActivity extends BaseActivity {
    private ActivityListMoviesBinding mBinding;
    private ListMoviesViewModel mViewModel;
    private MoviesAdapter mAdapter;

    private List<MoviesDomainModel> queueUpdates = new ArrayList<>();
    private boolean forceUpdate = true;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Const.NEW_DATA)) {
                if (forceUpdate) {
                    initializeView();
                    initializeVM();
                    return;
                }

                Snackbar mBar = Snackbar.make(mBinding.getRoot(), "Penyimpanan Lokal Telah Diperbarui", BaseTransientBottomBar.LENGTH_LONG);
                mBar.setAction("TAMPILKAN", view -> {
                    mBar.dismiss();
                    updateData(queueUpdates);
                });
                mBar.show();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityListMoviesBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        initializeService();

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mReceiver, createFilter());
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private void initializeView() {
        mBinding.progress.setVisibility(View.GONE);
    }

    private void initializeVM() {
        mViewModel = new ViewModelProvider(this).get(ListMoviesViewModel.class);

        mViewModel.getLocalMovies();
        mViewModel.getMovies().observe(ListMoviesActivity.this, moviesDomainModels -> {
            if (forceUpdate) {
                forceUpdate = false;
                updateData(moviesDomainModels);
            } else {
                queueUpdates.clear();
                queueUpdates.addAll(moviesDomainModels);
            }
        });
    }

    private void updateData(List<MoviesDomainModel> movies) {
        mAdapter = new MoviesAdapter(movies);
        mBinding.moviesRV.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mBinding.moviesRV.setAdapter(mAdapter);
    }

    private void initializeService() {
        startService(new Intent(this, MoviesService.class));
    }

    private IntentFilter createFilter() {
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(Const.NEW_DATA);
        mFilter.addAction(Const.ERROR_DATA);

        return mFilter;
    }
}
