package com.demo.stickylistheaders;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by panwenye on 14-7-13.
 */
public class DemoListAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    List<Contact> mDataSource;
    Context mContext;

    DemoListAdapter(Context context,List<Contact> dataSource){
        if(context==null||dataSource==null){
            throw new IllegalArgumentException();
        }
        mContext = context;
        mDataSource = dataSource;
    }

    @Override
    public View getHeaderView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view = new TextView(mContext);
            ((TextView) view).setGravity(Gravity.CENTER_VERTICAL);
            view.setBackgroundColor(Color.parseColor("#99000000"));
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,50);
            view.setLayoutParams(lp);
        }
        ((TextView) view).setText(mDataSource.get(i).sort_key.substring(0, 1));
        return view;
    }

    @Override
    public long getHeaderId(int i) {
        //获取联系人名称的首字母作为id
        return mDataSource.get(i).sort_key.charAt(0);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view = new TextView(mContext);
            ((TextView) view).setGravity(Gravity.CENTER_VERTICAL);
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100);
            view.setLayoutParams(lp);
        }
        ((TextView)view).setText(mDataSource.get(i).name);
        return view;
    }

}
