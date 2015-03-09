package com.example.yyb.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by yyb on 15-3-8.
 */

public class MyInnerFragment extends Fragment {

    @InjectView(R.id.textview)
    TextView textview;

    private int id;


    public static Fragment newInstance(int id) {
        MyInnerFragment f = new MyInnerFragment();
        f.id = id;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.innerfragment, null);
        ButterKnife.inject(this, view);
        textview.setText("select pager" + id);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
