package com.grumoon.androidultrapulltorefreshdemo.ui;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.grumoon.androidultrapulltorefreshdemo.R;
import com.grumoon.androidultrapulltorefreshdemo.adapter.ListViewAdapter;
import com.grumoon.androidultrapulltorefreshdemo.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentListViewFragment extends Fragment {

    private ListView lvMain;
    private BaseAdapter adapter;

    private PtrClassicFrameLayout ptr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_content_list_view, container, false);
        initView(v);
        return v;
    }


    private void initView(View v) {

        lvMain = (ListView) v.findViewById(R.id.lv_main);
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
        adapter = new ListViewAdapter(getActivity(), Arrays.asList(Constants.SMALL_IMAGE_URLS));
        lvMain.setAdapter(adapter);
    }


}
