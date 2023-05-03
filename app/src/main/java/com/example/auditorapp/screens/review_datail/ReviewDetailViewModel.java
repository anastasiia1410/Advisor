package com.example.auditorapp.screens.review_datail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.auditorapp.core.App;
import com.example.auditorapp.core.BaseViewModel;
import com.example.auditorapp.data.network.NetworkManager;
import com.example.auditorapp.entity.review.Review;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ReviewDetailViewModel extends BaseViewModel {
    private final NetworkManager networkManager;
    private final MutableLiveData<Review> reviewLD = new MutableLiveData<>();

    public ReviewDetailViewModel(@NonNull Application application) {
        super(application);
        networkManager = App.getInstance(application.getApplicationContext()).getManager();
    }

    public void getReviewDetail(String objectId) {
        Disposable disposable = networkManager.getReviewsDetail(objectId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reviewLD::postValue);

        compositeDisposable.add(disposable);
    }

    public void deleteReview(String objectId) {
        Disposable disposable = networkManager.deleteReview(objectId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();

        compositeDisposable.add(disposable);
    }

    public LiveData<Review> getReviewLD() {
        return reviewLD;
    }
}
