package com.dianjiang.widget.pickerview.support;

import android.content.Context;

public class DensityUtils {
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dp2px(Context context, float dpValue) {
		float density = context.getResources().getDisplayMetrics().density;
		return dp2px(density, dpValue);
	}

	public static int dp2px(float density, float dpValue){
		return (int) (dpValue * density + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dp(Context context, int pxValue) {
		float density = context.getResources().getDisplayMetrics().density;
		return px2dp(density, pxValue);
	}

	public static int px2dp(float density, int pxValue){
		return (int) (pxValue / density + 0.5f);
	}

	/**
	 * px2sp
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2sp(Context context, int pxValue) {
		float density = context.getResources().getDisplayMetrics().scaledDensity;
		return px2dp(density, pxValue);
	}

	public static int px2sp(float density, int pxValue){
		return (int) (pxValue / density + 0.5f);
	}


	/**
	 * sp2px
	 * @param context
	 * @param spValue
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
		return sp2px(scaledDensity, spValue);
	}

	public static int sp2px(float scaledDensity, float spValue){
		return (int) (spValue / scaledDensity + 0.5f);
	}
}
