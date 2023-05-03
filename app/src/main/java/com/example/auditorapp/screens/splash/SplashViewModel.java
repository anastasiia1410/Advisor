package com.example.auditorapp.screens.splash;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.auditorapp.core.App;
import com.example.auditorapp.core.AppPreference;
import com.example.auditorapp.core.BaseViewModel;
import com.example.auditorapp.entity.StartScreen;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SplashViewModel extends BaseViewModel {
    private final AppPreference appPreference;
    private final MutableLiveData<StartScreen> screensLD = new MutableLiveData<>();


    public SplashViewModel(@NonNull Application application) {
        super(application);
        appPreference = App.getInstance(application.getApplicationContext()).getAppPreference();
    }

    public void startTick() {
        Disposable disposable = Single.just(true)
                .delay(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if (appPreference.getStart()) {
                        screensLD.postValue(StartScreen.IntroScreen);
                        appPreference.setStart(false);
                    } else if (appPreference.getToken() != null) {
                        screensLD.postValue(StartScreen.HomeScreen);
                    } else {
                        screensLD.postValue(StartScreen.AuthorizationScreen);
                    }
                });
        compositeDisposable.add(disposable);
    }

    public MutableLiveData<StartScreen> getScreensLD() {
        return screensLD;
    }
}
