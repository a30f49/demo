package com.sample.app;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView
import com.sample.groovy.model.GroovyDevice

/**
 * Created by vincent on 2015/3/6 0006.
 */
public class Main extends Activity {
    private static final String TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout viewGroup = new LinearLayout(this);
        viewGroup.setLayoutParams(new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        viewGroup.setOrientation(LinearLayout.VERTICAL);

        GroovyDevice groovyDevice = new GroovyDevice(1, "device");
        Log.i(TAG, "groovyDevice:" + groovyDevice.getName());

        com.sample.model.Device device = new com.sample.model.Device(1, "device");

        TextView textView = new TextView(this);
        textView.setText(device.getName());
        viewGroup.addView(textView);

        setContentView(viewGroup);
        Log.d(TAG, "onCreate()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "onDestroy()");
    }
}
