package com.example.auditorapp.screens.create_reviews;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.auditorapp.core.App;
import com.example.auditorapp.core.BaseViewModel;
import com.example.auditorapp.data.db.Database;
import com.example.auditorapp.entity.DraftStatus;
import com.example.auditorapp.entity.drafts.Drafts;
import com.example.auditorapp.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CreateViewModel extends BaseViewModel {
    private final Database database;
    private final MutableLiveData<File> fileLD = new MutableLiveData<>();
    private final MutableLiveData<Boolean> reviewLD = new MutableLiveData<>();


    public CreateViewModel(Application application) {
        super(application);
        database = App.getInstance(application.getApplicationContext()).getDatabase();

        try {
            File file = FileUtil.createTempFile(application.getApplicationContext());
            fileLD.postValue(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertReview(String title, String text, String address, String path) {
        Drafts drafts = new Drafts();
        drafts.setTitle(title);
        drafts.setTextReview(text);
        drafts.setDate(new Date());
        drafts.setLocation(address);
        drafts.setStatus(DraftStatus.ReadyToSend);
        drafts.setImage(path);

        Disposable disposable = database.insertReview(drafts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    reviewLD.postValue(true);
                    CreateViewModel.this.clearFile();
                });

        compositeDisposable.add(disposable);
    }


    public void clearFile() {
        fileLD.postValue(null);
    }

    public LiveData<File> getFileLD() {
        return fileLD;
    }

    public LiveData<Boolean> getReviewLD() {
        return reviewLD;
    }


}
