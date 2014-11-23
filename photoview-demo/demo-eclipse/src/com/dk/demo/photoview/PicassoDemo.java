package com.dk.demo.photoview;

import uk.co.senab.photoview.PhotoView;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.squareup.picasso.Picasso;

public class PicassoDemo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_integration);
		PhotoView photoView = (PhotoView) findViewById(R.id.photoview);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		Picasso.with(this).load("http://dk-exp.qiniudn.com/saya.jpg")
				.centerInside().resize(dm.widthPixels, dm.heightPixels).tag(this).into(photoView);
	}
}
