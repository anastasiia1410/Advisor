package com.example.auditorapp.utils;

import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TextUtil {
    public static String inputText(TextView view) {
        CharSequence charSequence = view.getText();
        if (charSequence == null) {
            return "";
        } else {
            return charSequence.toString();
        }
    }

    public static String getFullDateInStr(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd.yyyy", Locale.ENGLISH);
        return simpleDateFormat.format(date);
    }
}
