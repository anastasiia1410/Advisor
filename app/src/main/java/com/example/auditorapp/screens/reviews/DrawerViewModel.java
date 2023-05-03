package com.example.auditorapp.screens.reviews;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.auditorapp.core.App;
import com.example.auditorapp.core.BaseViewModel;
import com.example.auditorapp.entity.user.User;
import com.example.auditorapp.data.network.NetworkManager;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DrawerViewModel extends BaseViewModel {
    private final MutableLiveData<User> userLD = new MutableLiveData<>();
    private final NetworkManager manager;

    public DrawerViewModel(@NonNull Application application) {
        super(application);
        manager = App.getInstance(application.getApplicationContext()).getManager();
        loadCurrentUser();
    }

    public void loadCurrentUser() {
        Disposable disposable = manager.getCurrentUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userLD::postValue);
        compositeDisposable.add(disposable);
    }

    public LiveData<User> getUserLD() {
        return userLD;
    }
}
