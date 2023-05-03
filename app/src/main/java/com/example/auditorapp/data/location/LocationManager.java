package com.example.auditorapp.data.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import com.example.auditorapp.utils.AddressUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import io.reactivex.rxjava3.core.Single;


public class LocationManager {
    private final FusedLocationProviderClient fusedLocationProviderClient;
    private final Geocoder geocoder;

    public LocationManager(Context context) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        geocoder = new Geocoder(context, Locale.getDefault());
    }

    @SuppressLint("MissingPermission")
    public Single<Location> getLastKnownLocation() {
        return Single.create(emitter -> fusedLocationProviderClient.getCurrentLocation
                        (Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener(location -> {
                    if (!emitter.isDisposed() && location != null) {
                        emitter.onSuccess(location);
                    }
                })
                .addOnFailureListener(e -> {
                    if (!emitter.isDisposed()) {
                        emitter.onError(e);
                    }

                })
                .addOnCanceledListener(() -> {
                    if (!emitter.isDisposed()) {
                        emitter.onError(new CancellationException());
                    }
                }));

    }

    public Single<String> getAddress(LatLng latLng) {
        return Single.fromCallable(() -> {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            Address address = addresses.get(0);
            return AddressUtil.getAddress(address);
        });

    }
}
