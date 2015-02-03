package com.grumoon.androidultrapulltorefreshdemo.ui;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.grumoon.androidultrapulltorefreshdemo.R;
import com.grumoon.androidultrapulltorefreshdemo.util.Constants;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentImageViewFragment extends Fragment {


    //下拉次数
    private int ptrTimes;

    private PtrClassicFrameLayout ptr;

    private ImageView ivImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_content_image_view, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {

        ivImage = (ImageView) v.findViewById(R.id.iv_main);


        ptr = (PtrClassicFrameLayout) v.findViewById(R.id.ptr_main);

        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(final PtrFrameLayout ptrFrameLayout) {

                ImageLoader.getInstance().displayImage(Constants.VERTICAL_IMAGE_URLS[ptrTimes % Constants.VERTICAL_IMAGE_URLS.length], ivImage, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        ptr.refreshComplete();
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        ptr.refreshComplete();
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        ptr.refreshComplete();
                    }
                });

                ptrTimes++;
            }
        });


        ptr.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptr.autoRefresh();
            }
        }, 100);
    }


}
