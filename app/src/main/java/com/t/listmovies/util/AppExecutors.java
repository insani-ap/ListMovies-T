package com.t.listmovies.util;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private static final int THREAD_COUNT = 3;

    private final Executor diskIO;

    private final Executor networkIO;

    private final Executor mainThread;

    private final Executor etcThread;

    AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread, Executor etcThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
        this.etcThread = etcThread;
    }

    public AppExecutors() {
        this(new DiskIOThreadExecutor(), Executors.newFixedThreadPool(THREAD_COUNT),
                new MainThreadExecutor(), new EtcThreadExecutor());
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor networkIO() {
        return networkIO;
    }

    public Executor mainThread() {
        return mainThread;
    }

    public Executor etcThread() {
        return etcThread;
    }

    private static class DiskIOThreadExecutor implements Executor {
        private final Executor mDiskIO = Executors.newSingleThreadExecutor();

        @Override
        public void execute(Runnable command) {
            mDiskIO.execute(command);
        }
    }

    private static class MainThreadExecutor implements Executor {
        private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }

    private static class EtcThreadExecutor implements Executor {
        private final Handler etcThreadHandler = new Handler();

        @Override
        public void execute(@NonNull Runnable command) {
            etcThreadHandler.post(command);
        }
    }
}
