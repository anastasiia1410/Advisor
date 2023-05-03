package com.example.auditorapp.screens.drafts;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.auditorapp.core.App;
import com.example.auditorapp.core.BaseViewModel;
import com.example.auditorapp.data.db.Database;
import com.example.auditorapp.data.network.NetworkManager;
import com.example.auditorapp.entity.DraftStatus;
import com.example.auditorapp.entity.drafts.Drafts;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DraftsViewModel extends BaseViewModel {
    private final Database database;
    private final NetworkManager networkManager;
    private final MutableLiveData<List<Drafts>> draftsLD = new MutableLiveData<>();
    private final MutableLiveData<Throwable> throwableLD = new MutableLiveData<>();


    public DraftsViewModel(@NonNull Application application) {
        super(application);
        database = App.getInstance(application.getApplicationContext()).getDatabase();
        networkManager = App.getInstance(application.getApplicationContext()).getManager();
        getDraftsReviews();
    }

    public void getDraftsReviews() {
        Disposable disposable = database.getListDraftsAsync()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(draftsLD::postValue);
        compositeDisposable.add(disposable);
    }

    public void sendDrafts(Drafts drafts) {
        Disposable disposable = networkManager.getCurrentUser()
                .concatMapCompletable(user ->
                        networkManager.sendDrafts(
                                user.getUserName(),
                                drafts.getTitle(),
                                drafts.getTextReview()
                                )
                )
                .toSingle(() -> true)
                .concatMap(aBoolean -> database.updateStatusAsync(drafts, DraftStatus.Sent))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription -> {
                    drafts.setStatus(DraftStatus.Sending);
                    draftsLD.postValue(draftsLD.getValue());
                })
                .subscribe(draft -> {
                    draft.setStatus(DraftStatus.Sent);
                    draftsLD.postValue(draftsLD.getValue());
                }, throwable -> {
                    if (throwable instanceof UnknownHostException ||
                            throwable instanceof SocketTimeoutException) {
                        throwableLD.postValue(throwable);
                    }
                    drafts.setStatus(DraftStatus.ReadyToSend);
                    draftsLD.postValue(draftsLD.getValue());
                });

        compositeDisposable.add(disposable);

    }

    public LiveData<List<Drafts>> getDraftsLD() {
        return draftsLD;
    }

    public LiveData<Throwable> getThrowableLD() {
        return throwableLD;
    }

}
