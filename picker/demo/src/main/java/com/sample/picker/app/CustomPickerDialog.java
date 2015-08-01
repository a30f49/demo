package com.sample.picker.app;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dianjiang.app.pickerdialog.WheelTimePicker;
import com.dianjiang.plugin.widget.pickerview.WheelView;

import java.util.Calendar;

/**
 * Created by vincent on 2015/7/22 0022.
 */
public class CustomPickerDialog extends DialogFragment {
    public static final String TAG = CustomPickerDialog.class.getSimpleName();

    private WheelView wv_year;
    private static int START_YEAR = 2000, END_YEAR = 2020;

    WheelTimePicker wheelTimePicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       return inflater.inflate(R.layout.dialog_long_date_picker, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View datepickerview = view.findViewById(R.id.datepickerview);
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
