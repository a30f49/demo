package com.dianjiang.plugin.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.dianjiang.plugin.pulltorefresh.R;

import java.util.Timer;
import java.util.TimerTask;


public class PullToRefreshLayout extends RelativeLayout {
    public static final String TAG = "PullToRefreshLayout";
    public static final int PULL_TO_REFRESH = 0;
    public static final int RELEASE_TO_REFRESH = 1;
    public static final int REFRESHING = 2;
    public static final int RELEASE_TO_LOAD = 3;
    public static final int LOADING = 4;
    public static final int DONE = 5;
    private int state = PULL_TO_REFRESH;

    private OnRefreshListener mListener;
    public static final int REFRESH_SUCCEED = 0;
    public static final int REFRESH_FAIL = 1;

    private float downY, lastY;
    public float pullDownY = 0;
    private float pullUpY = 0;
    private float refreshDist = 200;
    private float loadmoreDist = 200;

    private MyTimer timer;
    public float MOVE_SPEED = 8;
    private boolean isTouch = false;
    private float radio = 2;

    private RotateAnimation rotateAnimation;
    private RotateAnimation refreshingAnimation;

    private View refreshView;
    private View refreshPullView;
    private View refreshingView;
    private View refreshStateImageView;
    private TextView refreshStateTextView;

    private View loadmoreView;
    private View loadPullView;
    private View loadingView;
    private View loadStateImageView;
    private TextView loadStateTextView;
    private boolean isLayout;

    private View targetView;
    private boolean canLoadMore;

    private int mEvents;
    private boolean canPullDown = true;
    private boolean canPullUp = true;

    public boolean canPullDown() {
        if (targetView instanceof AbsListView) {
            final AbsListView absListView = (AbsListView) targetView;
            if (absListView.getCount() == 0) {
                return true;
            } else if (absListView.getFirstVisiblePosition() == 0
                    && absListView.getChildAt(0).getTop() >= 0) {
                return true;
            }
        } else if (targetView instanceof ScrollView) {
            final ScrollView scrollView = (ScrollView) targetView;
            if (scrollView.getScrollY() == 0) {
                return true;
            }
        } else if (targetView instanceof WebView) {
            final WebView webView = (WebView) targetView;
            if (webView.getScrollY() == 0) {
                return true;
            }
        } else {
            /// other view type
            return true;
        }

        return false;
    }

    public boolean canPullUp() {
        if (!canLoadMore) {
            return false;
        }

        if (targetView instanceof AbsListView) {
            final AbsListView absListView = (AbsListView) targetView;
            if (absListView.getCount() == 0) {
                return true;
            } else if (absListView.getLastVisiblePosition() == (absListView.getCount() - 1)) {
                if (absListView.getChildAt(absListView.getLastVisiblePosition() - absListView.getFirstVisiblePosition()) != null
                        && absListView.getChildAt(
                        absListView.getLastVisiblePosition()
                                - absListView.getFirstVisiblePosition()).getBottom() <= absListView.getMeasuredHeight())
                    return true;
            }
            return false;
        } else if (targetView instanceof ScrollView) {
            final ScrollView scrollView = (ScrollView) targetView;
            if (scrollView.getScrollY() >= (scrollView.getChildAt(0).getHeight() - scrollView.getMeasuredHeight())) {
                return true;
            }
        } else if (targetView instanceof WebView) {
            final WebView webView = (WebView) targetView;
            if (webView.getScrollY() >= webView.getContentHeight() * webView.getScale()
                    - webView.getMeasuredHeight()) {
                return true;
            }
        } else {
            /// other type of view
            return true;
        }

        return true;
    }

    Handler updateHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            MOVE_SPEED = (float) (8 + 5 * Math.tan(Math.PI / 2
                / getMeasuredHeight() * (pullDownY + Math.abs(pullUpY))));
            if (!isTouch) {
                if (state == REFRESHING && pullDownY <= refreshDist) {
                    pullDownY = refreshDist;
                    timer.cancel();
                } else if (state == LOADING && -pullUpY <= loadmoreDist) {
                    pullUpY = -loadmoreDist;
                    timer.cancel();
                }

            }
            if (pullDownY > 0)
                pullDownY -= MOVE_SPEED;
            else if (pullUpY < 0)
                pullUpY += MOVE_SPEED;
            if (pullDownY < 0) {
                refreshPullView.clearAnimation();
                if (state != REFRESHING && state != LOADING)
                    changeState(PULL_TO_REFRESH);
                timer.cancel();
            }
            if (pullUpY > 0) {
                pullUpY = 0;
                loadPullView.clearAnimation();
                if (state != REFRESHING && state != LOADING)
                    changeState(PULL_TO_REFRESH);
                timer.cancel();
            }
            requestLayout();
        }

    };

    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }

    public PullToRefreshLayout(Context context) {
        super(context);
        init(context);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        timer = new MyTimer(updateHandler);
        rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
            context, R.anim.pulltorefresh_reverse);
        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
            context, R.anim.pulltorefresh_rotating);
        LinearInterpolator lir = new LinearInterpolator();
        rotateAnimation.setInterpolator(lir);
        refreshingAnimation.setInterpolator(lir);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        refreshView = getChildAt(0);
        targetView = getChildAt(1);

        canLoadMore = false;
        if (getChildCount() > 2) {
            loadmoreView = getChildAt(2);
            canLoadMore = true;
        }

        initView();

        isLayout = false;
    }

    private void initView() {
        refreshPullView = refreshView.findViewById(R.id.pull_icon);
        refreshStateTextView = (TextView) refreshView
                .findViewById(R.id.state_tv);
        refreshingView = refreshView.findViewById(R.id.refreshing_icon);
        refreshStateImageView = refreshView.findViewById(R.id.state_iv);

        if (canLoadMore) {
            loadPullView = loadmoreView.findViewById(R.id.load_pull_icon);
            loadStateTextView = (TextView) loadmoreView
                    .findViewById(R.id.loadstate_tv);
            loadingView = loadmoreView.findViewById(R.id.loading_icon);
            loadStateImageView = loadmoreView.findViewById(R.id.loadstate_iv);
        }
    }

    private void hide() {
        timer.schedule(5);
    }

    public void setRefreshing(boolean refreshing){
        setRefreshing(refreshing, false);
    }
    public void setRefreshing(boolean refreshing, boolean hold) {
        if (refreshing) {
            changeState(REFRESHING);

            if (mListener != null) {
                mListener.onRefresh(this);
            }

        } else {
            refreshingView.clearAnimation();
            refreshingView.setVisibility(View.GONE);
            refreshStateImageView.setVisibility(View.VISIBLE);

            if(hold){
                /// show refresh result
                refreshStateTextView.setText(R.string.refresh_succeed);
                refreshStateImageView.setBackgroundResource(R.drawable.refresh_succeed);

                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        changeState(DONE);
                        hide();
                    }
                }.sendEmptyMessageDelayed(0, 500);
            }else{
                changeState(DONE);
                hide();
            }
        }
    }

    public void setLoading(boolean loading){
        setLoading(loading, false);
    }

    public void setLoading(boolean loading, boolean hold) {
        if(loading){
            changeState(LOADING);

            if (mListener != null) {
                mListener.onLoadMore(this);
            }
        }else {

            loadingView.clearAnimation();
            loadingView.setVisibility(View.GONE);
            loadStateImageView.setVisibility(View.VISIBLE);
            loadStateTextView.setText(R.string.load_succeed);
            loadStateImageView.setBackgroundResource(R.drawable.load_succeed);

            if (hold) {
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        changeState(DONE);
                        hide();
                    }
                }.sendEmptyMessageDelayed(0, 500);
            } else {
                changeState(DONE);
                hide();
            }
        }
    }

    @Deprecated
    public void refreshFinish(int refreshResult) {
        refreshingView.clearAnimation();
        refreshingView.setVisibility(View.GONE);
        switch (refreshResult) {
            case REFRESH_SUCCEED:
                refreshStateImageView.setVisibility(View.VISIBLE);
                refreshStateTextView.setText(R.string.refresh_succeed);
                refreshStateImageView
                        .setBackgroundResource(R.drawable.refresh_succeed);
                break;
            case REFRESH_FAIL:
            default:
                refreshStateImageView.setVisibility(View.VISIBLE);
                refreshStateTextView.setText(R.string.refresh_fail);
                refreshStateImageView
                        .setBackgroundResource(R.drawable.refresh_failed);
                break;
        }

        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                changeState(DONE);
                hide();
            }
        }.sendEmptyMessageDelayed(0, 300);
    }

    @Deprecated
    public void loadmoreFinish(int refreshResult) {
        loadingView.clearAnimation();
        loadingView.setVisibility(View.GONE);
        switch (refreshResult) {
            case REFRESH_SUCCEED:
                loadStateImageView.setVisibility(View.VISIBLE);
                loadStateTextView.setText(R.string.load_succeed);
                loadStateImageView.setBackgroundResource(R.drawable.load_succeed);
                break;

            case REFRESH_FAIL:
            default:
                loadStateImageView.setVisibility(View.VISIBLE);
                loadStateTextView.setText(R.string.load_fail);
                loadStateImageView.setBackgroundResource(R.drawable.load_failed);
                break;
        }
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                changeState(DONE);
                hide();
            }
        }.sendEmptyMessageDelayed(0, 300);
    }

    private void changeState(int to) {
        state = to;
        switch (state) {
            case PULL_TO_REFRESH:
                refreshStateImageView.setVisibility(View.GONE);
                refreshStateTextView.setText(R.string.pull_to_refresh);
                refreshPullView.clearAnimation();
                refreshPullView.setVisibility(View.VISIBLE);

                if (canLoadMore) {
                    loadStateImageView.setVisibility(View.GONE);
                    loadStateTextView.setText(R.string.pullup_to_load);
                    loadPullView.clearAnimation();
                    loadPullView.setVisibility(View.VISIBLE);
                }
                break;
            case RELEASE_TO_REFRESH:
                refreshStateTextView.setText(R.string.release_to_refresh);
                refreshPullView.startAnimation(rotateAnimation);
                break;
            case REFRESHING:
                refreshPullView.clearAnimation();
                refreshingView.setVisibility(View.VISIBLE);
                refreshPullView.setVisibility(View.INVISIBLE);
                refreshingView.startAnimation(refreshingAnimation);
                refreshStateTextView.setText(R.string.refreshing);
                break;
            case RELEASE_TO_LOAD:
                if (canLoadMore) {
                    loadStateTextView.setText(R.string.release_to_load);
                    loadPullView.startAnimation(rotateAnimation);
                } else {
                    Log.e(TAG, "should not be here with: " + state);
                }
                break;
            case LOADING:
                if (canLoadMore) {
                    loadPullView.clearAnimation();
                    loadingView.setVisibility(View.VISIBLE);
                    loadPullView.setVisibility(View.INVISIBLE);
                    loadingView.startAnimation(refreshingAnimation);
                    loadStateTextView.setText(R.string.loading);
                } else {
                    Log.e(TAG, "should not be here with: " + state);
                }
                break;
            case DONE:
                break;
        }
    }

    private void releasePull() {
        canPullDown = true;
        canPullUp = true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                lastY = downY;
                timer.cancel();
                mEvents = 0;
                releasePull();
                break;
				
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                mEvents = -1;
                break;
				
            case MotionEvent.ACTION_MOVE:
                if (mEvents == 0) {
                    if (canPullDown() && canPullDown
                            && state != LOADING) {
                        pullDownY = pullDownY + (ev.getY() - lastY) / radio;
                        if (pullDownY < 0) {
                            pullDownY = 0;
                            canPullDown = false;
                            canPullUp = true;
                        }
                        if (pullDownY > getMeasuredHeight())
                            pullDownY = getMeasuredHeight();
                        if (state == REFRESHING) {
                            isTouch = true;
                        }
                    } else if (canPullUp() && canPullUp
                            && state != REFRESHING) {
                        pullUpY = pullUpY + (ev.getY() - lastY) / radio;
                        if (pullUpY > 0) {
                            pullUpY = 0;
                            canPullDown = true;
                            canPullUp = false;
                        }
                        if (pullUpY < -getMeasuredHeight()){
                            pullUpY = -getMeasuredHeight();
						}
                        if (state == LOADING) {
                            isTouch = true;
                        }
                    } else{
                        releasePull();
					}
                } else{
                    mEvents = 0;
				}
				
				
                lastY = ev.getY();
                radio = (float) (2 + 2 * Math.tan(Math.PI / 2 / getMeasuredHeight()
                    * (pullDownY + Math.abs(pullUpY))));
                requestLayout();
                if (pullDownY <= refreshDist && state == RELEASE_TO_REFRESH) {
                    changeState(PULL_TO_REFRESH);
                }
                if (pullDownY >= refreshDist && state == PULL_TO_REFRESH) {
                    changeState(RELEASE_TO_REFRESH);
                }
                if (-pullUpY <= loadmoreDist && state == RELEASE_TO_LOAD) {
                    changeState(PULL_TO_REFRESH);
                }
                if (-pullUpY >= loadmoreDist && state == PULL_TO_REFRESH) {
                    changeState(RELEASE_TO_LOAD);
                }
                if ((pullDownY + Math.abs(pullUpY)) > 8) {
                    ev.setAction(MotionEvent.ACTION_CANCEL);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (pullDownY > refreshDist || -pullUpY > loadmoreDist) {
                    isTouch = false;
                }

                if (state == RELEASE_TO_REFRESH) {
                    changeState(REFRESHING);

                    if (mListener != null) {
                        mListener.onRefresh(this);
                    }
                } else if (state == RELEASE_TO_LOAD) {
                    changeState(LOADING);

                    if (mListener != null) {
                        mListener.onLoadMore(this);
                    }
                }
                hide();
            default:
                break;
        }
        super.dispatchTouchEvent(ev);
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!isLayout) {
            isLayout = true;
            refreshDist = ((ViewGroup) refreshView).getChildAt(0)
                    .getMeasuredHeight();

            if (canLoadMore) {
                loadmoreDist = ((ViewGroup) loadmoreView).getChildAt(0)
                        .getMeasuredHeight();
            }
        }

        refreshView.layout(0,
                (int) (pullDownY + pullUpY) - refreshView.getMeasuredHeight(),
                refreshView.getMeasuredWidth(), (int) (pullDownY + pullUpY));
        targetView.layout(0, (int) (pullDownY + pullUpY),
                targetView.getMeasuredWidth(), (int) (pullDownY + pullUpY)
                        + targetView.getMeasuredHeight());

        if (canLoadMore) {
            loadmoreView.layout(0,
                    (int) (pullDownY + pullUpY) + targetView.getMeasuredHeight(),
                    loadmoreView.getMeasuredWidth(),
                    (int) (pullDownY + pullUpY) + targetView.getMeasuredHeight()
                            + loadmoreView.getMeasuredHeight());
        }
    }

    class MyTimer {
        private Handler handler;
        private Timer timer;
        private MyTask mTask;

        public MyTimer(Handler handler) {
            this.handler = handler;
            timer = new Timer();
        }

        public void schedule(long period) {
            if (mTask != null) {
                mTask.cancel();
                mTask = null;
            }
            mTask = new MyTask(handler);
            timer.schedule(mTask, 0, period);
        }

        public void cancel() {
            if (mTask != null) {
                mTask.cancel();
                mTask = null;
            }
        }

        class MyTask extends TimerTask {
            private Handler handler;

            public MyTask(Handler handler) {
                this.handler = handler;
            }

            @Override
            public void run() {
                handler.obtainMessage().sendToTarget();
            }
        }
    }


    /**
     * interface OnRefreshListener
     */
    public interface OnRefreshListener {
        void onRefresh(PullToRefreshLayout pullToRefreshLayout);
        void onLoadMore(PullToRefreshLayout pullToRefreshLayout);
    }
}
