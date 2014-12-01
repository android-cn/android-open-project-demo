package com.example.listviewanimationdemo;

import com.example.listviewanimationdemo.itemanimation.ItemAnimationMainActivity;
import com.example.listviewanimationdemo.itemmanipulation.AnimateAdditionActivity;
import com.example.listviewanimationdemo.itemmanipulation.AnimateDismissActivity;
import com.example.listviewanimationdemo.itemmanipulation.DragAndDropActivity;
import com.example.listviewanimationdemo.itemmanipulation.ExpandableListItemActivity;
import com.example.listviewanimationdemo.itemmanipulation.SwipeToDismissActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class HomeActivity extends FragmentActivity implements OnClickListener {
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setTitle("ListAnimation动画示例");
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
		findViewById(R.id.home_item_add_btn).setOnClickListener(this);
		findViewById(R.id.home_item_go_github).setOnClickListener(this);
		findViewById(R.id.home_item_go_la_github).setOnClickListener(this);
		findViewById(R.id.home_item_go_demo_author_blog).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.home_item_animation_btn:
			ItemAnimationMainActivity.actionToItemAnimationMain(this);
			break;
		case R.id.home_swipe_to_dismiss_btn:
			SwipeToDismissActivity.actionToSwipeDismiss(this);
			break;
		case R.id.home_item_drag_btn:
			DragAndDropActivity.actionToDragAndDrop(this);
			break;
		case R.id.home_multiple_item_dismiss_btn:
			AnimateDismissActivity.actionToAnimateDismiss(this);
			break;
		case R.id.home_item_expend_btn:
			ExpandableListItemActivity.actionToExpandableList(this);
			break;
		case R.id.home_item_add_btn:
			AnimateAdditionActivity.actionToItemAddition(this);
			break;

		case R.id.home_item_go_github:
			Toast.makeText(this, "正在转向demo项目主页...", Toast.LENGTH_SHORT).show();
			openBrower("https://github.com/android-cn/android-open-project-demo");
			break;
		case R.id.home_item_go_la_github:
			Toast.makeText(this, "正在转向ListviewAnimation项目主页...",
					Toast.LENGTH_SHORT).show();
			openBrower("https://github.com/nhaarman/ListViewAnimations");
			break;
		case R.id.home_item_go_demo_author_blog:
			Toast.makeText(this, "正在转向Demo作者博客主页...",
					Toast.LENGTH_SHORT).show();
			openBrower("http://waylife.github.io");
			break;
		default:
			break;
		}
	}

	public void openBrower(String url) {
		Uri uri = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		intent.setPackage("com.android.browser");
		startActivity(intent);
	}
}
