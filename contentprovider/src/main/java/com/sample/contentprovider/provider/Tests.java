package com.sample.contentprovider.provider;

import android.net.Uri;

/**
 * Created by vincent on 2015/3/18.
 */
public class Tests {
    public static final String AUTHORITY = "com.sample.provider";
    public static final String RESOURCE = "test";

    public static final Uri CONTENT_URI = Uri.parse("content://com.sample.provider/test");


    static class Matches{
        static final String PATH_ITEMS = "test";
        static final String PATH_ITEM = "test/*";

        static final int ITEMS = 10;
        static final int ITEM = 11;
    }
}
