package com.wmmeng.quickadapterdemo;

import java.util.ArrayList;

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

import android.util.Log;
import android.widget.BaseAdapter;

public class SingleLayoutFragment extends BaseListViewFragment {
	ArrayList<String> data;
	BaseAdapter adapter;

	@Override
	public void initData() {
		data = new ArrayList<String>();
		for (int i = 0; i < 50; i++) {
			data.add("Hello QuickAdapter " + i);
		}
	}

	@Override
	protected BaseAdapter getAdapter() {
		Log.i("wmm", "getAdapter " + data.size());
		adapter = new QuickAdapter<String>(getActivity(),
				R.layout.item_single_layout, data) {

			@Override
			protected void convert(BaseAdapterHelper helper, String item) {
				Log.i("wmm", item);
				helper.setText(R.id.tv_text, item);
			}
		};
		return adapter;
	}
}
