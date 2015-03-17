package com.sample.viewdrophelper.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.sample.viewdrophelper.view.OffsetLayout;


public class DragContentActivity extends Activity {
    OffsetLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_layout);

        container = (OffsetLayout)findViewById(R.id.container);
        OffsetLayout.LayoutParams lp = (OffsetLayout.LayoutParams) container.getChildAt(0).getLayoutParams();
        lp.setOffset(0, 0);

        Intent intent = getIntent();
        //TODO
    }
}
