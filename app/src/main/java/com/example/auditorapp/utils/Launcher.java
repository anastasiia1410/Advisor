package com.example.auditorapp.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;

public class Launcher {
    public static void startCamera(Fragment fragment, int requestCode, File file) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri photoURI = FileProvider.getUriForFile(fragment.requireContext(),
                "com.example.auditorapp.fileprovider",
                file);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        fragment.startActivityForResult(takePictureIntent, requestCode);
    }
}
