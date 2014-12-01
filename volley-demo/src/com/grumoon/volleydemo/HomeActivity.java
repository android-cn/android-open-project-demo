package com.grumoon.volleydemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.grumoon.volleydemo.fragment.ImageLoaderFragment;
import com.grumoon.volleydemo.fragment.ImageRequestFragment;
import com.grumoon.volleydemo.fragment.JsonRequestFragment;
import com.grumoon.volleydemo.fragment.NetworkImageViewFragment;
import com.grumoon.volleydemo.fragment.PostRequestFragment;
import com.grumoon.volleydemo.fragment.StringRequestFragment;
import com.grumoon.volleydemo.fragment.XmlRequestFragment;
import com.grumoon.volleydemo.util.Constants;

public class HomeActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_main);
		initView();

	}

	private void initView() {
		// String请求
		findViewById(R.id.btn_string_request).setOnClickListener(this);

		// Json请求
		findViewById(R.id.btn_json_request).setOnClickListener(this);
		
		// Image请求
		findViewById(R.id.btn_image_request).setOnClickListener(this);

		// ImageLoader
		findViewById(R.id.btn_image_loader).setOnClickListener(this);

		// NetworkImageView
		findViewById(R.id.btn_network_image_view).setOnClickListener(this);

		// Xml请求
		findViewById(R.id.btn_xml_request).setOnClickListener(this);

		// post请求
		findViewById(R.id.btn_post_request).setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_about:
			startActivity(new Intent(HomeActivity.this, AboutActivity.class));
			return true;

		default:
			return false;
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(HomeActivity.this, RequestActivity.class);
		switch (v.getId()) {
		case R.id.btn_string_request:
			intent.putExtra(Constants.Extra.FRAGMENT_INDEX, StringRequestFragment.INDEX);
			break;
		case R.id.btn_json_request:
			intent.putExtra(Constants.Extra.FRAGMENT_INDEX, JsonRequestFragment.INDEX);
			break;
		case R.id.btn_image_request:
			intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImageRequestFragment.INDEX);
			break;
		case R.id.btn_image_loader:
			intent.putExtra(Constants.Extra.FRAGMENT_INDEX, ImageLoaderFragment.INDEX);
			break;
		case R.id.btn_network_image_view:
			intent.putExtra(Constants.Extra.FRAGMENT_INDEX, NetworkImageViewFragment.INDEX);
			break;
		case R.id.btn_xml_request:
			intent.putExtra(Constants.Extra.FRAGMENT_INDEX, XmlRequestFragment.INDEX);
			break;
		case R.id.btn_post_request:
			intent.putExtra(Constants.Extra.FRAGMENT_INDEX, PostRequestFragment.INDEX);
			break;
		default:
			break;
		}
		
		startActivity(intent);

	}

}
