package com.example.auditorapp.screens.reviews;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.auditorapp.core.BaseViewModel;

public class DrawerActionViewModel extends BaseViewModel {
    private final MutableLiveData<DrawerTabs> isButtonClickLD = new MutableLiveData<>(DrawerTabs.None);

    public DrawerActionViewModel(@NonNull Application application) {
        super(application);
    }

    public void onReviewsClick() {
        isButtonClickLD.postValue(DrawerTabs.Reviews);
    }

    public void onDraftsClick() {
        isButtonClickLD.postValue(DrawerTabs.Drafts);
    }

    public void onSettingClick() {
        isButtonClickLD.postValue(DrawerTabs.Settings);
    }

    public void onClearButtonClick() {
        isButtonClickLD.postValue(DrawerTabs.None);
    }

    public LiveData<DrawerTabs> getIsButtonClickLD() {
        return isButtonClickLD;
    }
}
