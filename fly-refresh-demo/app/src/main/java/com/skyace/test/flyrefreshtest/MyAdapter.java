package com.skyace.test.flyrefreshtest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/30 0030.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<Integer> mDataset; // 外面传入的数据

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;

        // TODO Auto-generated method stub
        public ViewHolder(View v) {
            super(v);
        }

    }

    public MyAdapter(ArrayList<Integer> mDataset) {
        this.mDataset = mDataset;
    }

    /**
     * 获取总的条目数量
     */
    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return mDataset.size();
    }

    /**
     * 增加一条条目
     */
//    public void addItem(){
//        mDataset.add(R.drawable.refresh_image);
//        this.notifyDataSetChanged();
//    }


    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // TODO Auto-generated method stub
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview, parent, false);
        ViewHolder holder = new ViewHolder(v);
        holder.mImageView = (ImageView) v.findViewById(R.id.iv_image);
        return holder;
    }

    /**
     * 将数据绑定到ViewHolder上
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // TODO Auto-generated method stub
        holder.mImageView.setImageResource(mDataset.get(position));
    }
}