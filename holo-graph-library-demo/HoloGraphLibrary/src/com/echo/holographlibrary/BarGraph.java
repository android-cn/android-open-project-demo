/*
 * 	   Created by Daniel Nadeau
 * 	   daniel.nadeau01@gmail.com
 * 	   danielnadeau.blogspot.com
 * 
 * 	   Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.echo.holographlibrary;

import android.content.Context;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

public class BarGraph extends View {

    private ArrayList<Bar> points = new ArrayList<Bar>();
    private Paint p = new Paint();
    private Path path = new Path();
    private Rect r;
    private boolean showBarText = true;
    private int indexSelected = -1;
    private OnBarClickedListener listener;
    private Bitmap fullImage;
    private boolean shouldUpdate = false;
    private String unit = "$";
    private Boolean append = false;
    private Rect r2 = new Rect();
    private Rect r3 = new Rect();

    public BarGraph(Context context) {
        super(context);
    }

    public BarGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setShowBarText(boolean show) {
        showBarText = show;
    }

    public void setBars(ArrayList<Bar> points) {
        this.points = points;
        postInvalidate();
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return this.unit;
    }

    public void appendUnit(Boolean doAppend) {
        this.append = doAppend;
    }

    public Boolean isAppended() {
        return this.append;
    }

    public ArrayList<Bar> getBars() {
        return this.points;
    }

    public void onDraw(Canvas ca) {

        if (fullImage == null || shouldUpdate) {
            fullImage = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(fullImage);
            canvas.drawColor(Color.TRANSPARENT);
            NinePatchDrawable popup = (NinePatchDrawable) this.getResources().getDrawable(R.drawable.popup_black);

            float maxValue = 0;
            float padding = 7;
            int selectPadding = 4;
            float bottomPadding = 40;

            float usableHeight;
            if (showBarText) {
                this.p.setTextSize(40);
                this.p.getTextBounds(unit, 0, 1, r3);
                usableHeight = getHeight() - bottomPadding - Math.abs(r3.top - r3.bottom) - 26;
            } else {
                usableHeight = getHeight() - bottomPadding;
            }


            p.setColor(Color.BLACK);
            p.setStrokeWidth(2);
            p.setAlpha(50);
            p.setAntiAlias(true);

            canvas.drawLine(0, getHeight() - bottomPadding + 10, getWidth(), getHeight() - bottomPadding + 10, p);

            float barWidth = (getWidth() - (padding * 2) * points.size()) / points.size();

            for (Bar p : points) {
                maxValue += p.getValue();
            }

            r = new Rect();

            path.reset();

            int count = 0;
            for (Bar p : points) {

                if(p.getStackedBar()){
                    ArrayList<BarStackSegment> values = new ArrayList<BarStackSegment>(p.getStackedValues());
                    int prevValue = 0;
                    for(BarStackSegment value : values) {
                        value.Value += prevValue;
                        prevValue += value.Value;
                    }
                    Collections.reverse(values);

                    for(BarStackSegment value : values) {
                        r.set((int) ((padding * 2) * count + padding + barWidth * count), (int) ((getHeight() - bottomPadding - (usableHeight * (value.Value / maxValue)))), (int) ((padding * 2) * count + padding + barWidth * (count + 1)), (int) ((getHeight() - bottomPadding)));
                        path.addRect(new RectF(r.left - selectPadding, r.top - selectPadding, r.right + selectPadding, r.bottom + selectPadding), Path.Direction.CW);
                        p.setPath(path);
                        p.setRegion(new Region(r.left - selectPadding, r.top - selectPadding, r.right + selectPadding, r.bottom + selectPadding));
                        this.p.setColor(value.Color);
                        this.p.setAlpha(255);
                        canvas.drawRect(r, this.p);
                    }
                }else {
                    r.set((int) ((padding * 2) * count + padding + barWidth * count), (int) (getHeight() - bottomPadding - (usableHeight * (p.getValue() / maxValue))), (int) ((padding * 2) * count + padding + barWidth * (count + 1)), (int) (getHeight() - bottomPadding));
                    path.addRect(new RectF(r.left - selectPadding, r.top - selectPadding, r.right + selectPadding, r.bottom + selectPadding), Path.Direction.CW);
                    p.setPath(path);
                    p.setRegion(new Region(r.left - selectPadding, r.top - selectPadding, r.right + selectPadding, r.bottom + selectPadding));
                    this.p.setColor(p.getColor());
                    this.p.setAlpha(255);
                    canvas.drawRect(r, this.p);
                }


                this.p.setTextSize(20);
                canvas.drawText(p.getName(), (int) (((r.left + r.right) / 2) - (this.p.measureText(p.getName()) / 2)), getHeight() - 5, this.p);
                if (showBarText) {
                    this.p.setTextSize(40);
                    this.p.setColor(Color.WHITE);
                    this.p.getTextBounds(unit + p.getValue(), 0, 1, r2);
                    if (popup != null)
                        popup.setBounds((int) (((r.left + r.right) / 2) - (this.p.measureText(unit + p.getValue()) / 2)) - 14, r.top + (r2.top - r2.bottom) - 26, (int) (((r.left + r.right) / 2) + (this.p.measureText(unit + p.getValue()) / 2)) + 14, r.top);
                    popup.draw(canvas);
                    if (isAppended())
                        canvas.drawText(p.getValue() + unit, (int) (((r.left + r.right) / 2) - (this.p.measureText(unit + p.getValue()) / 2)), r.top - 20, this.p);
                    else
                        canvas.drawText(unit + p.getValue(), (int) (((r.left + r.right) / 2) - (this.p.measureText(unit + p.getValue()) / 2)), r.top - 20, this.p);
                }
                if (indexSelected == count && listener != null) {
                    this.p.setColor(Color.parseColor("#33B5E5"));
                    this.p.setAlpha(100);
                    canvas.drawPath(p.getPath(), this.p);
                    this.p.setAlpha(255);
                }
                count++;
            }
            shouldUpdate = false;
        }

        ca.drawBitmap(fullImage, 0, 0, null);

    }

    @Override
    public boolean onTouchEvent(@NotNull MotionEvent event) {

        Point point = new Point();
        point.x = (int) event.getX();
        point.y = (int) event.getY();

        int count = 0;
        for (Bar bar : points) {
            Region r = new Region();
            r.setPath(bar.getPath(), bar.getRegion());
            if (r.contains(point.x, point.y) && event.getAction() == MotionEvent.ACTION_DOWN) {
                indexSelected = count;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (r.contains(point.x, point.y) && listener != null) {
                    listener.onClick(indexSelected);
                }
                indexSelected = -1;
            }
            count++;
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP) {
            shouldUpdate = true;
            postInvalidate();
        }


        return true;
    }

    public void setOnBarClickedListener(OnBarClickedListener listener) {
        this.listener = listener;
    }

    public interface OnBarClickedListener {
        abstract void onClick(int index);
    }
}
