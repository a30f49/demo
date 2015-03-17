package com.sample.viewdrophelper.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.sample.viewdrophelper.view.PullToRefreshLayout;


public class PullToRefreshActivity extends Activity {

    PullToRefreshLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh_layout);

        container = (PullToRefreshLayout) findViewById(R.id.container);

        container.setOnRefreshListener(new RefreshCallbacks());
    }


    class RefreshCallbacks implements PullToRefreshLayout.OnRefreshListener{

        @Override
        public void onRefresh() {
            new android.os.Handler().post(new Runnable() {
                @Override
                public void run() {

                    //container.setRefresh(false);
                }
            });
        }
    }
}
