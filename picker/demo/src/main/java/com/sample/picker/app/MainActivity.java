package com.sample.picker.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import com.dianjiang.app.pickerdialog.WheelTimePicker;
import com.dianjiang.plugin.widget.pickerview.WheelView;

import java.util.Calendar;

public class MainActivity extends ActionBarActivity {

    private TextView picker;
    private TextView tvTime;
    TimePickerPopupWindow pwTime;
    WheelView wheelYear;
    WheelTimePicker wheelTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* wheelYear = (WheelView) findViewById(R.id.yearview);
        wheelYear.setAdapter(new NumericWheelAdapter(2000, 2020));// 设置"年"的显示数据
        wheelYear.setLabel("年");
        wheelYear.setCurrentItem(2015 - 2000);// 初始化时显示的数据
        wheelYear.TEXT_SIZE = 72;
        wheelYear.setVisibility(View.VISIBLE);*/

        picker = (TextView) findViewById(R.id.picker);
        tvTime=(TextView) findViewById(R.id.tvTime);

        pwTime = new TimePickerPopupWindow(this, WheelTimePicker.Type.YEAR_MONTH_DAY);

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwTime.showAtLocation(tvTime, Gravity.BOTTOM, 0, 0);
            }
        });

        picker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new CustomPickerDialog()
                    .show(getFragmentManager(), CustomPickerDialog.TAG);
            }
        });

        View datepickerview = findViewById(R.id.datepickerview);
        wheelTimePicker = new WheelTimePicker(datepickerview, WheelTimePicker.Type.YEAR_MONTH_DAY);

        //select now
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        wheelTimePicker.setPicker(year, month, day, hours, minute);
    }
}