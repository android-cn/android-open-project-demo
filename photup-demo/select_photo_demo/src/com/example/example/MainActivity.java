package com.example.example;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zhaojian.jolly.selectphotos.DisplyImageActivity;

public class MainActivity extends Activity {

	static final String PHOTO_URIS="photo_uris";
	static final int  REQUEST_CODE=1111; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		
		Button btn;
		private ArrayList<String> uri_lists=new ArrayList<String>();
		TextView uris;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			btn=(Button)rootView.findViewById(R.id.startbtn);
			uris=(TextView)rootView.findViewById(R.id.uris);
			btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent();
					intent.setClass(getActivity(), DisplyImageActivity.class);
					startActivityForResult(intent,REQUEST_CODE);
				}
			});
			return rootView;
		}
		
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			switch (resultCode) {
			case Activity.RESULT_OK:
				if(data!=null){
					uri_lists=data.getExtras().getStringArrayList(PHOTO_URIS);
					if(uri_lists.size()>0){
						uris.setText(uri_lists.toString());
					}
				}
				break;
			}

			super.onActivityResult(requestCode, resultCode, data);
		}
		
	}

}
