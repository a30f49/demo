package com.sample.database.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by vincent on 2015/3/17.
 */

@RunWith(RobolectricTestRunner.class)
public class DatabaseTest {

    @Test
    public void test_Database(){
        Context context = Robolectric.getShadowApplication().getApplicationContext();

        Database database = new Database(context, Database.NAME, Database.VERSION);

        SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(UserTable.COLUMN_ID, 1);
        values.put(UserTable.COLUMN_NAME, "jacky");

        db.insert(UserTable.TABLE_NAME, null, values);

        db.setTransactionSuccessful();
        db.endTransaction();


        Cursor cursor = db.query(UserTable.TABLE_NAME, UserTable.PROJECTION, null, null, null, null, null);
        assertTrue(cursor!=null);
        assertEquals(1, cursor.getCount());

        cursor.moveToFirst();
        long id = cursor.getLong(cursor.getColumnIndex(UserTable.COLUMN_ID));
        String name = cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_NAME));
        assertEquals(1, id);
        assertEquals("jacky", name);
    }
}
