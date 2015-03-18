package com.sample.contentprovider.app;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.sample.contentprovider.app.R;
import com.sample.contentprovider.provider.Test;
import com.sample.contentprovider.provider.Tests;

import java.util.logging.Handler;


public class MainActivity extends Activity {

    Button mActionTest;
    View mViewProgress;
    TextView mViewId;
    TextView mViewName;

    ProviderQueryHandler providerHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActionTest = (Button)findViewById(R.id.action_test);
        mViewProgress = findViewById(R.id.progress);
        mViewId = (TextView) findViewById(R.id.item_id);
        mViewName = (TextView) findViewById(R.id.item_name);

        mActionTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onQueryProvider();
            }
        });

        providerHandler = new ProviderQueryHandler(getContentResolver());
    }

    void onQueryProvider(){
        providerHandler.startQuery(0, null, Tests.CONTENT_URI, Test.PROJECTION, null, null, null);
        mViewProgress.setVisibility(View.VISIBLE);
    }


    /**
     * class ProviderQueryHandler
     */
    class ProviderQueryHandler extends AsyncQueryHandler {

        public ProviderQueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            super.onQueryComplete(token, cookie, cursor);

            cursor.moveToFirst();
            final long id = cursor.getLong(cursor.getColumnIndex(Test.ID_TAG));
            final String name = cursor.getString(cursor.getColumnIndex(Test.NAME_TAG));

            // update UI in current UI process
            new android.os.Handler().post(new Runnable() {
                @Override
                public void run() {
                    mViewProgress.setVisibility(View.GONE);
                    mViewId.setText(String.valueOf(id));
                    mViewName.setText(name);
                }
            });
        }
    }
}
