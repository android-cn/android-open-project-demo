package xushuai.viewpager_indicator_demo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.UnderlinePageIndicator;

//仿微信
public class UnderlinesNoFade extends FragmentActivity implements View.OnClickListener{
    private String CONTENT[] = {"主页","发现","动态"};
    private TextView tab_left;
    private TextView tab_right;
    private TextView tab_mid;
    private UnderLineFragmentAdapter mAdapter;
    private ViewPager mPager;
    private UnderlinePageIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_underlines);

        mAdapter = new UnderLineFragmentAdapter(getSupportFragmentManager());
        mAdapter.setContent(CONTENT);

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mIndicator = (UnderlinePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.setFades(false);

        tab_left = (TextView) findViewById(R.id.tab_left);
        tab_mid = (TextView) findViewById(R.id.tab_mid);
        tab_right = (TextView) findViewById(R.id.tab_right);

        tab_left.setOnClickListener(this);
        tab_mid.setOnClickListener(this);
        tab_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tab_left:
                mPager.setCurrentItem(0);
                break;
            case R.id.tab_mid:
                mPager.setCurrentItem(1);
                break;
            case R.id.tab_right:
                mPager.setCurrentItem(2);
                break;
        }
    }
}