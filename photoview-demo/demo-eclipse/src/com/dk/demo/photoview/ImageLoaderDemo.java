package com.dk.demo.photoview;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import uk.co.senab.photoview.PhotoView;
import android.app.Activity;
import android.os.Bundle;

public class ImageLoaderDemo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_integration);
		PhotoView photoView = (PhotoView) findViewById(R.id.photoview);

		ImageLoader.getInstance().init(
				ImageLoaderConfiguration.createDefault(this));

		/**
		 * The Image URL is comes from my space. If it is invalid, you can
		 * change to any other picture url.
		 */
		ImageLoader.getInstance().displayImage(
				"http://dk-exp.qiniudn.com/saya.jpg", photoView);
	}
}
