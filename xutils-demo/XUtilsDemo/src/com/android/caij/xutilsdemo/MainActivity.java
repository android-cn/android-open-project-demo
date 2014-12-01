package com.android.caij.xutilsdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.caij.xutilsdemo.fragment.BitMapFragment;
import com.android.caij.xutilsdemo.fragment.DbFragment;
import com.android.caij.xutilsdemo.fragment.DownFragment;
import com.android.caij.xutilsdemo.fragment.HttpFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnRadioGroupCheckedChange;

@ContentView(R.layout.activity_main)
public class MainActivity extends FragmentActivity {

	private final static Fragment[] FRAGMENTS = { new DbFragment(),
			new HttpFragment(), new DownFragment(), new BitMapFragment() };

	@ViewInject(R.id.main_vp_container)
	private ViewPager mViewPager;
	@ViewInject(R.id.main_rg_tab)
	private RadioGroup mRadioGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this); //注入view和事件
		mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				((RadioButton)mRadioGroup.getChildAt(arg0)).setChecked(true);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}
	
	private class ViewPagerAdapter extends FragmentPagerAdapter {

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return FRAGMENTS[position];
		}

		@Override
		public int getCount() {
			return 4;
		}

	}

	@OnRadioGroupCheckedChange(R.id.main_rg_tab)
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.main_rb_db:
			mViewPager.setCurrentItem(0);
			break;

		case R.id.main_rb_http:
			mViewPager.setCurrentItem(1);
			break;

		case R.id.main_rb_down:
			mViewPager.setCurrentItem(2);
			break;
			
		case R.id.main_rb_bitmap:
			mViewPager.setCurrentItem(3);
			break;

		default:
			break;
		}
	}
}
