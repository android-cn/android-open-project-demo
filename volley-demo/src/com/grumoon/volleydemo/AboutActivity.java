package com.grumoon.volleydemo;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class AboutActivity extends Activity {

	private TextView tvAbout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_about);
		setTitle(R.string.action_about);
		tvAbout = (TextView) findViewById(R.id.tv_about);
		tvAbout.setMovementMethod(LinkMovementMethod.getInstance());
		tvAbout.setText(Html.fromHtml((getResources().getString((R.string.about_text)))));
	}
}
