package com.androidcn.circleimageview_demo;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author P. Seul
 * This is a demo of CircleImageView. Explanations can be found in R.layout.activity_main
 * You can set every attributes that ImageView has except ScaleType for CircleImageView, otherwise an IllegalArgumentException would be thrown out.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}