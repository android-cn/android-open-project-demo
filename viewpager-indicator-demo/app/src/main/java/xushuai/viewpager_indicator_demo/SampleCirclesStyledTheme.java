package xushuai.viewpager_indicator_demo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.CirclePageIndicator;

public class SampleCirclesStyledTheme extends BaseSampleActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //The look of this sample is set via a style in the manifest
        setContentView(R.layout.simple_circles_stroke);

        CircleAdapter mAdapter = new CircleAdapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        CirclePageIndicator  mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
//        mIndicator.setStrokeColor(0x535353);
//        indicator.setStrokeColor(0xFF000000);
        mIndicator.setViewPager(mPager);
    }
}