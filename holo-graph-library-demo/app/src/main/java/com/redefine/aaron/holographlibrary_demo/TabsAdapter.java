package com.redefine.aaron.holographlibrary_demo;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;

import java.util.ArrayList;


/**
 * Created by aaron on 14-11-22.
 */
public  class TabsAdapter extends FragmentStatePagerAdapter implements com.actionbarsherlock.app.ActionBar.TabListener, ViewPager.OnPageChangeListener {

    private final MainActivity mContext;
    private final ActionBar mActionBar;
    private final ViewPager mViewPager;
    private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
    private final ArrayList<Fragment> mFrags = new ArrayList<Fragment>();

    public TabsAdapter(MainActivity context, ViewPager viewPager) {
        super(context.getSupportFragmentManager());
        mContext = context;
        mViewPager = viewPager;
        mActionBar=context.getSupportActionBar();
        mViewPager.setAdapter(this);
        mViewPager.setOnPageChangeListener(this);
    }

    public void addTab(ActionBar.Tab tab,Class<?> clss,Bundle args,Fragment frag){
        TabInfo info = new TabInfo(clss,args);
        tab.setTag(info);
        tab.setTabListener(this);
        mTabs.add(info);
        mFrags.add(frag);
        mActionBar.addTab(tab);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        return mFrags.get(i);
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {
        mActionBar.setSelectedNavigationItem(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public void onTabSelected(com.actionbarsherlock.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
        Object tag = tab.getTag();
        for(int i = 0 ; i<mTabs.size();i++){
            if(mTabs.get(i)==tag){
                mViewPager.setCurrentItem(i);
            }
        }
    }

    @Override
    public void onTabUnselected(com.actionbarsherlock.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(com.actionbarsherlock.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    static final class TabInfo {
        private final Class<?> clss;
        private final Bundle args;

        TabInfo(Class<?> clss, Bundle args) {
            this.clss = clss;
            this.args = args;
        }
    }
}