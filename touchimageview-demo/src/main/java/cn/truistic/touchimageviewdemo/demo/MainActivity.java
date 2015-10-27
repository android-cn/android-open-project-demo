package cn.truistic.touchimageviewdemo.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.truistic.touchimageviewdemo.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_main_single).setOnClickListener(this);
        findViewById(R.id.btn_main_viewpager).setOnClickListener(this);
        findViewById(R.id.btn_main_imageloader).setOnClickListener(this);
        findViewById(R.id.btn_main_picasso).setOnClickListener(this);
        findViewById(R.id.btn_main_glide).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_single:
                // Single TouchImageView
                Intent singleIntent = new Intent(MainActivity.this, SingleActivity.class);
                startActivity(singleIntent);
                break;
            case R.id.btn_main_viewpager:
                // ViewPager Example
                Intent pagerIntent = new Intent(MainActivity.this, ViewPagerActivity.class);
                startActivity(pagerIntent);
                break;
            case R.id.btn_main_imageloader:
                // UIL Test
                Intent loaderIntent = new Intent(MainActivity.this, ImageLoaderActivity.class);
                startActivity(loaderIntent);
                break;
            case R.id.btn_main_picasso:
                // Picasoo Test
                Intent picassoIntent = new Intent(MainActivity.this, ImageLoaderActivity.class);
                startActivity(picassoIntent);
                break;
            case R.id.btn_main_glide:
                // Glide Test
                Intent glideIntent = new Intent(MainActivity.this, GlideActivity.class);
                startActivity(glideIntent);
                break;
        }
    }
}
