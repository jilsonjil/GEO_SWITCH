package com.example.geo;

import android.app.Application;

import com.example.geo.utils.NotificationUtil;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new NotificationUtil(getApplicationContext()).createChannel();
    }
}
