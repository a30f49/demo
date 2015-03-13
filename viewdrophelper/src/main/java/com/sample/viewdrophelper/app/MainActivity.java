package com.sample.viewdrophelper.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.sample.viewdrophelper.view.ContentLayout;


public class MainActivity extends Activity {
    ContentLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = (ContentLayout)findViewById(R.id.container);
        ContentLayout.LayoutParams lp = (ContentLayout.LayoutParams) container.getChildAt(0).getLayoutParams();
        lp.setOffset(0, 0);

        Intent intent = getIntent();
        //TODO
    }
}
