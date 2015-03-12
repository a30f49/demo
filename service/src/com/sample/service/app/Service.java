package com.sample.service.app;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by vincent on 2015/3/6 0006.
 */
public class Service extends IntentService {
    private static final String TAG = "com.sample.service.app.Service";

    public Service(){
        super(TAG);
        Log.i(TAG, "default construct Service()");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public Service(String name) {
        super(name);
        Log.i(TAG, "default construct Service(String name):name="+name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "start onHandleIntent()");

        Context context = getApplicationContext();

        int width = ScreenSize.getScreenWidth(context);
        int height = ScreenSize.getScreenHeight(context);

        Log.i(TAG, "Screen Width: " + width);
        Log.i(TAG, "Screen Height:" + height);

        String deviceInfo = DeviceUtils.getDeviceInfo(context);
        Log.i(TAG, "Device Info:" + deviceInfo);

        Log.i(TAG, "stop itself...");
        stopSelf();
        Log.i(TAG, "stop!");
    }
}
