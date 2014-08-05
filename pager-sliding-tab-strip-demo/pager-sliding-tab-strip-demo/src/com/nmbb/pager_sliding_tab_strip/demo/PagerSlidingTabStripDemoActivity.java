package com.nmbb.pager_sliding_tab_strip.demo;

import java.util.Random;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

public class PagerSlidingTabStripDemoActivity extends FragmentActivity {

	private PagerSlidingTabStrip mPagerSlidingTabStrip;
	private ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);

		mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		mViewPager = (ViewPager) findViewById(R.id.pager);

		//设置页面之间的间隔，实际项目中应该用dip转px
		mViewPager.setPageMargin(10);

		initPageAdapter();

		//
		mPagerSlidingTabStrip.setViewPager(mViewPager);

	}

	/** 初始化Adapter */
	private void initPageAdapter() {
		mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

			@Override
			public Fragment getItem(int position) {
				return PagerSlidingTabStripFragment.newInstance(position);
			}

			@Override
			public int getCount() {
				return 10;
			}

			@Override
			public CharSequence getPageTitle(int position) {
				return getString(R.string.fragment_name, position + 1);
			}
		});
	}

	public static final class PagerSlidingTabStripFragment extends Fragment {

		public static PagerSlidingTabStripFragment newInstance(int position) {
			PagerSlidingTabStripFragment fragment = new PagerSlidingTabStripFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("position", position);
			fragment.setArguments(bundle);
			return fragment;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.fragment_demo, container, false);
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);

			TextView text1 = (TextView) view.findViewById(android.R.id.text1);
			if (getArguments() != null) {
				text1.setText(getString(R.string.fragment_name, getArguments().getInt("position") + 1));
			}

			//背景随机色
			Random rnd = new Random();
			view.setBackgroundColor(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
		}
	}
}
