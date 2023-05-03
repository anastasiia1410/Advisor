package com.example.auditorapp.screens.intro;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.auditorapp.core.BaseViewModel;
import com.example.auditorapp.data.data_provider.DataProvider;
import com.example.auditorapp.entity.intro.IntroData;

import java.util.List;

public class IntroViewModel extends BaseViewModel {
    private final List<IntroData> dataList = DataProvider.createScreens();
    private final MutableLiveData<List<IntroData>> introDataLD = new MutableLiveData<>();

    public IntroViewModel(@NonNull Application application) {
        super(application);
    }

    public void getIntroData() {
        introDataLD.postValue(dataList);
    }

    public LiveData<List<IntroData>> getIntroDataLD() {
        return introDataLD;
    }
}
