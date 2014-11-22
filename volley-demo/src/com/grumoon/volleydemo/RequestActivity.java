package com.grumoon.volleydemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.grumoon.volleydemo.fragment.ImageLoaderFragment;
import com.grumoon.volleydemo.fragment.ImageRequestFragment;
import com.grumoon.volleydemo.fragment.JsonArrayRequestFragment;
import com.grumoon.volleydemo.fragment.JsonObjectRequestFragment;
import com.grumoon.volleydemo.fragment.NetworkImageViewFragment;
import com.grumoon.volleydemo.fragment.PostRequestFragment;
import com.grumoon.volleydemo.fragment.SampleFragment;
import com.grumoon.volleydemo.fragment.StringRequestFragment;
import com.grumoon.volleydemo.fragment.XmlRequestFragment;
import com.grumoon.volleydemo.util.Constants;

public class RequestActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		int frIndex = getIntent().getIntExtra(Constants.Extra.FRAGMENT_INDEX, 0);
		Fragment fr;
		String tag;
		int titleRes;
		switch (frIndex) {
			default:
			case StringRequestFragment.INDEX:
				tag = StringRequestFragment.class.getSimpleName();
				fr = getSupportFragmentManager().findFragmentByTag(tag);
				if (fr == null) {
					fr = new StringRequestFragment();
				}
				titleRes = R.string.string_request;
				break;
			case JsonObjectRequestFragment.INDEX:
				tag = JsonObjectRequestFragment.class.getSimpleName();
				fr = getSupportFragmentManager().findFragmentByTag(tag);
				if (fr == null) {
					fr = new JsonObjectRequestFragment();
				}
				titleRes = R.string.json_object_request;
				break;
			case JsonArrayRequestFragment.INDEX:
				tag = JsonArrayRequestFragment.class.getSimpleName();
				fr = getSupportFragmentManager().findFragmentByTag(tag);
				if (fr == null) {
					fr = new JsonArrayRequestFragment();
				}
				titleRes = R.string.json_array_request;
				break;
			case ImageRequestFragment.INDEX:
				tag = ImageRequestFragment.class.getSimpleName();
				fr = getSupportFragmentManager().findFragmentByTag(tag);
				if (fr == null) {
					fr = new ImageRequestFragment();
					fr.setArguments(getIntent().getExtras());
				}
				titleRes = R.string.image_request;
				break;
			case ImageLoaderFragment.INDEX:
				tag = ImageLoaderFragment.class.getSimpleName();
				fr = getSupportFragmentManager().findFragmentByTag(tag);
				if (fr == null) {
					fr = new ImageLoaderFragment();
				}
				titleRes = R.string.image_loader;
				break;
			case NetworkImageViewFragment.INDEX:
				tag = NetworkImageViewFragment.class.getSimpleName();
				fr = getSupportFragmentManager().findFragmentByTag(tag);
				if (fr == null) {
					fr = new NetworkImageViewFragment();
				}
				titleRes = R.string.network_image_view;
				break;
			case XmlRequestFragment.INDEX:
				tag = XmlRequestFragment.class.getSimpleName();
				fr = getSupportFragmentManager().findFragmentByTag(tag);
				if (fr == null) {
					fr = new XmlRequestFragment();
				}
				titleRes = R.string.xml_request;
				break;
			case PostRequestFragment.INDEX:
				tag = PostRequestFragment.class.getSimpleName();
				fr = getSupportFragmentManager().findFragmentByTag(tag);
				if (fr == null) {
					fr = new PostRequestFragment();
				}
				titleRes = R.string.post_request;
				break;
			case SampleFragment.INDEX:
				tag = SampleFragment.class.getSimpleName();
				fr = getSupportFragmentManager().findFragmentByTag(tag);
				if (fr == null) {
					fr = new SampleFragment();
				}
				titleRes = R.string.sample;
				break;
		}

		setTitle(titleRes);
		getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fr, tag).commit();
	}
}
