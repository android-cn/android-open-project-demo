package com.huxian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.huxian.Constants;
import com.huxian.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by huxian on 14-11-20.
 */
public class PhotoAdapter extends BaseAdapter {

    private DisplayImageOptions options;
    private LayoutInflater inflater;
    private String[] photos;

    public PhotoAdapter(Context context) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.photos = Constants.PHOTOS;
        this.options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.loading)
                .showImageOnLoading(R.drawable.loading)
                .showImageOnFail(R.drawable.loading)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    @Override
    public int getCount() {
        return photos == null ? 0 : photos.length;
    }

    @Override
    public Object getItem(int position) {
        return photos == null ? null : photos[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_photo_layout, null);
            holder.itemPhoto = (ImageView) convertView.findViewById(R.id.item_photo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(photos[position], holder.itemPhoto, options);
        return convertView;
    }

    private static class ViewHolder {
        ImageView itemPhoto;
    }

}
