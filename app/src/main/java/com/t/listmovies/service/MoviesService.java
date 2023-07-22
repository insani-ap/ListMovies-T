package com.t.listmovies.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.t.listmovies.data.remote.movies.MoviesRepo;
import com.t.listmovies.model.response.BaseCallback;
import com.t.listmovies.util.Const;
import com.t.listmovies.util.Preferences;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MoviesService extends Service {
    @Inject
    MoviesRepo mRepo;
    @Inject
    Preferences mPreferences;
    @Inject
    LocalBroadcastManager mBroadcastManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startJob();

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startJob() {
        ScheduledExecutorService mScheduled = Executors.newScheduledThreadPool(1);
        mScheduled.scheduleAtFixedRate(() -> mRepo.getRemoteMovies(mPreferences.getPageSize(), new BaseCallback() {
            @Override
            public void onSuccess() {
                mPreferences.setPageSize(mPreferences.getPageSize() + 1);

                Intent intent = new Intent();
                intent.setAction(Const.NEW_DATA);
                mBroadcastManager.sendBroadcast(intent);
            }

            @Override
            public void onError() {
                mScheduled.shutdown();

                Intent intent = new Intent();
                intent.setAction(Const.ERROR_DATA);
                mBroadcastManager.sendBroadcast(intent);
            }
        }), 0L, 10L, TimeUnit.SECONDS);
    }
}
