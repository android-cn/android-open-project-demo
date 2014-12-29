package com.dk.demo.matrix;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
	private ListView mListView;
	final private static String[] strings = new String[] { "ROTATE",
			"TRANSLATE", "SCALE", "SKEW" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (ListView) findViewById(R.id.list);
		mListView.setAdapter(new SubViewAdapter());
		mListView.setOnItemClickListener(mOnItemClickListener);
	}

	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(MainActivity.this, MatrixActivity.class);
			switch (position) {
			case 0:
				intent.putExtra(MatrixActivity.KEY_ACTION,
						MatrixActivity.ACTION_ROTATE);
				break;
			case 1:
				intent.putExtra(MatrixActivity.KEY_ACTION,
						MatrixActivity.ACTION_TRANSLATE);
				break;
			case 2:
				intent.putExtra(MatrixActivity.KEY_ACTION,
						MatrixActivity.ACTION_SCALE);
				break;
			case 3:
				intent.putExtra(MatrixActivity.KEY_ACTION,
						MatrixActivity.ACTION_SKEW);
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
