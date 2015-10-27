package cn.truistic.touchimageviewdemo.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import cn.truistic.touchimageviewdemo.R;
import cn.truistic.touchimageviewdemo.lib.TouchImageView;

/**
 * Picasoo Test
 */
public class PicassoActivity extends AppCompatActivity {

    private TouchImageView timgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso);
        timgView = (TouchImageView) findViewById(R.id.timg_picasso);
        Picasso.with(this).load(R.drawable.img_big).into(timgView);
    }
}
