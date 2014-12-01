package com.example.listviewanimationdemo.base;

import java.util.ArrayList;


import com.example.listviewanimationdemo.R;
import com.nhaarman.listviewanimations.ArrayAdapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class BaseListActivity extends FragmentActivity {

	private ListView mListView;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baselist);
		mListView = (ListView) findViewById(R.id.activity_baselist_listview);
		mListView.setDivider(null);
	}

	public ListView getListView() {
		return mListView;
	}

	protected ArrayAdapter<Integer> createListAdapter() {
		return new MyListAdapter(this, getItems());
	}

	public static ArrayList<Integer> getItems() {
		ArrayList<Integer> items = new ArrayList<Integer>();
		for (int i = 0; i < 1000; i++) {
			items.add(i);
		}
		return items;
	}

	private static class MyListAdapter extends ArrayAdapter<Integer> {

		private final Context mContext;

		public MyListAdapter(final Context context,
				final ArrayList<Integer> items) {
			super(items);
			mContext = context;
		}

		@Override
		public long getItemId(final int position) {
			return getItem(position).hashCode();
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public View getView(final int position, final View convertView,
				final ViewGroup parent) {
			TextView tv = (TextView) convertView;
			if (tv == null) {
				tv = (TextView) LayoutInflater.from(mContext).inflate(
						R.layout.item_list_row, parent, false);
			}
			tv.setText("第 " + getItem(position)+"行");
			return tv;
		}
	}

}
