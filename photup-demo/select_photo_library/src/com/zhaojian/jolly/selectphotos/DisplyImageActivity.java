package com.zhaojian.jolly.selectphotos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.zhaojian.jolly.fragment.UserPhotosFragment;
import com.zhaojian.select_photo_library.R;

public class DisplyImageActivity extends Activity {

	private static final int RESULT_PHOTOVIEW = 102;
	private UserPhotosFragment photoFragment;

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		super.onBackPressed();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_photos);

		photoFragment=new UserPhotosFragment();
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.frag_primary, photoFragment).commit();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case RESULT_PHOTOVIEW:
			photoFragment.onActivityResult(requestCode, resultCode, data);
			break;
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

}
