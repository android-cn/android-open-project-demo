package cn.truistic.touchimageviewdemo.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cn.truistic.touchimageviewdemo.R;
import cn.truistic.touchimageviewdemo.lib.TouchImageView;

/**
 * UIL ImageLoader Test
 */
public class ImageLoaderActivity extends AppCompatActivity {

    private TouchImageView timgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageloader);
        timgView = (TouchImageView) findViewById(R.id.timg_imageloader);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage("drawable://" + R.drawable.img_big, timgView);
    }
}
