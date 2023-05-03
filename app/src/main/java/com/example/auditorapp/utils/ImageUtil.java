package com.example.auditorapp.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageUtil {
    public static void load(ImageView image, String url) {
        Glide.with(image).load(url).fitCenter().centerCrop().into(image);
    }
}
