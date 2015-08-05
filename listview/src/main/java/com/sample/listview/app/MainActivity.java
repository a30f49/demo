package com.sample.listview.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

    ListView mList;
    ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mList = (ListView) findViewById(android.R.id.list);

        View header = getLayoutInflater().inflate(R.layout.list_view_header, null);
        mList.addHeaderView(header, null, false);

        String[] cities = getResources().getStringArray(R.array.cities);
        mAdapter = new ArrayAdapter<>(this, R.layout.list_item, cities);

        mList.setAdapter(mAdapter);
    }
}
