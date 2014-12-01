package com.example.example;

import android.app.Application;

import com.zhaojian.jolly.selectphotos.SelectPhotoApplication;

public class ExampleApplication extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		SelectPhotoApplication.init(this);
	}

}
