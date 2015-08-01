package com.dianjiang.plugin.widget.picker;

import java.util.Arrays;
import java.util.List;

import com.dianjiang.app.pickerdialog.R;
import com.dianjiang.plugin.widget.picker.internal.NumericWheelAdapter;
import com.dianjiang.plugin.widget.picker.internal.OnWheelChangedListener;

import android.content.Context;
import android.view.View;

public class WheelTimePicker {
	//public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm";

	private View view;
	private WheelView wv_year;
	private WheelView wv_month;
	private WheelView wv_day;
	private WheelView wv_hours;
	private WheelView wv_mins;
	public int textSize;

	private Type type;
	private int startYear;
	private int endYear;
	private static int DEFAULT_START_YEAR = 1990, DEFAULT_END_YEAR = 2100;

	public WheelTimePicker(View view) {
		super();
		this.view = view;
		type = Type.ALL;
		setView(view);
	}
	public WheelTimePicker(View view, Type type) {
		super();
		this.view = view;
		this.type = type;
		setView(view);
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
		this.startYear = DEFAULT_START_YEAR;
		this.endYear = DEFAULT_END_YEAR;
	}

	public int getStartYear() {
		return this.startYear;
	}

	public void setStartYear(int year) {
		this.startYear = year;
	}

	public int getEndYear() {
		return this.endYear;
	}

	public void setEndYear(int year) {
		this.endYear = year;
	}


	/**
	 * text size in dp
	 * @param textSize
	 */
	public void setTextSize(int textSize){
		this.textSize = textSize;

		/// update text size immediately
		if(wv_day!=null) {
			wv_day.setTextSize(textSize);
		}
		if(wv_month!=null) {
			wv_month.setTextSize(textSize);
		}
		if(wv_year!=null) {
			wv_year.setTextSize(textSize);
		}
		if(wv_hours!=null) {
			wv_hours.setTextSize(textSize);
		}
		if(wv_mins!=null) {
			wv_mins.setTextSize(textSize);
		}
	}

	public void setPicker(int year ,int month,int day){
		this.setPicker(year, month, day, 0, 0);
	}
	
	/**
	 * @Description: 弹出日期时间选择器
	 */
	public void setPicker(int year, int month, int day, int h, int m) {
		// 添加大小月月份并将其转换为list,方便之后的判断
		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		Context context = view.getContext();

		// 年
		wv_year = (WheelView) view.findViewById(R.id.year);
		wv_year.setAdapter(new NumericWheelAdapter(DEFAULT_START_YEAR, DEFAULT_END_YEAR));// 设置"年"的显示数据
		wv_year.setLabel(context.getString(R.string.year));// 添加文字
		wv_year.setCurrentItem(year - DEFAULT_START_YEAR);// 初始化时显示的数据

		// month
		wv_month = (WheelView) view.findViewById(R.id.month);
		wv_month.setAdapter(new NumericWheelAdapter(1, 12));
		wv_month.setLabel(context.getString(R.string.month));		
		wv_month.setCurrentItem(month);

		// day
		wv_day = (WheelView) view.findViewById(R.id.day);
		// 判断大小月及是否闰年,用来确定"日"的数据
		if (list_big.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 30));
		} else {
			// 闰年
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				wv_day.setAdapter(new NumericWheelAdapter(1, 29));
			else
				wv_day.setAdapter(new NumericWheelAdapter(1, 28));
		}
		wv_day.setLabel(context.getString(R.string.day));
		wv_day.setCurrentItem(day - 1);

		// hour
		wv_hours = (WheelView)view.findViewById(R.id.hour);
		wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
		wv_hours.setLabel(context.getString(R.string.hours));
		wv_hours.setCurrentItem(h);

		// min
		wv_mins = (WheelView)view.findViewById(R.id.min);
		wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
		wv_mins.setLabel(context.getString(R.string.minutes));
		wv_mins.setCurrentItem(m);
		
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + DEFAULT_START_YEAR;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big
						.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(wv_month
						.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if ((year_num % 4 == 0 && year_num % 100 != 0)
							|| year_num % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
			}
		};
		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if (((wv_year.getCurrentItem() + DEFAULT_START_YEAR) % 4 == 0 && (wv_year
							.getCurrentItem() + DEFAULT_START_YEAR) % 100 != 0)
							|| (wv_year.getCurrentItem() + DEFAULT_START_YEAR) % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
			}
		};
		wv_year.addChangingListener(wheelListener_year);
		wv_month.addChangingListener(wheelListener_month);

		// 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
		switch(type){
		case ALL:
			break;
		case YEAR_MONTH_DAY:
			wv_hours.setVisibility(View.GONE);
			wv_mins.setVisibility(View.GONE);
			break;
		case HOURS_MINS:
			wv_year.setVisibility(View.GONE);
			wv_month.setVisibility(View.GONE);
			wv_day.setVisibility(View.GONE);
			break;
		case MONTH_DAY_HOUR_MIN:
			wv_year.setVisibility(View.GONE);
			break;
		}

		wv_day.setTextSize(textSize);
		wv_month.setTextSize(textSize);
		wv_year.setTextSize(textSize);
		wv_hours.setTextSize(textSize);
		wv_mins.setTextSize(textSize);
	}

	/**
	 * 设置是否循环滚动
	 */
	public void setCyclic(boolean cyclic){
		wv_year.setCyclic(cyclic);
		wv_month.setCyclic(cyclic);
		wv_day.setCyclic(cyclic);
		wv_hours.setCyclic(cyclic);
		wv_mins.setCyclic(cyclic);
	}

	public String getTime() {
		StringBuffer sb = new StringBuffer();
			sb.append((wv_year.getCurrentItem() + DEFAULT_START_YEAR)).append("-")
			.append((wv_month.getCurrentItem() + 1)).append("-")
			.append((wv_day.getCurrentItem() + 1)).append(" ")
			.append(wv_hours.getCurrentItem()).append(":")
			.append(wv_mins.getCurrentItem());
		return sb.toString();
	}

	/**
	 * Time Fields
	 */
	public enum Type {
		ALL, YEAR_MONTH_DAY, HOURS_MINS, MONTH_DAY_HOUR_MIN
	}
}
