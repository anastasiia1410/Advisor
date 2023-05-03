package com.example.auditorapp.core;

import android.app.Application;
import android.content.Context;


import com.example.auditorapp.data.network.NetworkManager;
import com.example.auditorapp.data.location.LocationManager;
import com.example.auditorapp.data.db.Database;

public class App extends Application {
    private NetworkManager manager;
    private AppPreference appPreference;
    private Database database;
    private LocationManager locationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        appPreference = new AppPreference(this);
        manager = new NetworkManager(this);
        database = new Database(this);
        locationManager = new LocationManager(this);
    }

    public NetworkManager getManager() {
        return manager;
    }

    public AppPreference getAppPreference() {
        return appPreference;
    }

    public Database getDatabase() {
        return database;
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public static App getInstance(Context context) {
        return ((App) context.getApplicationContext());
    }
}
