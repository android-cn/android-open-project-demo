package com.grumoon.androidultrapulltorefreshdemo.ui;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.grumoon.androidultrapulltorefreshdemo.R;
import com.grumoon.androidultrapulltorefreshdemo.adapter.ListViewAdapter;
import com.grumoon.androidultrapulltorefreshdemo.header.WindmillHeader;
import com.grumoon.androidultrapulltorefreshdemo.util.Constants;
import com.grumoon.androidultrapulltorefreshdemo.view.GetMoreListView;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class GetMoreListViewFragment extends Fragment {


    private static final int PAGE_NUM = 10;

    private GetMoreListView gmlvMain;
    private BaseAdapter adapter;

    private PtrFrameLayout ptr;

    private int currentPage = 0;

    private Handler handler;

    private List<String> imageUrlList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        imageUrlList = new ArrayList<String>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_get_more_list_view, container, false);
        initView(v);
        return v;
    }


    private void initView(View v) {

        gmlvMain = (GetMoreListView) v.findViewById(R.id.gmlv_main);

        gmlvMain.setOnGetMoreListener(new GetMoreListView.OnGetMoreListener() {
            @Override
            public void onGetMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        getData(false);

                    }
                }, 2000);

            }
        });

        adapter = new ListViewAdapter(getActivity(), imageUrlList);
        gmlvMain.setAdapter(adapter);

        ptr = (PtrFrameLayout) v.findViewById(R.id.ptr_main);

        final WindmillHeader header = new WindmillHeader(getActivity());


        ptr.setHeaderView(header);
        ptr.addPtrUIHandler(header);


        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gmlvMain.setHasMore();
                        getData(true);

                    }
                }, 2000);

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ptr.autoRefresh();
            }
        }, 100);

    }


    private void getData(boolean isRefresh) {

        if (isRefresh) {
            currentPage = 0;
            imageUrlList.clear();
        }

        List<String> newImageUrlList = new ArrayList<String>();

        int start = currentPage * PAGE_NUM;
        int to = Math.min((currentPage + 1) * PAGE_NUM, Constants.SMALL_IMAGE_URLS.length);

        for (int i = start; i < to; i++) {
            newImageUrlList.add(Constants.SMALL_IMAGE_URLS[i]);
        }

        if (newImageUrlList.size() < 10) {
            gmlvMain.setNoMore();
        }

        currentPage++;

        imageUrlList.addAll(newImageUrlList);

        adapter.notifyDataSetChanged();

        gmlvMain.getMoreComplete();
        ptr.refreshComplete();
    }

}
