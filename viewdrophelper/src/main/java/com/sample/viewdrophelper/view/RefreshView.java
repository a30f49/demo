package com.sample.viewdrophelper.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by vincent on 2015/3/17.
 */
public class RefreshView extends View {

    private Paint mPaint;
    private Rect mBounds;
    private String text;

    public RefreshView(Context context) {
        this(context, null, 0);
    }

    public RefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final int[] ATTRS = new int[]{
                android.R.attr.text
        };

        final TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
        text = a.getString(0);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBounds = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        mPaint.setColor(Color.YELLOW);
        mPaint.setTextSize(30);
        mPaint.getTextBounds(text, 0, text.length(), mBounds);
        float textWidth = mBounds.width();
        float textHeight = mBounds.height();
        canvas.drawText(text, getWidth() / 2 - textWidth / 2, getHeight() / 2
                + textHeight / 2, mPaint);
    }



    /**
     * class RefreshHandler
     */
    public class RefreshHandler{
        public RefreshHandler(){

        }

        public void onRefresh(){

        }

        public void onRequestRefresh(){

        }

        public void onCancelRefresh(){

        }
    }
}
