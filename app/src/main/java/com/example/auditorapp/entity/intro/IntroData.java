package com.example.auditorapp.entity.intro;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public class IntroData {
    @DrawableRes
    private int image;
    @StringRes
    private int text;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getText() {
        return text;
    }

    public void setText(int text) {
        this.text = text;
    }
}
