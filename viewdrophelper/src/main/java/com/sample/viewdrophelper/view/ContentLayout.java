package com.sample.viewdrophelper.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by vincent on 2015/3/13.
 */
public class ContentLayout extends ViewGroup{
    View mTarget;

    public ContentLayout(Context context) {
        super(context);
    }

    public ContentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTarget = getChildAt(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                getMeasuredWidth() - getPaddingRight() - getPaddingLeft(),
                MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom(),
                MeasureSpec.EXACTLY);

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingTop = getPaddingTop();
        int paddingLeft = getPaddingLeft();

        for(int index=0; index<getChildCount(); index++) {
            View child = getChildAt(index);
            if (child.getVisibility() == View.GONE) {
                return;
            }

            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            int offsetX = (int) lp.getOffsetX();
            int offsetY = (int) lp.getOffsetY();

            child.layout(
                    l + paddingTop + offsetX,
                    t + paddingLeft + offsetY,
                    l + paddingLeft + offsetX + child.getMeasuredWidth(),
                    l + paddingTop + offsetY + child.getMeasuredHeight());
        }
    }


    /**
     * LayoutParams Override methods
     * @param p
     * @return
     */
    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }




    /**
     * class LayoutParams
     */
    public static class LayoutParams extends MarginLayoutParams {

        private static final int[] LAYOUT_ATTRS = new int[] {
                android.R.attr.layout_gravity,
                android.R.attr.layout_x,
                android.R.attr.layout_y,
        };

        int gravity;
        float offsetX;
        float offsetY;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);

            final TypedArray a = c.obtainStyledAttributes(attrs, LAYOUT_ATTRS);
            this.gravity = a.getInt(0, Gravity.NO_GRAVITY);
            this.offsetX = a.getDimension(1, 0f);
            this.offsetY = a.getDimension(2, 0f);
            //

            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(LayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams setGravity(int gravity){
            this.gravity = gravity;
            return this;
        }

        public int getGravity(){
            return this.gravity;
        }

        public LayoutParams setOffset(float offsetX, float offsetY){
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            return this;
        }

        public float getOffsetX(){
            return this.offsetX;
        }

        public float getOffsetY(){
            return this.offsetY;
        }
    }
}
