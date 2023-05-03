package com.example.auditorapp.screens.setting;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.auditorapp.core.App;
import com.example.auditorapp.core.AppPreference;
import com.example.auditorapp.core.BaseViewModel;
import com.example.auditorapp.data.network.NetworkManager;

import io.reactivex.rxjava3.disposables.Disposable;

public class SettingViewModel extends BaseViewModel {
    private final NetworkManager networkManager;
    private final AppPreference appPreference;

    public SettingViewModel(@NonNull Application application) {
        super(application);
        networkManager = App.getInstance(application.getApplicationContext()).getManager();
        appPreference = App.getInstance(application.getApplicationContext()).getAppPreference();
        clearCurrentUser();
    }

    private void clearCurrentUser() {
        Disposable disposable = networkManager.getCurrentUser()
                .subscribe(value -> {
                    value.clearUser(value);
                    appPreference.saveToken(null);
                });

        compositeDisposable.add(disposable);
    }
}
