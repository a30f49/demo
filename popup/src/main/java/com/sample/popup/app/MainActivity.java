package com.sample.popup.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

public class MainActivity extends Activity {

    private Button mActionDropdown;
    private Button mActionPopup;

    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View popupView = getLayoutInflater().inflate(R.layout.popup_layout, null);

        mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT,
                                                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupWindow.getContentView().setFocusableInTouchMode(true);

        mActionDropdown = (Button) findViewById(R.id.action_dropdown);
        mActionDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.setAnimationStyle(R.style.dropdown_anim_style);
                mPopupWindow.showAsDropDown(v, 0, 0);
            }
        });

        mActionPopup = (Button) findViewById(R.id.action_popup);
        mActionPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.setAnimationStyle(R.style.popup_anim_style);
                mPopupWindow.showAtLocation(findViewById(R.id.container), Gravity.BOTTOM, 0, 0);
            }
        });
    }
}
