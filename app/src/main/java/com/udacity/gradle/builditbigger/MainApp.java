package com.udacity.gradle.builditbigger;

import android.app.Application;

import timber.log.Timber;

public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (com.iamzhaoyuan.android.jokedisplay.BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        // TODO Add release level log tree
    }
}
