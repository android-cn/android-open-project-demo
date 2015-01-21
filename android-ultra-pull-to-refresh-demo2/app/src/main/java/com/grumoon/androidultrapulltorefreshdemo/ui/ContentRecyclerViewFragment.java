package com.grumoon.androidultrapulltorefreshdemo.ui;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grumoon.androidultrapulltorefreshdemo.R;
import com.grumoon.androidultrapulltorefreshdemo.adapter.RecyclerViewAdapter;
import com.grumoon.androidultrapulltorefreshdemo.util.Constants;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentRecyclerViewFragment extends Fragment {

    private RecyclerView rvMain;

    private PtrClassicFrameLayout ptr;

    private RecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_content_recycler_view, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {

        ptr = (PtrClassicFrameLayout) v.findViewById(R.id.ptr_main);

        rvMain = (RecyclerView) v.findViewById(R.id.rv_main);


        rvMain.setLayoutManager(new LinearLayoutManager(getActivity()));

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
        adapter = new RecyclerViewAdapter(getActivity(), Constants.BIG_IMAGE_URLS);
        rvMain.setAdapter(adapter);
    }


}
