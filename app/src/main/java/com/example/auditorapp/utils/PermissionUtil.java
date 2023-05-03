package com.example.auditorapp.utils;

import java.util.Map;

public class PermissionUtil {
    public static boolean getOrDefault(Map<String, Boolean> result, String permission) {
        Boolean permissionGranted = result.get(permission);
        if (permissionGranted == null) {
            return false;
        }
        return permissionGranted;
    }
}
