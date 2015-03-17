package com.sample.database.app;

/**
 * Created by vincent on 2015/3/17.
 */
public class UserTable {
    public static final String TABLE_NAME = "user";
    public static final String COLUMN_SQLITE_ID = "_id";
    public static final String COLUMN_ID  = "id";
    public static final String COLUMN_NAME = "name";

    // Database creation SQL statement
    public static final String CREATE_TABLE = "create table "
            + TABLE_NAME
            + "("
            + COLUMN_SQLITE_ID + " integer primary key autoincrement, "
            + COLUMN_ID + " integer not null, "
            + COLUMN_NAME + " text not null"
            +");";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String[] PROJECTION = new String[]{
            COLUMN_SQLITE_ID,
            COLUMN_ID,
            COLUMN_NAME
    };

    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
