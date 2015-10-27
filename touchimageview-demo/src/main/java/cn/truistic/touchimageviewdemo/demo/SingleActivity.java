package cn.truistic.touchimageviewdemo.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import cn.truistic.touchimageviewdemo.R;
import cn.truistic.touchimageviewdemo.lib.TouchImageView;

/**
 * Single TouchImageView Example
 */
public class SingleActivity extends AppCompatActivity {

    private TouchImageView timg;
    private int[] imgs = {R.drawable.img_normal_h, R.drawable.img_normal_v, R.drawable.img_small};
    private static final ScaleType[] scaleTypes = {ScaleType.CENTER, ScaleType.CENTER_CROP, ScaleType.CENTER_INSIDE, ScaleType.FIT_XY, ScaleType.FIT_CENTER};
    private int imgIndex = 0;
    private int scaleIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);
        initView();
    }

    private void initView() {
        timg = (TouchImageView) findViewById(R.id.timg_single);
        findViewById(R.id.btn_single_change_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timg.setImageResource(imgs[(++imgIndex % imgs.length)]);
            }
        });

        findViewById(R.id.btn_single_change_scaletype).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scaleIndex = ++scaleIndex % scaleTypes.length;
                timg.setScaleType(scaleTypes[scaleIndex]);
                Toast.makeText(SingleActivity.this, "ScaleType:" + scaleTypes[scaleIndex], Toast.LENGTH_SHORT).show();
            }
        });
        // Onclick Test
        timg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SingleActivity.this, "OnClick", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
