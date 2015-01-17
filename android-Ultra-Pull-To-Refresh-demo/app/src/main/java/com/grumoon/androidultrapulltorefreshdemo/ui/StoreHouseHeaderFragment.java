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
import com.grumoon.androidultrapulltorefreshdemo.util.DisplayUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreHouseHeaderFragment extends Fragment {
    public static final String[] HEADER_STRING_ARRAY = {"ULTRA-PTR", "Grumoon"};

    //下拉次数
    private int ptrTimes;

    private PtrFrameLayout ptr;

    private ImageView ivImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_house_header, container, false);
        initView(view);
        return view;


    }


    private void initView(View v) {
        ptr = (PtrFrameLayout) v.findViewById(R.id.ptr_main);

        ivImage = (ImageView) v.findViewById(R.id.iv_main);

        final StoreHouseHeader header = new StoreHouseHeader(getActivity());
        header.setPadding(0, DisplayUtil.dp2px(15), 0, DisplayUtil.dp2px(15));

        header.initWithString(HEADER_STRING_ARRAY[0]);
        ptr.setHeaderView(header);
        ptr.addPtrUIHandler(header);


        ptr.addPtrUIHandler(new PtrUIHandler() {
            @Override
            public void onUIReset(PtrFrameLayout ptrFrameLayout) {
                header.initWithString(HEADER_STRING_ARRAY[ptrTimes % HEADER_STRING_ARRAY.length]);
            }

            @Override
            public void onUIRefreshPrepare(PtrFrameLayout ptrFrameLayout) {

            }

            @Override
            public void onUIRefreshBegin(PtrFrameLayout ptrFrameLayout) {

            }

            @Override
            public void onUIRefreshComplete(PtrFrameLayout ptrFrameLayout) {

            }

            @Override
            public void onUIPositionChange(PtrFrameLayout ptrFrameLayout, boolean b, byte b2, int i, int i2, float v, float v2) {

            }
        });


        ptr.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
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
                ptr.autoRefresh(false);
            }
        }, 100);

    }


}
