package com.example.auditorapp.core;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference {
    private static final String APP_PREFERENCE_NAME = "app.preferences";
    private static final String TOKEN_KEY = "token_key";
    private final SharedPreferences isFirstStart;
    private static final String IS_FIRST_START = "first_start.preferences";
    private static final String START_KEY = "start_key";
    private final SharedPreferences sharedPreferences;


    public AppPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        isFirstStart = context.getSharedPreferences(IS_FIRST_START, Context.MODE_PRIVATE);
    }


    public boolean getStart() {
        return isFirstStart.getBoolean(START_KEY, true);
    }

    public void setStart(boolean value) {
        isFirstStart.edit()
                .putBoolean(START_KEY, value)
                .apply();
    }

    public String getToken() {
        return sharedPreferences.getString(TOKEN_KEY, null);
    }


    public void saveToken(String token) {
        sharedPreferences.edit()
                .putString(TOKEN_KEY, token)
                .apply();
    }
}
