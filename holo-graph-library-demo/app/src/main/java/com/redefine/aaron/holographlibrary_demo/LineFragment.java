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
import com.echo.holographlibrary.Line;
import com.echo.holographlibrary.LineGraph;
import com.echo.holographlibrary.LinePoint;


public class LineFragment extends SherlockFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v =  inflater.inflate(R.layout.fragment_line, container, false);
        Line l = new Line();
        LinePoint p = new LinePoint();
        p.setX(0);
        p.setY(2);
        l.addPoint(p);
        p = new LinePoint();
        p.setX(3);
        p.setY(3);
        l.addPoint(p);
        p = new LinePoint();
        p.setX(5);
        p.setY(9);
        l.addPoint(p);
        p = new LinePoint();
        p.setX(10);
        p.setY(7);
        l.addPoint(p);
        l.setColor(Color.parseColor("##CCCC99"));

        LineGraph li = (LineGraph) v.findViewById(R.id.linegraph);
        li.addLine(l);
        li.setRangeY(0, 15);
        li.setLineToFill(10);

        li.setOnPointClickedListener(new LineGraph.OnPointClickedListener() {
            @Override
            public void onClick(int lineIndex, int pointIndex) {

            }
        });
        return v;
    }


}
