package com.iamzhaoyuan.android.jokedisplay;

import android.app.Application;

import timber.log.Timber;


public class JokeDisplayApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
