package xushuai.viewpager_indicator_demo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.TitlePageIndicator;

public class SampleTitlesDefault extends BaseSampleActivity {
    private static final String[] CONTENT = new String[] { "推荐", "科技", "原创", "房产"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_titles);

        mAdapter = new TestFragmentAdapter(getSupportFragmentManager());
        mAdapter.setContent(CONTENT);

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        mIndicator = (TitlePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
    }
}