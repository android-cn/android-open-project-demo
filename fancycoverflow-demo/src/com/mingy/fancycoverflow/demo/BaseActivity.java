package com.mingy.fancycoverflow.demo;

import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		init( );
	}
	
	public void init( ){
		findViews( );
		getData( );
		showContent( );
	}
	
	public abstract void findViews( );
	public abstract void getData( );
	public abstract void showContent( );
}
