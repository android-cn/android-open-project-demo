package com.example.yyb.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.yyb.myapplication.adapter.FragmentPagterIconTabAdapter;
import com.example.yyb.myapplication.utils.CommonUtil;
import com.example.yyb.myapplication.utils.MyToast;
import com.example.yyb.myapplication.view.PagerSlidingTabStripNoScrool;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends FragmentActivity implements TabListener {

    @InjectView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @InjectView(R.id.viewpager)
    ViewPager viewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        init();

    }

    private void init() {
        //tab 宽度均分
        tabs.setShouldExpand(true);
        tabs.setDividerColor(Color.TRANSPARENT);
        //设置选中的滑动指示
        tabs.setIndicatorColor(Color.GREEN);
        tabs.setIndicatorHeight(CommonUtil.dp2px(this,48));
        //设置背景颜色
        tabs.setBackgroundColor(getResources().getColor(R.color.write));

        viewpager.setAdapter(new FragmentPagterIconTabAdapter(getSupportFragmentManager()));
        tabs.setOnPageChangeListener(mPagerChangerListener);
        tabs.setViewPager(viewpager);


    }


    OnPageChangeListener mPagerChangerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            tabs.setTranslationX(0);
        }

        @Override
        public void onPageSelected(int position) {
            switch (position){
                case 0:
                    MyToast.makeText(MainActivity.this, "the page is " + "messager", Toast.LENGTH_SHORT);
                    break;
                case 1:
                    MyToast.makeText(MainActivity.this, "the page is " + "news", Toast.LENGTH_SHORT);
                    break;
                case 2:
                    MyToast.makeText(MainActivity.this, "the page is " + "user", Toast.LENGTH_SHORT);
                    break;
            }


        }

        @Override
        public void onPageScrollStateChanged(int state) {


        }
    };


    @Override
    public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {

        viewpager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
