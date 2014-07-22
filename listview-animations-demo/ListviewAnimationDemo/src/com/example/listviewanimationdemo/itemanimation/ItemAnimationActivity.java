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
package com.example.listviewanimationdemo.itemanimation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.listviewanimationdemo.R;
import com.example.listviewanimationdemo.base.BaseListActivity;
import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.ScaleInAnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingLeftInAnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingRightInAnimationAdapter;

import java.util.ArrayList;

public class ItemAnimationActivity extends BaseListActivity {

	private BaseAdapter mAdapter;

	public static void actionToItemAnimation(Context context) {
		Intent intent = new Intent(context, ItemAnimationActivity.class);
		context.startActivity(intent);
	}
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("item动画");
		mAdapter = new MyAdapter(this, getItems());
		setAlphaAdapter();
	}

	private void setAlphaAdapter() {
		AnimationAdapter animAdapter = new AlphaInAnimationAdapter(mAdapter);
		animAdapter.setAbsListView(getListView());
		getListView().setAdapter(animAdapter);
	}

	private void setLeftAdapter() {
		AnimationAdapter animAdapter = new SwingLeftInAnimationAdapter(mAdapter);
		animAdapter.setAbsListView(getListView());
		getListView().setAdapter(animAdapter);
	}

	private void setRightAdapter() {
		AnimationAdapter animAdapter = new SwingRightInAnimationAdapter(
				mAdapter);
		animAdapter.setAbsListView(getListView());
		getListView().setAdapter(animAdapter);
	}

	private void setBottomAdapter() {
		AnimationAdapter animAdapter = new SwingBottomInAnimationAdapter(
				mAdapter);
		animAdapter.setAbsListView(getListView());
		getListView().setAdapter(animAdapter);
	}

	private void setBottomRightAdapter() {
		AnimationAdapter animAdapter = new SwingBottomInAnimationAdapter(
				new SwingRightInAnimationAdapter(mAdapter));
		animAdapter.setAbsListView(getListView());
		getListView().setAdapter(animAdapter);
	}

	private void setScaleAdapter() {
		AnimationAdapter animAdapter = new ScaleInAnimationAdapter(mAdapter);
		animAdapter.setAbsListView(getListView());
		getListView().setAdapter(animAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_item_animation, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.menu_item_animation_alpha:
			setAlphaAdapter();
			break;
		case R.id.menu_item_animation_left:
			setLeftAdapter();
			break;
		case R.id.menu_item_animation_right:
			setRightAdapter();
			break;
		case R.id.menu_item_animation_bottom:
			setBottomAdapter();
			break;
		case R.id.menu_item_animation_bottom_right:
			setBottomRightAdapter();
			break;
		case R.id.menu_item_animation_scale:
			setScaleAdapter();
			break;
		default:
			break;
		}
		return true;
	}

	/* Non-ListViewAnimations related stuff below */

	class MyAdapter extends ArrayAdapter<Integer> {

		private final Context mContext;

		public MyAdapter(final Context context, final ArrayList<Integer> items) {
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
			tv.setText("******第 " + getItem(position) + "行******");
			return tv;
		}
	}
}