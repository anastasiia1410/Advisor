package com.example.auditorapp.screens.authorization;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.auditorapp.core.App;
import com.example.auditorapp.core.BaseViewModel;
import com.example.auditorapp.entity.user.User;
import com.example.auditorapp.data.network.NetworkManager;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class AuthorizationViewModel extends BaseViewModel {
    private final NetworkManager manager;
    private final MutableLiveData<String> tokenLD = new MutableLiveData<>();
    private final MutableLiveData<Throwable> noInternetErrorLD = new MutableLiveData<>();
    private final MutableLiveData<Throwable> timeOutErrorLD = new MutableLiveData<>();
    private final MutableLiveData<Boolean> setTextLD = new MutableLiveData<>();


    public AuthorizationViewModel(@NonNull Application application) {
        super(application);
        manager = App.getInstance(application.getApplicationContext()).getManager();
    }

    public void getLoggingUser(User user) {
        Disposable disposable = manager.logging(user.getUserName(), user.getPassword())
                .doOnSubscribe(disposable1 -> setTextLD.postValue(true))
                .doFinally(() -> setTextLD.postValue(false))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String value) throws Throwable {
                        tokenLD.postValue(value);
                    }
                }, throwable -> {
                    if (throwable instanceof UnknownHostException) {
                        noInternetErrorLD.postValue(throwable);
                    } else if (throwable instanceof SocketTimeoutException) {
                        timeOutErrorLD.postValue(throwable);
                    }
                });
        compositeDisposable.add(disposable);
    }

    public MutableLiveData<String> getTokenLD() {
        return tokenLD;
    }

    public MutableLiveData<Throwable> getNoInternetErrorLD() {
        return noInternetErrorLD;
    }

    public MutableLiveData<Throwable> getTimeOutErrorLD() {
        return timeOutErrorLD;
    }

    public LiveData<Boolean> getSetTextLD() {
        return setTextLD;
    }
}
