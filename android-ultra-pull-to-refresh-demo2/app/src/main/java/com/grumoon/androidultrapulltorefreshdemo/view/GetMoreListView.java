package com.grumoon.androidultrapulltorefreshdemo.view;

import android.content.Context;

import android.util.AttributeSet;

import android.view.LayoutInflater;

import android.view.View;

import android.widget.AbsListView;

import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.grumoon.androidultrapulltorefreshdemo.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class GetMoreListView extends ListView {


    // 加载更多接口
    public interface OnGetMoreListener {
        public void onGetMore();
    }

    private static final String TAG = GetMoreListView.class.getSimpleName();


    private LayoutInflater inflater;


    // 加载更多视图（底部视图）
    private View footView;

    // 加载更多文字
    private TextView tvFootTitle;

    // 加载更多忙碌框
    private ProgressBar pbFootRefreshing;


    // 是否已经添加了footer
    private boolean addFooterFlag;

    // 是否还有数据标志
    private boolean hasMoreDataFlag = true;

    /**
     * Scroll时到达最后一个Item的次数，只有第一次能触发自动刷新
     */
    private int reachLastPositionCount = 0;


    private OnGetMoreListener getMoreListener;


    private boolean isGetMoreing = false;

    public GetMoreListView(Context context) {
        this(context, null);
    }

    public GetMoreListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GetMoreListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context, AttributeSet attrs) {


        inflater = LayoutInflater.from(context);

        /**
         * 底部
         */
        footView = inflater.inflate(R.layout.get_more_list_view_foot, this, false);
        tvFootTitle = (TextView) footView.findViewById(R.id.tv_foot_title);
        pbFootRefreshing = (ProgressBar) footView.findViewById(R.id.pb_foot_refreshing);

        // 滑动监听
        setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                doOnScrollStateChanged(view, scrollState);

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                doOnScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);

            }
        });

    }


    // 加载更多
    private void getMore() {
        if (getMoreListener != null) {
            isGetMoreing = true;
            pbFootRefreshing.setVisibility(View.VISIBLE);
            tvFootTitle.setText("正在加载...");
            getMoreListener.onGetMore();
        }
    }


    /**
     * 判断是否可以自动加载更多<br/>
     *
     * @return
     */
    private boolean checkCanAutoGetMore() {
        if (footView == null) {
            return false;
        }
        if (getMoreListener == null) {
            return false;
        }
        if (isGetMoreing) {
            return false;
        }
        if (!hasMoreDataFlag) {
            return false;
        }
        if (getAdapter() == null) {
            return false;
        }
        if (!canScroll(1) && !canScroll(-1)) {
            return false;
        }
        if (getLastVisiblePosition() != getAdapter().getCount() - 1) {
            return false;
        }
        if (reachLastPositionCount != 1) {
            return false;
        }
        return true;
    }

    /**
     * 判断ListView是否可以滑动
     *
     * @param direction
     * @return
     */
    private boolean canScroll(int direction) {
        final int childCount = getChildCount();
        if (childCount == 0) {
            return false;
        }

        final int firstPosition = getFirstVisiblePosition();
        final int listPaddingTop = getPaddingTop();
        final int listPaddingBottom = getPaddingTop();
        final int itemCount = getAdapter().getCount();

        if (direction > 0) {
            final int lastBottom = getChildAt(childCount - 1).getBottom();
            final int lastPosition = firstPosition + childCount;
            return lastPosition < itemCount || lastBottom > getHeight() - listPaddingBottom;
        } else {
            final int firstTop = getChildAt(0).getTop();
            return firstPosition > 0 || firstTop < listPaddingTop;
        }
    }

    /**
     * 设置加载更多监听器
     *
     * @param getMoreListener
     */
    public void setOnGetMoreListener(OnGetMoreListener getMoreListener) {
        this.getMoreListener = getMoreListener;
        if (!addFooterFlag) {
            addFooterFlag = true;
            this.addFooterView(footView);
        }
    }


    /**
     * 加载更多完成
     */
    public void getMoreComplete() {
        isGetMoreing = false;
        pbFootRefreshing.setVisibility(View.GONE);
        tvFootTitle.setText("加载更多");
    }

    /**
     * 设置没有更多的数据了<br/>
     * 不再显示加载更多按钮
     */
    public void setNoMore() {
        hasMoreDataFlag = false;
        if (footView != null) {
            footView.setVisibility(View.GONE);
        }
    }

    /**
     * 显示加载更多按钮
     */
    public void setHasMore() {
        hasMoreDataFlag = true;
        if (footView != null) {
            footView.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 如果项目中其他地方需要重新设置GetMoreListView的OnScrollListener<br/>
     * 请在新的listener中onScrollStateChanged方法内调用此方法，保证PullListView正常运行。
     *
     * @param view
     * @param scrollState
     */
    public void doOnScrollStateChanged(AbsListView view, int scrollState) {

        switch (scrollState) {
            case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                break;
            case OnScrollListener.SCROLL_STATE_FLING:
                // 滑动时候停止加载图片
                ImageLoader.getInstance().pause();
                break;

            case OnScrollListener.SCROLL_STATE_IDLE:
                // 停下后恢复加载图片
                ImageLoader.getInstance().resume();
                break;
            default:
                break;
        }

    }

    /**
     * 如果项目中其他地方需要重新设置GetMoreListView的OnScrollListener<br/>
     * 请在新的listener中onScroll方法内调用此方法，保证PullListView正常运行。
     *
     * @param view
     * @param firstVisibleItem
     * @param visibleItemCount
     * @param totalItemCount
     */
    public void doOnScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (getAdapter() == null) {
            return;
        }

        if (getLastVisiblePosition() == getAdapter().getCount() - 1) {
            reachLastPositionCount++;
        } else {
            reachLastPositionCount = 0;
        }

        if (checkCanAutoGetMore()) {
            getMore();
        }

    }

}
