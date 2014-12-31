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
        Line l = new Line();    //创建第一个点
        LinePoint p = new LinePoint();
        p.setX(0);  //设置x坐标
        p.setY(2);  //设置y坐标
        l.addPoint(p);
        p = new LinePoint();    //创建第二个点
        p.setX(3);  //设置x坐标
        p.setY(3);  //设置y坐标
        l.addPoint(p);
        p = new LinePoint();    //创建第三个点
        p.setX(5);  //设置x坐标
        p.setY(9);  //设置y坐标
        l.addPoint(p);
        p = new LinePoint();    //创建第四个点
        p.setX(10); //设置x坐标
        p.setY(7);  //设置y坐标
        l.addPoint(p);
        l.setColor(Color.parseColor("#99CC00"));

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
