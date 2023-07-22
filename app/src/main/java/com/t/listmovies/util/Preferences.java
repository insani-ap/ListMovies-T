package com.t.listmovies.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class Preferences {
    private SharedPreferences sharedPreferences;

    public Preferences(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    public int getPageSize() {
        return sharedPreferences.getInt(Const.PAGE_SIZE, 1);
    }

    public void setPageSize(int pageSize) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Const.PAGE_SIZE, pageSize);
        editor.apply();
    }
}
