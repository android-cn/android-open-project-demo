/*
 * Copyright 2013 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.listviewanimationdemo.itemmanipulation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.listviewanimationdemo.R;
import com.example.listviewanimationdemo.base.BaseListActivity;
import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.AnimateAdditionAdapter;

import java.util.ArrayList;

public class AnimateAdditionActivity extends BaseListActivity implements
		AdapterView.OnItemClickListener {

	private int mAddedItemNumber;
	private AnimateAdditionAdapter<String> mAnimateAdditionAdapter;

	public static void actionToItemAddition(Context context) {
		Intent intent = new Intent(context, AnimateAdditionActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("增加item");
		MyAdapter myAdapter = new MyAdapter(this, getStringItems());

		mAnimateAdditionAdapter = new AnimateAdditionAdapter<String>(myAdapter);
		mAnimateAdditionAdapter.setListView(getListView());

		getListView().setAdapter(mAnimateAdditionAdapter);
		getListView().setOnItemClickListener(this);

		Toast.makeText(this, "点击某一项来增加一项", Toast.LENGTH_LONG).show();
	}

	private static ArrayList<String> getStringItems() {
		ArrayList<String> items = new ArrayList<String>();
		for (int i = 0; i < 1000; i++) {
			items.add(">>>第 " + i + "行===");
		}
		return items;
	}

	@Override
	public void onItemClick(final AdapterView<?> parent, final View view,
			final int position, final long id) {
		mAnimateAdditionAdapter.insert(position, "***新增加的item: "
				+ mAddedItemNumber);
		mAddedItemNumber++;
	}

	private static class MyAdapter extends ArrayAdapter<String> {

		private final Context mContext;

		public MyAdapter(final Context context, final ArrayList<String> items) {
			super(items);
			mContext = context;
		}

		@Override
		public long getItemId(final int position) {
			return getItem(position).hashCode();
		}

		@Override
		public View getView(final int position, final View convertView,
				final ViewGroup parent) {
			TextView tv = (TextView) convertView;
			if (tv == null) {
				tv = (TextView) LayoutInflater.from(mContext).inflate(
						R.layout.item_list_row, parent, false);
			}
			tv.setText(getItem(position));
			return tv;
		}
	}
}
