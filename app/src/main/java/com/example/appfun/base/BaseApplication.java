package com.example.appfun.base;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by zx on 2016/3/4.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
        super.onCreate();
    }
}
