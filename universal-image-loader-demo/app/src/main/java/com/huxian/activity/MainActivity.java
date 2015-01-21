package com.huxian.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.huxian.R;
import com.huxian.adapter.PhotoAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

/**
 * Created by huxian on 14-11-20.
 */
public class MainActivity extends Activity {

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        gridView = (GridView) findViewById(R.id.grid_photo);
        PhotoAdapter adapter = new PhotoAdapter(this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(photoClickListener);
        gridView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), false, true));
    }

    private AdapterView.OnItemClickListener photoClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(MainActivity.this, ShowPhotoActivity.class);
            intent.putExtra(ShowPhotoActivity.INTENT_PHOTO_INDEX, i);
            startActivity(intent);
        }
    };
}
