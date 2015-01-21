package com.grumoon.androidultrapulltorefreshdemo;

import android.app.Application;

import com.grumoon.androidultrapulltorefreshdemo.util.DisplayUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by grumoon on 2015/1/14.
 */
public class PTRApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DisplayUtil.init(getApplicationContext());

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();

        ImageLoader.getInstance().init(configuration);
    }
}
