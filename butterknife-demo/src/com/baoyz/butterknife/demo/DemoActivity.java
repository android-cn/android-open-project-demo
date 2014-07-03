package com.baoyz.butterknife.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 
 * @author baoyz
 *
 */
public class DemoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);
		
		ButterKnife.inject(this);
	}

	@OnClick(R.id.simple)
	public void simple(View v) {
		startActivity(new Intent(this, SimpleActivity.class));
	}

}
