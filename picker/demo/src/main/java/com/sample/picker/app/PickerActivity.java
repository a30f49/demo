package com.sample.picker.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import com.dianjiang.app.pickerdialog.OptionPopupWindow;
import com.dianjiang.app.pickerdialog.TimePopupWindow;
import com.dianjiang.app.pickerdialog.WheelTimePicker;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PickerActivity extends ActionBarActivity {

    private ArrayList<String> options1Items = new ArrayList<String>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
    private TextView tvTime, tvOptions;
    TimePopupWindow pwTime;
    OptionPopupWindow pwOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTime=(TextView) findViewById(R.id.tvTime);
        tvOptions=(TextView) findViewById(R.id.tvOptions);

        //时间选择器
        pwTime = new TimePopupWindow(this, WheelTimePicker.Type.YEAR_MONTH_DAY);

        //选项选择器
        pwOptions = new OptionPopupWindow(this);

        //选项1
        options1Items.add("广东");
        options1Items.add("湖南");

        //选项2
        ArrayList<String> options2Items_01=new ArrayList<String>();
        options2Items_01.add("广州");
        options2Items_01.add("佛山");
        options2Items_01.add("东莞");
        ArrayList<String> options2Items_02=new ArrayList<String>();
        options2Items_02.add("长沙");
        options2Items_02.add("岳阳");
        options2Items.add(options2Items_01);
        options2Items.add(options2Items_02);

        //选项3
        ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> options3Items_02 = new ArrayList<ArrayList<String>>();
        ArrayList<String> options3Items_01_01=new ArrayList<String>();
        options3Items_01_01.add("白云");
        options3Items_01_01.add("天河");
        options3Items_01_01.add("海珠");
        options3Items_01_01.add("越秀");
        options3Items_01.add(options3Items_01_01);
        ArrayList<String> options3Items_01_02=new ArrayList<String>();
        options3Items_01_02.add("南海");
        options3Items_01.add(options3Items_01_02);
        ArrayList<String> options3Items_01_03=new ArrayList<String>();
        options3Items_01_03.add("常平");
        options3Items_01_03.add("虎门");
        options3Items_01.add(options3Items_01_03);

        ArrayList<String> options3Items_02_01=new ArrayList<String>();
        options3Items_02_01.add("长沙1");
        options3Items_02.add(options3Items_02_01);
        ArrayList<String> options3Items_02_02=new ArrayList<String>();
        options3Items_02_02.add("岳1");
        options3Items_02.add(options3Items_02_02);

        options3Items.add(options3Items_01);
        options3Items.add(options3Items_02);

        //三级联动效果
        pwOptions.setPicker(options1Items, options2Items, options3Items, true);
        //设置选择的三级单位
        pwOptions.setLabels("省", "市", "区");
        //设置默认选中的三级项目
        pwOptions.setSelectOptions(0, 0, 0);
        //监听确定选择按钮
        pwOptions.setOnoptionsSelectListener(new OptionPopupWindow.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1)
                    +options2Items.get(options1).get(option2)
                    +options3Items.get(options1).get(option2).get(options3);
                tvOptions.setText(tx);
            }
        });
        //点击弹出选项选择器
        tvOptions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pwOptions.showAtLocation(tvTime, Gravity.BOTTOM, 0, 0);
            }
        });

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwTime.showAtLocation(tvTime, Gravity.BOTTOM, 0, 0);
            }
        });
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
}