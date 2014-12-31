package com.redefine.aaron.holographlibrary_demo;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieSlice;


public class PieFragment extends SherlockFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v =  inflater.inflate(R.layout.fragment_pie, container, false);

        PieGraph pieGraph  = (PieGraph) v.findViewById(R.id.piegraph);
        PieSlice slice = new PieSlice();    //设置第一个扇形
        slice.setColor(Color.parseColor("#FFBB33"));    //设置颜色
        slice.setValue(1);          //设置大小
        pieGraph.addSlice(slice);
        slice = new PieSlice();     //设置第二个扇形
        slice.setColor(Color.parseColor("#AA66CC"));
        slice.setValue(2);
        pieGraph.addSlice(slice);
        slice = new PieSlice();     //设置第三个扇形
        slice.setColor(Color.parseColor("#333333"));
        slice.setValue(2);
        pieGraph.addSlice(slice);
        slice = new PieSlice();     //设置第三个扇形
        slice.setColor(Color.parseColor("#339933"));
        slice.setValue(4);
        pieGraph.addSlice(slice);
        slice = new PieSlice();     //设置第四个扇形
        slice.setColor(Color.parseColor("#99CC00"));
        slice.setValue(9);
        pieGraph.addSlice(slice);

        pieGraph.setOnSliceClickedListener(new PieGraph.OnSliceClickedListener() {
            @Override
            public void onClick(int index) {

            }
        });

        return v;
    }



}
