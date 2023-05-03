package com.example.auditorapp.screens.drafts_detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.auditorapp.core.App;
import com.example.auditorapp.core.AppPreference;
import com.example.auditorapp.core.BaseViewModel;
import com.example.auditorapp.data.db.Database;
import com.example.auditorapp.data.network.NetworkManager;
import com.example.auditorapp.entity.DraftStatus;
import com.example.auditorapp.entity.drafts.DraftAndUser;
import com.example.auditorapp.entity.drafts.Drafts;
import com.example.auditorapp.entity.user.User;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class DetailsViewModel extends BaseViewModel {
    private final Database database;
    private final NetworkManager networkManager;
    private final MutableLiveData<Drafts> reviewLD = new MutableLiveData<>();
    private final MutableLiveData<Throwable> throwableLD = new MutableLiveData<>();

    public DetailsViewModel(@NonNull Application application) {
        super(application);
        database = App.getInstance(application.getApplicationContext()).getDatabase();
        networkManager = App.getInstance(application.getApplicationContext()).getManager();
    }

    public void getDetailInfo(String title) {
        Disposable disposable = database.getDetailReviewAsync(title)
                .subscribe(reviewLD::postValue);

        compositeDisposable.add(disposable);
    }

    public void sendDrafts(Drafts drafts) {
        Disposable disposable = networkManager.getCurrentUser()
                .concatMapCompletable(user ->
                     networkManager.sendDrafts(
                             drafts.getTitle(),
                           drafts.getTextReview(),
                           user.getUserName())

        )

                .toSingle(() -> true)
                .concatMap(aBoolean -> database.updateStatusAsync(drafts, DraftStatus.Sent))
                .doOnSubscribe(subscription -> {
                    drafts.setStatus(DraftStatus.Sending);
                    reviewLD.postValue(reviewLD.getValue());
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(drafts1 -> {
                    drafts1.setStatus(DraftStatus.Sent);
                    reviewLD.postValue(reviewLD.getValue());
                }, throwable -> {
                    drafts.setStatus(DraftStatus.ReadyToSend);
                    reviewLD.postValue(reviewLD.getValue());
                });

        compositeDisposable.add(disposable);

    }

    public LiveData<Drafts> getReviewLD() {
        return reviewLD;
    }

    public LiveData<Throwable> getThrowableLD() {
        return throwableLD;
    }
}
