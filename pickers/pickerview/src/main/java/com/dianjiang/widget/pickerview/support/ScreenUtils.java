package com.dianjiang.widget.pickerview.support;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by vincent on 2015/7/23 0023.
 */
public class ScreenUtils {
    public static float getDensity(Context context){
        return context.getResources().getDisplayMetrics().density;
    }

    public static int getScreenWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static DisplayMetrics getDisplayMetrics(Context context){
        return context.getResources().getDisplayMetrics();
    }

}
