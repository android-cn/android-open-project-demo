package com.example.yyb.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.astuetz.PagerSlidingTabStrip;

/**
 * Created by yyb on 15-3-8.
 */
public class PagerSlidingTabStripNoScrool extends PagerSlidingTabStrip {
    public PagerSlidingTabStripNoScrool(Context context) {
        super(context);
    }

    public PagerSlidingTabStripNoScrool(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PagerSlidingTabStripNoScrool(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
