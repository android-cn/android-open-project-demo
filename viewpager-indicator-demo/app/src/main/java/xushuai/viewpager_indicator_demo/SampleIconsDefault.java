package xushuai.viewpager_indicator_demo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.viewpagerindicator.IconPageIndicator;

public class SampleIconsDefault extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_icons);

        getSupportActionBar().setCustomView(R.layout.actionbar_icon_indicator);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        TestFragmentAdapter mAdapter = new TestFragmentAdapter(getSupportFragmentManager());
        ViewPager mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        IconPageIndicator  mIndicator = (IconPageIndicator) getSupportActionBar().getCustomView().findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
    }
}
