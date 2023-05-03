package com.example.auditorapp.data.data_provider;

import com.example.auditorapp.R;
import com.example.auditorapp.entity.intro.IntroData;

import java.util.ArrayList;
import java.util.List;

public class DataProvider {
    public static List<IntroData> createScreens() {
        List<IntroData> dataList = new ArrayList<>();
        dataList.add(createFirstScreen());
        dataList.add(createSecondScreen());
        dataList.add(createThirdScreen());

        return dataList;
    }

    private static IntroData createFirstScreen() {
        IntroData data = new IntroData();
        data.setImage(R.drawable.frame_20);
        data.setText(R.string.text);

        return data;
    }

    private static IntroData createSecondScreen() {
        IntroData data = new IntroData();
        data.setImage(R.drawable.frame_20__1_);
        data.setText(R.string.text);

        return data;
    }

    private static IntroData createThirdScreen() {
        IntroData data = new IntroData();
        data.setImage(R.drawable.frame_20__2_);
        data.setText(R.string.text);

        return data;
    }
}
