package com.grumoon.androidultrapulltorefreshdemo.header;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.grumoon.androidultrapulltorefreshdemo.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;

/**
 * Created by grumoon on 2015/1/20.
 */
public class WindmillHeader extends FrameLayout implements PtrUIHandler {

    private LayoutInflater inflater;

    // 下拉刷新视图（头部视图）
    private ViewGroup headView;

    // 下拉刷新文字
    private TextView tvHeadTitle;

    // 下拉图标
    private ImageView ivWindmill;

    private WindmillDrawable drawable;

    public WindmillHeader(Context context) {
        this(context, null);
    }

    public WindmillHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WindmillHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {

        inflater = LayoutInflater.from(context);
        /**
         * 头部
         */
        headView = (ViewGroup) inflater.inflate(R.layout.windmill_header, this, true);
        ivWindmill = (ImageView) headView.findViewById(R.id.iv_windmill);
        tvHeadTitle = (TextView) headView.findViewById(R.id.tv_head_title);
        drawable = new WindmillDrawable(context, ivWindmill);
        ivWindmill.setImageDrawable(drawable);


    }

    @Override
    public void onUIReset(PtrFrameLayout ptrFrameLayout) {
        tvHeadTitle.setText("下拉刷新");
        drawable.stop();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout ptrFrameLayout) {
        tvHeadTitle.setText("下拉刷新");
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout ptrFrameLayout) {
        tvHeadTitle.setText("正在刷新");
        drawable.start();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout ptrFrameLayout) {
        ivWindmill.clearAnimation();
        tvHeadTitle.setText("刷新完成");
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, int lastPos, int currentPos, float oldPercent, float currentPercent) {
        if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
            drawable.postRotation(currentPos - lastPos);
            invalidate();
        }

        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                tvHeadTitle.setText("下拉刷新");

            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                tvHeadTitle.setText("松开刷新");
            }
        }


    }
}
