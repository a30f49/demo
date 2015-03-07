package com.sample.app;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by vincent on 2015/3/6 0006.
 */
public class Main extends Activity {
    private static final String TAG = "com.sample.app.Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Activity onCreate()");
        LinearLayout viewGroup = new LinearLayout(this);
        viewGroup.setLayoutParams(new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        setContentView(viewGroup);

        Log.i(TAG, "onCreate() try to finish");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "on activity destroy");
    }
}
