package com.sample.acra.app;

import android.app.Activity;
import android.os.Bundle;
import com.sample.acra.app.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        throw new RuntimeException("Test acra exception.");
    }
}
