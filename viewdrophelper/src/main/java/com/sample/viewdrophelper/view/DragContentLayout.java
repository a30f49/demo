package com.sample.viewdrophelper.view;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by vincent on 2015/3/13.
 */
public class DragContentLayout extends ContentLayout{
    private static final String TAG = "DragContentLayout";
    private static final float MAX_OFFSET_RANGE = 0.25f;

    private float mInitialMotionX;
    private float mInitialMotionY;

    final ViewDragHelper mDragHelper;
    DragListener mDragListener;

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

    public void setDragListener(DragListener listener){
        mDragListener = listener;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);

        if (!(action != MotionEvent.ACTION_DOWN)) {
            mDragHelper.cancel();
            return super.onInterceptTouchEvent(ev);
        }

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel();
            return false;
        }

        boolean interceptTap = false;

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                final float x = ev.getX();
                final float y = ev.getY();
                mInitialMotionX = x;
                mInitialMotionY = y;

                if (mDragHelper.isViewUnder(mTarget, (int) x, (int) y)) {
                    interceptTap = true;
                }
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                final float x = ev.getX();
                final float y = ev.getY();
                final float adx = Math.abs(x - mInitialMotionX);
                final float ady = Math.abs(y - mInitialMotionY);
                final int slop = mDragHelper.getTouchSlop();
                if (adx > slop && ady > adx) {
                    mDragHelper.cancel();
                    return false;
                }
            }
        }

        final boolean interceptForDrag = mDragHelper.shouldInterceptTouchEvent(ev);

        return interceptForDrag || interceptTap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mDragHelper.processTouchEvent(ev);

        final int action = ev.getAction();
        boolean wantTouchEvents = true;

        switch (action & MotionEventCompat.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                final float x = ev.getX();
                final float y = ev.getY();
                mInitialMotionX = x;
                mInitialMotionY = y;
                break;
            }

            case MotionEvent.ACTION_UP: {
                Log.i(TAG, "onTouchEvent():ACTION_UP");
                final float x = ev.getX();
                final float y = ev.getY();
                final float dx = x - mInitialMotionX;
                final float dy = y - mInitialMotionY;
                final int slop = mDragHelper.getTouchSlop();
                if (dx * dx + dy * dy < slop * slop &&
                        mDragHelper.isViewUnder(mTarget, (int) x, (int) y)) {
                    // Taps close a dimmed open pane.
                    if(mDragListener!=null){
                        mDragListener.onTap();
                    }
                    break;
                }
                break;
            }
        }

        return wantTouchEvents;
    }

    @Override
    public void computeScroll() {
        Log.i(TAG, "computeScroll()");
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


    /**
     * Smoothly animate mDraggingPane to the target X position within its range.
     *
     * @param slideOffset position to animate to
     * @param velocity initial velocity in case of fling, or 0.
     */
    boolean smoothSlideTo(float slideOffset, int velocity) {
        final LayoutParams lp = (LayoutParams) mTarget.getLayoutParams();

        int startBound = getPaddingTop() + lp.topMargin;
        int y = (int) (startBound + getHeight() * MAX_OFFSET_RANGE * slideOffset);

        if (mDragHelper.smoothSlideViewTo(mTarget, mTarget.getLeft(), y)) {
            ViewCompat.postInvalidateOnAnimation(this);
            return true;
        }
        return false;
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
            float offsetPercent = Math.abs( offset / ((float) totalOffset * MAX_OFFSET_RANGE) );

            int targetY = (int) (oldTop + dy * (1 - offsetPercent));
            return targetY;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            Log.i(TAG, "onViewPositionChanged(left:"+left+",top:"+top+",dx:"+dx+",dy:"+dy+")");

            ((LayoutParams)(changedView.getLayoutParams())).setOffset(left, top);
            Log.i(TAG, "LayoutParams.getOffsetY()="+((LayoutParams)(changedView.getLayoutParams())).getOffsetY());

            requestLayout();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            Log.i(TAG, "onViewReleased(xvel:"+xvel+",yvel:"+yvel+")");

            mDragHelper.settleCapturedViewAt(releasedChild.getLeft(), getPaddingTop());
            invalidate();
        }
    }


    static interface DragListener{
        void onTap();
    }
}
