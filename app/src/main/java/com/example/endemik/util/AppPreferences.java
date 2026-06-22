package com.example.endemik.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class AppPreferences {

    private static final String PREFS_NAME = "endemik_prefs";
    private static final String KEY_NIGHT_MODE = "night_mode";

    private AppPreferences() {
    }

    private static SharedPreferences prefs(Context context) {
        return context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static void applyStoredSettings(Context context) {
        applyNightMode(getNightMode(context));
    }

    public static int getNightMode(Context context) {
        return prefs(context).getInt(KEY_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    public static void setNightMode(Context context, int mode) {
        prefs(context).edit().putInt(KEY_NIGHT_MODE, mode).apply();
        applyNightMode(mode);
    }

    public static void toggleNightMode(Context context) {
        int currentMode = getNightMode(context);
        int nextMode;

        if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            nextMode = AppCompatDelegate.MODE_NIGHT_NO;
        } else if (currentMode == AppCompatDelegate.MODE_NIGHT_NO) {
            nextMode = AppCompatDelegate.MODE_NIGHT_YES;
        } else {
            int nightModeFlags = context.getResources().getConfiguration().uiMode
                    & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
            nextMode = nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES
                    ? AppCompatDelegate.MODE_NIGHT_NO
                    : AppCompatDelegate.MODE_NIGHT_YES;
        }

        setNightMode(context, nextMode);
    }

    private static void applyNightMode(int mode) {
        AppCompatDelegate.setDefaultNightMode(mode);
    }
}
