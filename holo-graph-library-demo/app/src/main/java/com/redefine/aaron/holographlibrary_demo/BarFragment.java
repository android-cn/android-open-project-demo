package com.redefine.aaron.holographlibrary_demo;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.echo.holographlibrary.Bar;
import com.echo.holographlibrary.BarGraph;
import com.echo.holographlibrary.BarGraph.OnBarClickedListener;
import com.echo.holographlibrary.BarStackSegment;

import java.util.ArrayList;


public class BarFragment extends SherlockFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v =  inflater.inflate(R.layout.fragment_bar, container, false);
        assert v !=null;

        ArrayList<Bar> points = new ArrayList<Bar>();
        Bar d = new Bar();
        d.setColor(Color.parseColor("##99CC00"));
        d.setName("小明");
        d.setValue(10);
        Bar d2 = new Bar();
        d2.setColor(Color.parseColor("##99CC00"));
        d2.setName("小四");
        d2.setValue(20);
        Bar d3 = new Bar();
        d3.setColor(Color.parseColor("##CCCC99"));
        d3.setName("大大");
        d3.setStackedBar(true);
        d3.AddStackValue(new BarStackSegment(2, Color.parseColor("#99CC00")));
        d3.AddStackValue(new BarStackSegment(4, Color.RED));
        points.add(d);
        points.add(d2);
        points.add(d3);

        BarGraph g = (BarGraph)v.findViewById(R.id.bargraph);
        assert g != null;
        g.setUnit("¥");
        g.appendUnit(true);
        g.setBars(points);

        g.setOnBarClickedListener(new OnBarClickedListener(){

            @Override
            public void onClick(int index) {

            }

        });

        return v;

    }


}
