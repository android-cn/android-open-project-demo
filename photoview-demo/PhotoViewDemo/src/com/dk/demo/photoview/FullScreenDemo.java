package com.dk.demo.photoview;

import uk.co.senab.photoview.PhotoView;
import android.app.Activity;
import android.os.Bundle;

public class FullScreenDemo extends Activity {
	private PhotoView mPhotoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen);
		mPhotoView = (PhotoView) findViewById(R.id.photoview);
		mPhotoView.setImageResource(R.drawable.saya);
				
	}
}
