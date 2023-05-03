package com.example.auditorapp.screens.map;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.auditorapp.core.App;
import com.example.auditorapp.core.BaseViewModel;
import com.example.auditorapp.data.location.LocationManager;
import com.google.android.gms.maps.model.LatLng;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class MapViewModel extends BaseViewModel {
    private final LocationManager locationManager;
    private final MutableLiveData<LatLng> latLngLD = new MutableLiveData<>();
    private final MutableLiveData<String> addressLD = new MutableLiveData<>();


    public MapViewModel(@NonNull Application application) {
        super(application);
        locationManager = App.getInstance(application.getApplicationContext()).getLocationManager();
    }

    public void loadCurrentLocation() {
        Disposable disposable = locationManager.getLastKnownLocation()
                .map(location ->
                        new LatLng(location.getLatitude(), location.getLongitude()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(latLngLD::postValue);

        compositeDisposable.add(disposable);
    }

    public void saveLocation(LatLng latlng){
       Disposable disposable = locationManager.getAddress(latlng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(addressLD::postValue);
       compositeDisposable.add(disposable);
    }

    public LiveData<LatLng> getLatLngLD() {
        return latLngLD;
    }

    public LiveData<String> getAddressLD() {
        return addressLD;
    }

}
