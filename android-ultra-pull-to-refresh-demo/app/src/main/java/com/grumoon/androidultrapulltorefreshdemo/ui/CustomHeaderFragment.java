package com.grumoon.androidultrapulltorefreshdemo.ui;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.grumoon.androidultrapulltorefreshdemo.R;
import com.grumoon.androidultrapulltorefreshdemo.header.WindmillHeader;
import com.grumoon.androidultrapulltorefreshdemo.util.Constants;
import com.grumoon.androidultrapulltorefreshdemo.util.DisplayUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomHeaderFragment extends Fragment {


    //下拉次数
    private int ptrTimes;

    private PtrFrameLayout ptr;

    private ImageView ivImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_header, container, false);
        initView(view);
        return view;
    }


    private void initView(View v) {
        ptr = (PtrFrameLayout) v.findViewById(R.id.ptr_main);

        ivImage = (ImageView) v.findViewById(R.id.iv_main);

        final WindmillHeader header = new WindmillHeader(getActivity());

        final ImageLoadingListener listener = new ImageLoadingListener() {
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
        };


        ptr.setHeaderView(header);
        ptr.addPtrUIHandler(header);


        ptr.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ImageLoader.getInstance().displayImage(Constants.VERTICAL_IMAGE_URLS[ptrTimes % Constants.VERTICAL_IMAGE_URLS.length], ivImage, listener);

                        ptrTimes++;
                    }
                },3000);

            }
        });


        ptr.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptr.autoRefresh(false);
            }
        }, 100);

    }

}
