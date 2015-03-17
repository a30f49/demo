package com.sample.database.app;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.sample.database.app.R;


public class MainActivity extends Activity {

    Database mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDB = new Database(this, Database.NAME, Database.VERSION);
        SQLiteDatabase db = mDB.getReadableDatabase();
        db.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(UserTable.COLUMN_ID, 1);
        values.put(UserTable.COLUMN_NAME, "jacky");

        db.insert(UserTable.TABLE_NAME, null, values);

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public SQLiteOpenHelper getDatabase(){
        return mDB;
    }
}
