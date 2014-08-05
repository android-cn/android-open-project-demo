package com.mingy.fancycoverflow.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import at.technikum.mti.fancycoverflow.FancyCoverFlow.LayoutParams;
import at.technikum.mti.fancycoverflow.FancyCoverFlowAdapter;

public class FancyCoverFlowBaseAdapter extends FancyCoverFlowAdapter {
	public FancyCoverFlowBaseAdapter( Context context, Integer[] dataList ){
		mDataList = dataList;
	}
	
	@Override
	public int getCount() {
		return mDataList.length;
	}

	@Override
	public Object getItem(int position) {
		return mDataList[ position ];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getCoverFlowItem(int position, View reusableView, ViewGroup parent) {
		ImageView imageView = null;

        if (reusableView != null) {
            imageView = (ImageView) reusableView;
        } else {
            imageView = new ImageView(parent.getContext());
            imageView.setLayoutParams(new FancyCoverFlow.LayoutParams(LayoutParams.WRAP_CONTENT,256));
        }

        imageView.setBackgroundResource( mDataList[ position ] );
        
        return imageView;
	}

	private Integer[] mDataList = null;
}
