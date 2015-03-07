package com.sample.app;

import android.app.Application;
import android.util.Log;

public class App extends Application{
    private static final String TAG = "com.sample.app.App";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "App onCreate()");
    }

    @Override
    public void onTerminate(){
       Log.i(TAG, "onTerminate()");
    }     
}
