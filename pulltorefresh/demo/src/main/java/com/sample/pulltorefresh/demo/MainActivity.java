package com.sample.pulltorefresh.demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import com.dianjiang.plugin.widget.PullToRefreshLayout;
import com.sample.pulltorefresh.demo.R;

public class MainActivity extends ActionBarActivity{
	private static final String TAG = "PULL_TO_REFRESH";

	private PullToRefreshLayout mPullToRefresh;
	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mPullToRefresh = (PullToRefreshLayout) findViewById(R.id.refresher);
		mListView = (ListView) findViewById(android.R.id.list);
		mPullToRefresh.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				Log.i(TAG, "onRefresh");
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						mPullToRefresh.setRefreshing(false);
					}
				}, 1000);
			}

			@Override
			public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
				Log.i(TAG, "onLoadMore");
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						mPullToRefresh.setLoading(false);
					}
				}, 1000);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
}
