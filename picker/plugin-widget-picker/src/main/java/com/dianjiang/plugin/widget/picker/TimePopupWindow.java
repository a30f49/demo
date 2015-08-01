package com.dianjiang.plugin.widget.picker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import com.dianjiang.app.pickerdialog.R;

/**
 * 时间选择器
 * 
 * @author Sai
 * 
 */
public class TimePopupWindow extends PopupWindow implements OnClickListener {
	private View rootView;
	WheelTimePicker wheelTimePicker;
	private View btnSubmit, btnCancel;
	private static final String TAG_SUBMIT = "submit";
	private static final String TAG_CANCEL = "cancel";
	private OnTimeSelectListener timeSelectListener;

	public TimePopupWindow(Context context, WheelTimePicker.Type type) {
		super(context);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setBackgroundDrawable(new BitmapDrawable());// 这样设置才能点击屏幕外dismiss窗口
		this.setOutsideTouchable(true);
		this.setAnimationStyle(R.style.timepopwindow_anim_style);

		LayoutInflater mLayoutInflater = LayoutInflater.from(context);
		rootView = mLayoutInflater.inflate(R.layout.time_popup, null);
		// -----确定和取消按钮
		btnSubmit = rootView.findViewById(R.id.btnSubmit);
		btnSubmit.setTag(TAG_SUBMIT);
		btnCancel = rootView.findViewById(R.id.btnCancel);
		btnCancel.setTag(TAG_CANCEL);
		btnSubmit.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		// ----时间转轮
		final View timepickerview = rootView.findViewById(R.id.timepicker);
		wheelTimePicker = new WheelTimePicker(timepickerview, type);

		//DisplayMetrics metrics = ScreenUtils.getDisplayMetrics(context);
		//int textSize = metrics.scaledDensity / 4;
		//wheelTimePicker.setTextSize(textSize);

		//select now
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		wheelTimePicker.setPicker(year, month, day, hours, minute);
		
		setContentView(rootView);
	}

	/**
	 * set year range
	 * 
	 * @param start
	 * @param end
	 */
	public void setRange(int start, int end) {
		wheelTimePicker.setStartYear(start);
		wheelTimePicker.setEndYear(end);
	}

	/**
	 * set time
	 * @param date
	 */
	public void setTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		if (date == null)
			calendar.setTimeInMillis(System.currentTimeMillis());
		else
			calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		wheelTimePicker.setPicker(year, month, day, hours, minute);
	}

	/**
	 * show picker at location
	 * @param parent
	 * @param gravity
	 * @param x
	 * @param y
	 * @param date
	 */
	public void showAtLocation(View parent, int gravity, int x, int y, Date date) {
		Calendar calendar = Calendar.getInstance();
		if (date == null)
			calendar.setTimeInMillis(System.currentTimeMillis());
		else
			calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		wheelTimePicker.setPicker(year, month, day, hours, minute);
		update();
		super.showAtLocation(parent, gravity, x, y);
	}

	/**
	 * cyclic or not
	 * 
	 * @param cyclic
	 */
	public void setCyclic(boolean cyclic) {
		wheelTimePicker.setCyclic(cyclic);
	}

	@Override
	public void onClick(View v) {
		String tag = (String) v.getTag();
		if (tag.equals(TAG_CANCEL)) {
			dismiss();
			return;
		} else {
			if (timeSelectListener != null) {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat(WheelTimePicker.DEFAULT_DATETIME_PATTERN);
					Date date = dateFormat.parse(wheelTimePicker.getTime());
					timeSelectListener.onTimeSelect(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			dismiss();
			return;
		}
	}

	public void setOnTimeSelectListener(OnTimeSelectListener timeSelectListener) {
		this.timeSelectListener = timeSelectListener;
	}


	/**
	 * interface OnTimeSelectListener
	 */
	public interface OnTimeSelectListener {
		void onTimeSelect(Date date);
	}
}
