package com.redefine.aaron.holographlibrary_demo;


import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.echo.holographlibrary.Line;
import com.echo.holographlibrary.PieGraph;

public class MainActivity extends SherlockFragmentActivity {
        ViewPager mViewPager;
        TabsAdapter mTabsAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            mViewPager = (ViewPager) findViewById(R.id.view_pager);
            final com.actionbarsherlock.app.ActionBar bar = getSupportActionBar();
            bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            BarFragment barFrag = new BarFragment();
            LineFragment lineFragment = new LineFragment();
            PieFragment pieFragment = new PieFragment();

            mTabsAdapter = new TabsAdapter(this, mViewPager);
            mTabsAdapter.addTab(bar.newTab().setText("Line"),
                    LineFragment.class, null, lineFragment);
            mTabsAdapter.addTab(bar.newTab().setText("Pie"),
                    PieFragment.class, null, pieFragment);
            mTabsAdapter.addTab(bar.newTab().setText("Bar"),
                    BarFragment.class, null, barFrag);


            mViewPager.setOffscreenPageLimit(mTabsAdapter.getCount()-1);
        }


}
