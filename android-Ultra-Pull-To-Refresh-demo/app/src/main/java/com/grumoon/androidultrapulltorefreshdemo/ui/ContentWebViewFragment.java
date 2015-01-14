package com.grumoon.androidultrapulltorefreshdemo.ui;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.grumoon.androidultrapulltorefreshdemo.R;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentWebViewFragment extends Fragment {


    private static final String[] URLS = new String[]{
            "https://github.com/grumoon",
            "https://github.com/android-cn/android-open-project-analysis",
            "https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh",
    };

    //下拉次数
    private int ptrTimes;

    private PtrClassicFrameLayout ptr;

    private WebView wvMain;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_content_web_view, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {

        wvMain = (WebView) v.findViewById(R.id.wv_main);
        wvMain.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);
                ptr.refreshComplete();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {

                super.onReceivedError(view, errorCode, description, failingUrl);
                ptr.refreshComplete();
                Toast.makeText(getActivity(), "页面打开失败，请稍候再试", Toast.LENGTH_SHORT)
                        .show();
            }
        });


        ptr = (PtrClassicFrameLayout) v.findViewById(R.id.ptr_main);

        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view2) {
                return wvMain.getScrollY() == 0;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {

                wvMain.loadUrl(URLS[ptrTimes % URLS.length]);
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
