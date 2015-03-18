package com.sample.contentprovider.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

public class TestProvider extends ContentProvider {
    private static final String TAG = "TestProvider";

    private static final String DIR_TYPE = appendEncodedPath(
            ContentResolver.CURSOR_DIR_BASE_TYPE, Tests.RESOURCE);
    private static final String ITEM_TYPE = appendEncodedPath(
            ContentResolver.CURSOR_ITEM_BASE_TYPE, Tests.RESOURCE);

    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI(Tests.AUTHORITY, Tests.Matches.PATH_ITEMS, Tests.Matches.ITEMS);
        matcher.addURI(Tests.AUTHORITY, Tests.Matches.PATH_ITEM, Tests.Matches.ITEM);
    }

    static String appendEncodedPath(String typeString, String pathString){
        return new StringBuilder()
                .append(typeString).append("/").append(pathString)
                .toString();
    }

    public TestProvider() {
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)){
            case Tests.Matches.ITEMS:
                return DIR_TYPE;
            case Tests.Matches.ITEM:
                return ITEM_TYPE;
            default:break;
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (matcher.match(uri)){
            case Tests.Matches.ITEMS:
                Log.i(TAG, "insert() match ITEMS");
                break;
            default:break;
        }

        return null;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        MatrixCursor cursor = new MatrixCursor(new String[]{Test.ID_TAG, Test.NAME_TAG});

        switch (matcher.match(uri)){
            case Tests.Matches.ITEMS:
                cursor.addRow(new Object[]{1, "apple"});
                cursor.addRow(new Object[]{2, "orange"});
                break;

            case Tests.Matches.ITEM:
                cursor.addRow(new Object[]{10, "paper"});
                break;

            default:break;
        }

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
