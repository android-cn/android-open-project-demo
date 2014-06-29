package com.example.listviewanimationdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

public class HomeActivity extends FragmentActivity implements OnClickListener {
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_home);
		initView();
	}

	void initView() {
		findViewById(R.id.home_item_animation_btn).setOnClickListener(this);
		findViewById(R.id.home_swipe_to_dismiss_btn).setOnClickListener(this);
		findViewById(R.id.home_item_drag_btn).setOnClickListener(this);
		findViewById(R.id.home_multiple_item_dismiss_btn).setOnClickListener(
				this);
		findViewById(R.id.home_item_expend_btn).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.home_item_animation_btn:

			break;
		case R.id.home_swipe_to_dismiss_btn:
			SwipeToDismissActivity.actionToSwipeDismiss(this);
			break;
		case R.id.home_item_drag_btn:
			break;
		case R.id.home_multiple_item_dismiss_btn:
			break;
		case R.id.home_item_expend_btn:

			break;

		default:
			break;
		}
	}
}
