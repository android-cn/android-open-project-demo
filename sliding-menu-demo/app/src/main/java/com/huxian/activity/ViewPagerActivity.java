package com.huxian.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.huxian.R;
import com.huxian.adapter.ViewPagerAdapter;

public class ViewPagerActivity extends BaseActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }
}
