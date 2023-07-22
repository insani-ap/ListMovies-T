package com.t.listmovies.di;

import android.content.Context;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.room.Room;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.t.listmovies.data.local.database.MoviesDatabase;
import com.t.listmovies.util.AppExecutors;
import com.t.listmovies.util.Const;
import com.t.listmovies.util.Preferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class ApplicationModule {
    @Singleton
    @Provides
    public Retrofit provideRetrofit() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

        return new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();
    }

    @Provides
    Context provideContext(@ApplicationContext Context mContext) {
        return mContext;
    }

    @Singleton
    @Provides
    public MoviesDatabase provideDatabase(Context mContext) {
        return Room.databaseBuilder(mContext, MoviesDatabase.class, MoviesDatabase.DATABASE_NAME)
                .build();
    }

    @Provides
    public AppExecutors provideAppExecutors() {
        return new AppExecutors();
    }

    @Singleton
    @Provides
    public Preferences providePreferences(Context mContext) {
        return new Preferences(mContext);
    }

    @Singleton
    @Provides
    public LocalBroadcastManager provideLocalBroadcast(Context mContext) {
        return LocalBroadcastManager.getInstance(mContext);
    }
}
