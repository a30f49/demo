package com.sample.recyclerview.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.sample.recyclerview.R;
import com.sample.recyclerview.model.DummyTodoList;
import com.sample.recyclerview.viewmodel.TodoAdapter;


public class MainActivity extends ActionBarActivity {

    private TodoAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);

        mAdapter = new TodoAdapter(new DummyTodoList(), R.layout.todo_item, this);
        mRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*public boolean isViewTop(ViewGroup parent, View view) {
        boolean apply = false;
        if (view != null && view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            LayoutManager layout = recyclerView.getLayoutManager();
            if (layout != null && layout instanceof LinearLayoutManager) {
                LinearLayoutManager layoutManager = ((LinearLayoutManager) layout);
                int orientation = layoutManager.getOrientation();
                if (orientation == LinearLayoutManager.VERTICAL) {
                    View child = layoutManager.getChildAt(0);
                    if (child != null) {
                        int position = recyclerView.getChildPosition(child);
                        if (position == 0) {
                            apply = child.getTop() >= recyclerView.getTop();
                        }
                    } else {
                        apply = true;
                    }
                }
            }
        }
        return apply;
    }*/
}
