package com.example.yyb.myapplication;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.example.yyb.myapplication.utils.CommonUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yyb on 15-3-8.
 */
public class MyFragment extends Fragment implements ActionBar.TabListener {

    @InjectView(R.id.innerviewpager)
    ViewPager viewpager;
    @InjectView(R.id.innertabs)
    PagerSlidingTabStrip tabs;

    private int id;
    private LinearLayout mTabsLinearLayout;

    public static MyFragment newInstance(int id) {
        MyFragment f = new MyFragment();
        f.id = id;
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, null);
        ButterKnife.inject(this, view);
        init();
        return view;
    }

    private void init() {
        initTabs();
        viewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return MyInnerFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return 6;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                CharSequence title ="";
                switch (id){
                    case 0:
                        title = "megtest" + position;
                        break;
                    case 1:
                        title = "newstest" + position;
                        break;
                    case 2:
                        title = "usertest" + position;
                        break;
                }
                return title;
            }
        });
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabs.setTranslationX(0);
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mTabsLinearLayout.getChildCount(); i++) {
                    TextView tv = (TextView) mTabsLinearLayout.getChildAt(i);
                    if (i == position) {

                        tv.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Medium.ttf"));
                    } else {
                        tv.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf"));

                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabs.setViewPager(viewpager);
        mTabsLinearLayout = (LinearLayout) tabs.getChildAt(0);
        for (int i = 0; i < mTabsLinearLayout.getChildCount(); i++) {
            TextView tv = (TextView) mTabsLinearLayout.getChildAt(i);
            if (i == 0) {

                tv.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Medium.ttf"));
            } else {
                tv.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Light.ttf"));

            }
        }
    }

    private void initTabs() {
        // tabtitle字体大写
        tabs.setAllCaps(true);
        //tab 宽度均分
        tabs.setShouldExpand(true);
        tabs.setTextSize(CommonUtil.dp2px(getActivity(), 18));
        //设置下划线
        tabs.setUnderlineColor(Color.BLACK);
        tabs.setUnderlineHeight(CommonUtil.dp2px(getActivity(), 1));
        //设置滑动指示线
        tabs.setIndicatorColor(Color.RED);
        tabs.setIndicatorHeight(CommonUtil.dp2px(getActivity(), 4));
        //设置tab间分割线
        tabs.setDividerColor(Color.TRANSPARENT);
        //设置背景颜色
        tabs.setBackgroundColor(getResources().getColor(R.color.write));


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewpager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
