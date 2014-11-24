package com.grumoon.volleydemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.grumoon.volleydemo.R;

public abstract class ImageBaseAdapter extends BaseAdapter {

	private String[] imageUrlArray;
	private LayoutInflater inflater;

	public ImageBaseAdapter(Context context,String[] imageUrlArray) {
		
		this.imageUrlArray = imageUrlArray;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if (imageUrlArray == null) {
			return 0;
		} else {
			return imageUrlArray.length;
		}
	}

	@Override
	public Object getItem(int position) {
		if (imageUrlArray == null || position >= imageUrlArray.length) {
			return null;
		} else {
			return imageUrlArray[position];
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		if(convertView==null)
		{
			convertView=this.inflater.inflate(getItemLayout(), parent,false);
			viewHolder=new ViewHolder();
			viewHolder.ivCar=(ImageView)convertView.findViewById(R.id.iv_car);
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder=(ViewHolder)convertView.getTag();
		}
		
		setImage(viewHolder.ivCar, imageUrlArray[position]);
		
		return convertView;
	}

	static class ViewHolder {
		ImageView ivCar;
	}
	
	
	abstract int getItemLayout();
	
	abstract void setImage(ImageView imageView,String imageUrl);

}
