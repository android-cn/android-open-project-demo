package com.example.listviewanimationdemo.itemanimation;

import com.example.listviewanimationdemo.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

public class ItemAnimationMainActivity extends FragmentActivity implements
		OnClickListener {

	public static void actionToItemAnimationMain(Context context) {
		Intent intent = new Intent(context, ItemAnimationMainActivity.class);
		context.startActivity(intent);
	}

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setTitle("item动画主页");
		setContentView(R.layout.activity_item_animation_main);
		findViewById(R.id.item_animation_main_1).setOnClickListener(this);
		findViewById(R.id.item_animation_main_google_card).setOnClickListener(
				this);
		findViewById(R.id.item_animation_main_gridview)
				.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.item_animation_main_1:
			ItemAnimationActivity.actionToItemAnimation(this);
			break;
		case R.id.item_animation_main_google_card:
			GoogleCardsActivity.actionToGoogleCard(this);
			break;
		case R.id.item_animation_main_gridview:
			GridViewActivity.actionToGridView(this);
			break;
		default:
			break;
		}
	}
}
