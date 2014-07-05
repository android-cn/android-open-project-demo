package com.caesar.PSL_demo.adapter;

import java.util.ArrayList;

import com.caesar.PSL_demo.R;
import com.caesar.PSL_demo.bean.Item;
import com.caesar.PSL_demo.library.PinnedSectionListView.PinnedSectionListAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @description PSL 适配器
 *
 * @author selfimpr
 * 
 * @date 2014-7-4 下午5:10:47
 * 
 */
public class PinnedAdapter extends BaseAdapter implements PinnedSectionListAdapter{
	
	private ArrayList<Item> mData ;
	
	private Context mContext;
	
	public PinnedAdapter(Context context, ArrayList<Item> data) {
		this.mContext = context;
		this.mData = data;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView =LayoutInflater.from(mContext).inflate(R.layout.pinned_item, parent,false);
		}
		TextView textView = (TextView) convertView;
		textView.setText(mData.get(position).content);
		if(mData.get(position).type==Item.SECTION){
			textView.setBackgroundColor(Color.parseColor("#ff33b5e5"));
		}
		return textView;
	}

	@Override
	public boolean isItemViewTypePinned(int viewType) {
		return viewType == Item.SECTION;
	}
	
	@Override
	public int getItemViewType(int position) {
		return mData.get(position).type;
	}
	@Override
	public int getViewTypeCount() {
		return 2;
	}

}
