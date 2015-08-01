package com.sample.picker.app;

/**
 * https://github.com/saiwu-bigkoo/Android-PickerView
 */
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import com.dianjiang.plugin.widget.picker.WheelTimePicker;
import com.dianjiang.plugin.widget.picker.WheelView;
import com.dianjiang.plugin.widget.picker.internal.NumericWheelAdapter;

public class MainActivity extends ActionBarActivity {

    private TextView pickerTime;
    private TextView pickerCustom;

    TimePickerPopupWindow timePopupWindow;
    WheelView wheelYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DemoPickerActivity.class);
                startActivity(intent);
            }
        });

        // time popup
        pickerTime =(TextView) findViewById(R.id.time);
        timePopupWindow = new TimePickerPopupWindow(this, WheelTimePicker.Type.YEAR_MONTH_DAY);
        pickerTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePopupWindow.showAtLocation(pickerTime, Gravity.BOTTOM, 0, 0);
            }
        });

        // date
        pickerCustom = (TextView) findViewById(R.id.custom_date);
        pickerCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CustomPickerDialog()
                        .show(getFragmentManager(), CustomPickerDialog.TAG);
            }
        });

        // for year only
        wheelYear = (WheelView) findViewById(R.id.yearonlyview);
        wheelYear.setTextSize((int) (32 * getResources().getDisplayMetrics().scaledDensity));
        wheelYear.setAdapter(new NumericWheelAdapter(2000, 2020));// 设置"年"的显示数据
        wheelYear.setLabel("年");
        wheelYear.setCurrentItem(2015 - 2000);

        /*View timePickerView = findViewById(R.id.datepickerview);
        WheelTimePicker wheelTimePicker = new WheelTimePicker(timePickerView, WheelTimePicker.Type.YEAR_MONTH_DAY);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        wheelTimePicker.setPicker(year, month, day, hours, minute);*/
    }
}
