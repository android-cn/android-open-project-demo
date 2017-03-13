package com.wmmeng.quickadapterdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import com.squareup.picasso.Picasso;

public class DemoActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);
		initData();
	}

	private void initData() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		String f = getIntent().getStringExtra(MainActivity.KEY);
		if(SingleLayoutFragment.class.getSimpleName().equals(f)){
			fragmentTransaction.replace(R.id.lay_container, new SingleLayoutFragment());
        }else if(PicassoFragment.class.getSimpleName().equals(f)){
            fragmentTransaction.replace(R.id.lay_container, new PicassoFragment());
        }
		fragmentTransaction.commit();
	}
}
