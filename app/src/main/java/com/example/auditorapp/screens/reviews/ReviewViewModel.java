package com.example.auditorapp.screens.reviews;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.auditorapp.core.App;
import com.example.auditorapp.core.BaseViewModel;
import com.example.auditorapp.data.network.NetworkManager;
import com.example.auditorapp.entity.review.Review;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ReviewViewModel extends BaseViewModel {
    private final NetworkManager networkManager;
    private final MutableLiveData<List<Review>> reviewLD = new MutableLiveData<>();
    private final MutableLiveData<Throwable> throwableLD = new MutableLiveData<>();

    public ReviewViewModel(@NonNull Application application) {
        super(application);
        networkManager = App.getInstance(application.getApplicationContext()).getManager();
        getReviews();
    }

    public void getReviews(){
        Disposable disposable = networkManager.getReviews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Review>>() {
                    @Override
                    public void accept(List<Review> value) throws Throwable {
                        reviewLD.postValue(value);
                    }
                }, throwable -> {
                    if(throwable instanceof NullPointerException){
                        throwableLD.postValue(throwable);
                    }
                });
        compositeDisposable.add(disposable);
    }


    public LiveData<List<Review>> getReviewLD() {
        return reviewLD;
    }

    public LiveData<Throwable> getThrowableLD() {
        return throwableLD;
    }
}
