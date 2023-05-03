package com.example.auditorapp.utils;

import android.location.Address;

public class AddressUtil {
    public static String getAddress(Address address) {
        String str;
        if (address.getSubThoroughfare() != null) {
            str = address.getPostalCode() + ", " + address.getLocality()
                    + ", " + address.getThoroughfare()
                    + ", " + address.getSubThoroughfare();
        }else {
            str = address.getPostalCode() + ", " + address.getLocality()
                    + ", " + address.getSubLocality();
        }

        return str;
    }
}
