package com.sample.viewdrophelper.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

/**
 * Created by vincent on 2015/3/16.
 */
public class PullToRefreshLayout extends ContentLayout {
    private static final String TAG = "PullToRefreshLayout";

    private static final int REFRESH_THRESHHOLD = 60;   //dp

    private DragListener mDragListener;
    private int mOffsetToRefreshThreshHold;

    private View mRefreshView;
    private OnRefreshListener mRefreshListener;
    private RefreshView.RefreshHandler mRefreshHandler;

    private boolean mRequestRefresh;
    private boolean mRefreshing;

    public PullToRefreshLayout(Context context) {
        this(context, null, 0);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setMaxDragRange(0.3f);

        mDragListener = new PullListener();
        setDragListener(mDragListener);

        float density = context.getResources().getDisplayMetrics().density;
        mOffsetToRefreshThreshHold = Math.round((float) REFRESH_THRESHHOLD * density);

        mRefreshing = false;
    }

    @Override
    protected void onFinishInflate() {
        Log.i(TAG, "onFinishInflate()");
        if(getChildCount() > 2){
            throw new IllegalStateException("ContentLayout can only support two child.");
        }

        for(int index=0; index<getChildCount();index++){
            View child = getChildAt(index);

            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if(isRefreshView(lp)){
                mRefreshView  = child;
            }else{
                mTarget = child;
            }
        }

        if(mRefreshView==null || mTarget==null){
            throw new IllegalStateException("One and only one child must set gravity to TOP");
        }

        /// set inner class
        RefreshView refreshIndicator = (mRefreshView instanceof RefreshView) ? (RefreshView)mRefreshView
                : (mRefreshView instanceof ViewGroup) ?
                  findRefreshIndicator((ViewGroup)mRefreshView)
                : null;
        if(refreshIndicator==null){
            throw new IllegalStateException("The Refresh view must be instance of RefreshView or ViewGroup which contains RefreshView.");
        }

        mRefreshHandler = refreshIndicator.new RefreshHandler();
    }

    boolean isRefreshView(LayoutParams lp){
        return lp.gravity == Gravity.TOP;
    }

    private RefreshView findRefreshIndicator(ViewGroup viewGroup){
        for(int i=0; i<viewGroup.getChildCount(); i++){
            View child = viewGroup.getChildAt(0);
            if(child instanceof RefreshView){
                return (RefreshView)child;
            }
        }

        throw new IllegalStateException("There is no RefreshView within the ViewGroup.");
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (!isEnabled() || canChildScrollUp() || mRefreshing) {
            return false;
        }

        return super.onInterceptTouchEvent(ev);
    }

    private boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return mTarget.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mTarget, -1);
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
        int y = (int) (startBound + mOffsetToRefreshThreshHold * slideOffset);

        if (mDragHelper.smoothSlideViewTo(mTarget, mTarget.getLeft(), y)) {
            ViewCompat.postInvalidateOnAnimation(this);
            return true;
        }

        return false;
    }


    class PullListener implements DragListener{

        @Override
        public void onStart(float x, float y) {
            mRequestRefresh = false;
        }

        @Override
        public void onTap(float x, float y) {
            //smoothSlideTo(0f, -1);
        }

        @Override
        public void onDrag(int left, int top, int dx, int dy) {
            if(!mRequestRefresh && dy>0){
                if(top > mOffsetToRefreshThreshHold){
                    Log.i(TAG, "Refresh requested.");
                    mRequestRefresh = true;

                    mRefreshHandler.onRequestRefresh();
                }
            }else if(mRequestRefresh && dy<0){
                if(top < mOffsetToRefreshThreshHold){
                    Log.i(TAG, "Refresh cancelled.");
                    mRequestRefresh = false;

                    mRefreshHandler.onCancelRefresh();
                }
            }

            /// set refresh view offset
            LayoutParams lp = (LayoutParams) mRefreshView.getLayoutParams();
            lp.setOffset(mRefreshView.getLeft(), top - mRefreshView.getMeasuredHeight());
        }

        @Override
        public void onReleased() {
            setRefresh(mRequestRefresh);
        }
    }


    public void setRefresh(boolean refresh){
        mRefreshing = refresh;

        if(refresh){
            smoothSlideTo(1f, -1);

            /// set the refresh view
            mRefreshHandler.onRefresh();

            /// dispatch refresh event
            if(mRefreshListener != null){
                mRefreshListener.onRefresh();
            }
        }else{
            smoothSlideTo(0f, -1);
        }
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mRefreshListener = listener;
    }

    public static interface OnRefreshListener {
        public void onRefresh();
    }
}
