package com.dk.demo.photoview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private ListView mListView;
	private static final int DEMO_FULL_SCREEN = 0;
	private static final int DEMO_WITH_VIEWPAGER = 1;
	private static final int DEMO_INTEGRATION_WITH_IMAGELOADER = 2;
	private static final int DEMO_INTEGRATION_WITH_PICASSO = 3;

	private String strings[] = new String[] { "Full Screen",
			"Demo with ViewPager",
			"Integration with ImageLoader", "Integration with Picasso" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (ListView) findViewById(R.id.mian_list);
		mListView.setAdapter(new SubViewAdapter());
		mListView.setOnItemClickListener(mOnItemClickListener);
	}

	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapterView, View item,
				int position, long arg3) {
			Intent intent = null;
			switch (position) {
			case DEMO_FULL_SCREEN:
				intent = new Intent(MainActivity.this, FullScreenDemo.class);
				break;
			case DEMO_WITH_VIEWPAGER:
				intent = new Intent(MainActivity.this, ViewPagerDemo.class);
				break;
			case DEMO_INTEGRATION_WITH_IMAGELOADER:
				intent = new Intent(MainActivity.this, ImageLoaderDemo.class);
				break;
			case DEMO_INTEGRATION_WITH_PICASSO:
				intent = new Intent(MainActivity.this, PicassoDemo.class);
				break;
			}
			startActivity(intent);
		}
	};

	class SubViewAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return strings.length;
		}

		@Override
		public Object getItem(int position) {
			return strings[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(MainActivity.this).inflate(
						R.layout.view_mainlist_item, null);
			}
			((TextView) convertView.findViewById(R.id.list_item_text))
					.setText(strings[position]);
			return convertView;
		}

	}

}
