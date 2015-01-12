package com.grumoon.androidultrapulltorefreshdemo.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grumoon.androidultrapulltorefreshdemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentTextViewFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_content_text_view, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {

    }


}
