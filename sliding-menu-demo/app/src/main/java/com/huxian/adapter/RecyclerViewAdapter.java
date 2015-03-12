package com.huxian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.huxian.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private LayoutInflater inflater;
    private String[] imgUrls;
    private DisplayImageOptions options;

    public RecyclerViewAdapter(Context context, String[] imgUrls) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imgUrls = imgUrls;
        this.options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageForEmptyUri(R.drawable.icon_empty)
                .showImageOnFail(R.drawable.icon_empty)
                .showImageOnLoading(R.drawable.icon_empty)
                .build();
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        ImageLoader.getInstance().displayImage(imgUrls[position], holder.imageView, options);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_view_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return imgUrls == null ? 0 : imgUrls.length;
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_recycler_view_item);

        }
    }
}
