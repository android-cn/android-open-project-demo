package com.grumoon.androidultrapulltorefreshdemo.ui;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.grumoon.androidultrapulltorefreshdemo.R;
import com.grumoon.androidultrapulltorefreshdemo.adapter.GradViewAdapter;
import com.grumoon.androidultrapulltorefreshdemo.util.Constants;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentGridViewFragment extends Fragment {


    private GridView gvMain;
    private BaseAdapter adapter;

    private PtrClassicFrameLayout ptr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_content_grid_view, container, false);
        initView(v);
        return v;
    }


    private void initView(View v) {

        gvMain = (GridView) v.findViewById(R.id.gv_main);
        ptr = (PtrClassicFrameLayout) v.findViewById(R.id.ptr_main);
        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                getData();
                ptr.refreshComplete();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ptr.autoRefresh();
            }
        }, 100);

    }


    private void getData() {
        adapter = new GradViewAdapter(getActivity(), Constants.SMALL_IMAGE_URLS);
        gvMain.setAdapter(adapter);
    }


}
