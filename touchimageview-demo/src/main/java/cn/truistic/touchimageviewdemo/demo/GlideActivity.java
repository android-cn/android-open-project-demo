package cn.truistic.touchimageviewdemo.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import cn.truistic.touchimageviewdemo.R;
import cn.truistic.touchimageviewdemo.lib.TouchImageView;

/**
 * Glide Test
 */
public class GlideActivity extends AppCompatActivity {

    private TouchImageView timgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        timgView = (TouchImageView) findViewById(R.id.timg_glide);
        Glide.with(this).load(R.drawable.img_big).asBitmap().into(timgView);
    }
}
