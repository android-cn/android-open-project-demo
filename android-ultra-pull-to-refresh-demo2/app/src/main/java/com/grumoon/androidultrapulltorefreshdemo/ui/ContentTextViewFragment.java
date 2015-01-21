package com.grumoon.androidultrapulltorefreshdemo.ui;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grumoon.androidultrapulltorefreshdemo.R;

import org.w3c.dom.Text;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentTextViewFragment extends Fragment {

    private PtrClassicFrameLayout ptr;

    private TextView tvAbout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_content_text_view, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {

        tvAbout=(TextView)v.findViewById(R.id.tv_about);
        tvAbout.setMovementMethod(LinkMovementMethod.getInstance());
        tvAbout.setText(Html.fromHtml((getResources().getString((R.string.app_about)))));

        ptr = (PtrClassicFrameLayout) v.findViewById(R.id.ptr_main);

        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptr.refreshComplete();
                    }
                }, 2000);
            }
        });
    }


}
