package com.sample.viewdrophelper.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by vincent on 2015/3/13.
 */
public class DragContentLayout extends ContentLayout{
    private static final String TAG = "DragContentLayout";
    private final ViewDragHelper mDragHelper;

    private float mInitialMotionX;
    private float mInitialMotionY;

    public DragContentLayout(Context context) {
        this(context, null, 0);
    }

    public DragContentLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragContentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //Log.i(TAG, "onLayout("+l+","+t+","+r+","+b+")");
        super.onLayout(changed, l, t, r, b);

        Log.i(TAG, "mTarget.getTop()=" + mTarget.getTop());
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);

        if (( action != MotionEvent.ACTION_DOWN)) {
            mDragHelper.cancel();
            return super.onInterceptTouchEvent(ev);
        }

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel();
            return false;
        }

        final float x = ev.getX();
        final float y = ev.getY();
        boolean interceptTap = false;

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mInitialMotionX = x;
                mInitialMotionY = y;
                interceptTap = mDragHelper.isViewUnder(mTarget, (int) x, (int) y);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                final float adx = Math.abs(x - mInitialMotionX);
                final float ady = Math.abs(y - mInitialMotionY);
                final int slop = mDragHelper.getTouchSlop();
                if (ady > slop && adx > ady) {
                    mDragHelper.cancel();
                    return false;
                }
            }
        }

        return mDragHelper.shouldInterceptTouchEvent(ev) || interceptTap;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mDragHelper.processTouchEvent(ev);

        final int action = ev.getAction();
        final float x = ev.getX();
        final float y = ev.getY();

        boolean isChildUnder = mDragHelper.isViewUnder(mTarget, (int) x, (int) y);

        switch (action & MotionEventCompat.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                mInitialMotionX = x;
                mInitialMotionY = y;
                Log.i(TAG, "onTouchEvent:ACTION_DOWN:x="+x+",y="+y);
                break;
            }

            case MotionEvent.ACTION_UP: {
                final float dx = x - mInitialMotionX;
                final float dy = y - mInitialMotionY;
                final int slop = mDragHelper.getTouchSlop();
                Log.i(TAG, "onTouchEvent:ACTION_UP:dx="+dx+",dy="+dy+",slop="+slop);

                if(isChildUnder) {
                    if (dx * dx + dy * dy < slop * slop) {
                        Log.i(TAG, "isChildUnder Dragged");
                         //smoothSlideTo(0f);
                    }
                }
                break;
            }
        }

        return true;
    }


    /**
     * class DragHelperCallback
     */
    class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mTarget;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            Log.i(TAG, "clampViewPositionVertical(top:"+top+",dy:"+dy+")");
            final int offset = top - getPaddingTop();
            final int totalOffset = getHeight();

            int oldTop = top - dy;
            float offsetPercent = (float)offset / ((float)totalOffset * 0.2f);
            int targetY = (int)(oldTop + dy * (1-offsetPercent));

            return targetY;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            Log.i(TAG, "onViewPositionChanged(left:"+left+",top:"+top+",dx:"+dx+",dy:"+dy+")");

            ((LayoutParams)(mTarget.getLayoutParams())).setOffset(left, top);
            Log.i(TAG, "LayoutParams.getOffsetY()="+((LayoutParams)(mTarget.getLayoutParams())).getOffsetY());

            requestLayout();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            Log.i(TAG, "onViewReleased(xvel:"+xvel+",yvel:"+yvel+")");

            smoothSlideTo(0f);
            mDragHelper.settleCapturedViewAt(releasedChild.getLeft(), getPaddingTop());
        }
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    boolean smoothSlideTo(float slideOffset) {
        if (mDragHelper.smoothSlideViewTo(mTarget, mTarget.getLeft(), getPaddingTop())) {
            ViewCompat.postInvalidateOnAnimation(this);
            return true;
        }
        return false;
    }
}
