package com.huxian.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huxian.R;
import com.huxian.activity.DefaultActivity;
import com.huxian.activity.ListViewActivity;
import com.huxian.activity.RecyclerViewActivity;
import com.huxian.activity.ViewPagerActivity;
import com.huxian.activity.WebViewActivity;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private String[] data;

    public MainAdapter(Context context, String[] data) {
        this.context = context;
        this.data = data;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.main_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvSlidingMenu.setText(data[position]);
        holder.layoutSlidingMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                redictTo(position);
            }
        });
    }

    public void redictTo(int position) {
        Intent intent = new Intent();
        switch(position) {
            case 0:
                intent.setClass(context, DefaultActivity.class);
                break;
            case 1:
                intent.setClass(context, ViewPagerActivity.class);
                break;
            case 2:
                intent.setClass(context, ListViewActivity.class);
                break;
            case 3:
                intent.setClass(context, RecyclerViewActivity.class);
                break;
            case 4:
                intent.setClass(context, WebViewActivity.class);
                break;
            default:
                break;
        }
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private RelativeLayout layoutSlidingMenu;
        private TextView tvSlidingMenu;

        public ViewHolder(View itemView) {
            super(itemView);
            layoutSlidingMenu = (RelativeLayout) itemView.findViewById(R.id.layout_main_item);
            tvSlidingMenu = (TextView) itemView.findViewById(R.id.tv_main_item);
        }
    }
}
