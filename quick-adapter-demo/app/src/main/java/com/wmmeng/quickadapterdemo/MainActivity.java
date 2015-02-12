package com.wmmeng.quickadapterdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	public static final String KEY = "fragment";
	private Button btn_single_layout, btn_net_image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initListener();
	}

	private void initView() {
		btn_single_layout = (Button) findViewById(R.id.btn_single_layout);
		btn_net_image = (Button) findViewById(R.id.btn_net_image);
	}

	private void initListener() {
		btn_single_layout.setOnClickListener(this);
		btn_net_image.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
        Intent intent;
		switch (view.getId()) {
		case R.id.btn_single_layout:
			intent = new Intent(this, DemoActivity.class);
			intent.putExtra(KEY, SingleLayoutFragment.class.getSimpleName());
			startActivity(intent);

			break;
		case R.id.btn_net_image:
            intent = new Intent(this, DemoActivity.class);
            intent.putExtra(KEY, PicassoFragment.class.getSimpleName());
            startActivity(intent);
			break;

		default:
			break;
		}
	}

}
