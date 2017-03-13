package com.wmmeng.quickadapterdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public abstract class BaseListViewFragment extends Fragment {

	ListView listview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
	}

	public abstract void initData();

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_listview, null);
		listview = (ListView) view.findViewById(R.id.listview);
		listview.setAdapter(getAdapter());
		return view;
	}
	
	protected abstract BaseAdapter getAdapter();
	
	
}
