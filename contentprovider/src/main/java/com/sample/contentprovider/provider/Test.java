package com.sample.contentprovider.provider;

/**
 * Created by vincent on 2015/3/18.
 */
public class Test {
    public static final String ID_TAG = "id";
    public static final String NAME_TAG = "name";
    public static final String[] PROJECTION = new String[]{
            ID_TAG,
            NAME_TAG
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
