package com.sample.database.app;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by vincent on 2015/3/17.
 */
public class Database extends SQLiteOpenHelper {
    private static final String TAG = "database";
    public static final int VERSION = 1;
    public static final String NAME = "sample.db";

    public Database(Context context, String name, int version) {
        super(context, name, null, version);
    }

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.beginTransaction();
                createTables(db);
            db.setTransactionSuccessful();
            db.endTransaction();

            Log.i(TAG, "database is created.");
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.i(TAG, "failed to create database.");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(UserTable.class.getName(), "Upgrading Database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");

        try {
            db.beginTransaction();
                dropTables(db);
                createTables(db);
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    private void createTables(SQLiteDatabase db) throws SQLiteException {
        /// create status's table for http request
        db.execSQL(UserTable.CREATE_TABLE);
    }

    private void dropTables(SQLiteDatabase db) throws SQLiteException {
        /// create status's table for http request
        db.execSQL(UserTable.DROP_TABLE);
    }
}
