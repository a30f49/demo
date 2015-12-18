package com.example.huayu.fonticontest;

import android.app.Application;

import com.shamanland.fonticon.FontIconTypefaceHolder;

/**
 * Created by huayu on 2015/8/19.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FontIconTypefaceHolder.init(getAssets(), "fontawesome-webfont.ttf");
    }
}
