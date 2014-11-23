package com.dk.demo.photoview;

import uk.co.senab.photoview.PhotoView;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class ViewPagerDemo extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager);
		ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
		pager.setAdapter(new DemoAdapter());
	}

	class DemoAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			PhotoView photoview = new PhotoView(ViewPagerDemo.this);
			photoview.setImageResource(R.drawable.saya);
			container.addView(photoview, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			return photoview;
		}

	}
}
