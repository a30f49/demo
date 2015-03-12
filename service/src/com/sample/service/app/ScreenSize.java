package com.sample.service.app;

import android.app.Activity;
import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by vincent on 2015/3/10.
 */
public class ScreenSize {
    static public int getScreenHeight(Context context) {
        return getDisplay(context).getHeight();
    }

    static public int getScreenWidth(Context context) {
        return getDisplay(context).getWidth();
    }

    static private Display getDisplay(Context context) {
        return ((WindowManager) context.getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay();
    }
}
