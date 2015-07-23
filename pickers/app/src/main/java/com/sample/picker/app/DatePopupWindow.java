package com.sample.picker.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import com.dianjiang.app.pickerdialog.WheelTime;
import com.dianjiang.widget.pickerview.support.ScreenInfo;

import java.util.Calendar;

/**
 * @author Sai
 * 
 */
public class DatePopupWindow extends PopupWindow {
	WheelTime wheelTime;

	public DatePopupWindow(Context context, WheelTime.Type type) {
		super(context);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setBackgroundDrawable(new BitmapDrawable());// 这样设置才能点击屏幕外dismiss窗口
		this.setOutsideTouchable(true);
		this.setAnimationStyle(R.style.timepopwindow_anim_style);

		LayoutInflater mLayoutInflater = LayoutInflater.from(context);
		View rootView = mLayoutInflater.inflate(R.layout.datepicker, null);
		ScreenInfo screenInfo = new ScreenInfo((Activity) context);
		wheelTime = new WheelTime(rootView, type);

		wheelTime.screenheight = screenInfo.getHeight();

		//select now
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		wheelTime.setPicker(year, month, day, hours, minute);
		
		setContentView(rootView);
	}
}
