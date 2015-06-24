package com.sample.actionbar;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private static final int SHOW_ICON_TOKEN = 1;
    private static final int SHOW_LOGO_TOKEN = 2;
    private static final int SHOW_AS_UP_TOKEN = 3;
    private static final int SHOW_TITLE_CENTER_TOKEN = 4;

    private int token = SHOW_TITLE_CENTER_TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_actionbar);

        ActionBar actionBar = getSupportActionBar();

        switch (token) {
            case SHOW_ICON_TOKEN:
                // show icon
                actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
                actionBar.setIcon(R.drawable.ic_launcher);
                break;

            case SHOW_LOGO_TOKEN:
                /// show logo
                actionBar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
                actionBar.setLogo(R.drawable.abc_btn_radio_material);
                break;

            case SHOW_AS_UP_TOKEN:
                /// show as up
                actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
                actionBar.setIcon(R.drawable.ic_launcher);
                break;

            case SHOW_TITLE_CENTER_TOKEN:
                actionBar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM);
                actionBar.setLogo(R.drawable.abc_btn_radio_material);

                LayoutInflater inflater = LayoutInflater.from(this);
                View v = inflater.inflate(R.layout.custom_action_bar, null);
                TextView title = (TextView) v.findViewById(R.id.title);
                actionBar.setCustomView(v);
                title.setText(R.string.app_title);
                break;

            default:break;
        }
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
        if (id == R.id.action_toolbar) {
            Intent intent = new Intent(this, MainActivityToolbar.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
