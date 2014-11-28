package com.huxian.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.huxian.Constants;
import com.huxian.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import static android.support.v4.view.ViewPager.OnPageChangeListener;

public class ShowPhotoActivity extends Activity {

    public static final String INTENT_PHOTO_INDEX = "photo_index";

    private ViewPager viewPager;
    private DisplayImageOptions options;

    private int photoIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);

        initData();
        findViews();
        initViews();
    }

    private void initData() {
        Intent intent = getIntent();
        photoIndex = intent.getIntExtra(INTENT_PHOTO_INDEX, 0);
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.loading)
                .showImageOnFail(R.drawable.loading)
                .showImageOnLoading(R.drawable.loading)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    private void findViews() {
        viewPager = (ViewPager) findViewById(R.id.show_photo_vp);
    }

    private void initViews() {
        viewPager.setAdapter(new PhotoPagerAdapter());
        viewPager.setOnPageChangeListener(new PhotoOnPageChangeListener());
        viewPager.setCurrentItem(photoIndex);
    }

    class PhotoPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Constants.PHOTOS.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return (view == obj);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(ShowPhotoActivity.this);
            ImageLoader.getInstance().displayImage(Constants.PHOTOS[position], imageView, options);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class PhotoOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i2) {

        }

        @Override
        public void onPageSelected(int i) {

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

}
