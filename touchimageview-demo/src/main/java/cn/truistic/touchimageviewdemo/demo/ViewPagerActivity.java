package cn.truistic.touchimageviewdemo.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.truistic.touchimageviewdemo.R;
import cn.truistic.touchimageviewdemo.lib.ExtendedViewPager;

/**
 * ViewPager Example
 */
public class ViewPagerActivity extends AppCompatActivity {

    private int[] imgs = {R.drawable.img_normal_h, R.drawable.img_normal_v, R.drawable.img_small};
    private ExtendedViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        initView();
    }

    private void initView() {
        viewPager = (ExtendedViewPager) findViewById(R.id.pager_viewpager);
        DemoPagerAdapter adapter = new DemoPagerAdapter(this, imgs);
        viewPager.setAdapter(adapter);
    }

}
