package com.grumoon.androidultrapulltorefreshdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.grumoon.androidultrapulltorefreshdemo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by grumoon on 15/1/17.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter {

    private LayoutInflater layoutInflater;

    private String[] imageUrls;

    private DisplayImageOptions options;

    public RecyclerViewAdapter(Context context, String[] imageUrls) {

        this.layoutInflater = LayoutInflater.from(context);

        this.imageUrls = imageUrls;

        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_empty_large)
                .showImageOnLoading(R.drawable.ic_empty_large)
                .showImageOnFail(R.drawable.ic_empty_large)
                .build();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.recycler_view_item, parent, false);
        return new CarViewHoler(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ImageLoader.getInstance().displayImage(imageUrls[position], ((CarViewHoler) holder).ivCar, options);

    }

    @Override
    public int getItemCount() {
        if (imageUrls == null) {
            return 0;
        }
        return imageUrls.length;
    }


    static class CarViewHoler extends RecyclerView.ViewHolder {

        ImageView ivCar;

        public CarViewHoler(View itemView) {
            super(itemView);
            ivCar = (ImageView) itemView.findViewById(R.id.iv_car);

        }
    }
}
