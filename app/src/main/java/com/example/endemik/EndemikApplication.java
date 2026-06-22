package com.example.endemik;

import android.app.Application;

import com.example.endemik.util.AppPreferences;

public class EndemikApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppPreferences.applyStoredSettings(this);
    }
}
