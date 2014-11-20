package com.android.caij.xutilsdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.caij.xutilsdemo.R;
import com.lidroid.xutils.ViewUtils;

public class BitMapFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.db_fragment_view, container,
				false);
		ViewUtils.inject(this, view);
		
		return view;
	}
}
