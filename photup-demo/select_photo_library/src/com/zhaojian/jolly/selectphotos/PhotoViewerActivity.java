/*
 * Copyright 2013 Chris Banes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhaojian.jolly.selectphotos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.zhaojian.jolly.adapter.SelectedPhotosViewPagerAdapter;
import com.zhaojian.jolly.adapter.UserPhotosViewPagerAdapter;
import com.zhaojian.jolly.controller.PhotoUploadController;
import com.zhaojian.jolly.model.PhotoUpload;
import com.zhaojian.jolly.utils.CursorPagerAdapter;
import com.zhaojian.jolly.utils.MediaStoreCursorHelper;
import com.zhaojian.jolly.utils.PhotupCursorLoader;
import com.zhaojian.jolly.views.CheckableImageView;
import com.zhaojian.jolly.views.PhotoTagItemLayout;
import com.zhaojian.select_photo_library.R;

public class PhotoViewerActivity extends Activity implements
		OnCheckedChangeListener, OnPageChangeListener,
		LoaderManager.LoaderCallbacks<Cursor> {

	public static final String EXTRA_POSITION = "extra_position";
	public static final String EXTRA_MODE = "extra_mode";
	public static final String EXTRA_BUCKET_ID = "extra_bucket_id";

	static final int RESULT_PHOTOVIEW = 102;

	public static int MODE_ALL_VALUE = 100;
	public static int MODE_SELECTED_VALUE = 101;

	static final int REQUEST_CROP_PHOTO = 200;

	class PhotoRemoveAnimListener implements AnimationListener {

		private final View mView;

		public PhotoRemoveAnimListener(View view) {
			mView = view;
		}

		public void onAnimationEnd(Animation animation) {
			mView.setVisibility(View.GONE);
			animation.setAnimationListener(null);

			if (!mController.hasSelections()) {
				finish();
			} else {
				View view = (View) mView.getParent();
				view.post(new Runnable() {
					public void run() {
						mAdapter.notifyDataSetChanged();
					}
				});
			}
		}

		public void onAnimationRepeat(Animation animation) {
		}

		public void onAnimationStart(Animation animation) {
		}

	}

	private ViewPager mViewPager;
	private PagerAdapter mAdapter;
	private ViewGroup mContentView;
	private CheckableImageView checkImageView;
	private LinearLayout back_btn;
	private Button finish_btn;

	private PhotoUploadController mController;

	private int mMode = MODE_SELECTED_VALUE;
	private String mBucketId;
	private int mRequestedPosition = -1;

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	private void rotateCurrentPhoto() {
		PhotoTagItemLayout currentView = getCurrentView();
		PhotoUpload upload = currentView.getPhotoSelection();
		upload.rotateClockwise();
		reloadView(currentView);
	}

	private void resetCurrentPhoto() {
		PhotoTagItemLayout currentView = getCurrentView();
		PhotoUpload upload = currentView.getPhotoSelection();

		upload.reset();
		reloadView(currentView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getSupportMenuInflater().inflate(R.menu.menu_photo_viewer, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void onPageScrolled(int position, float arg1, int arg2) {
		// NO-OP
		// reloadView(getCurrentView());
		// Log.d("zj","scroll");

		if (position == 0) {
			reloadView(getCurrentView());
		}
	}

	public void onPageScrollStateChanged(int state) {
		reloadView(getCurrentView());
	}

	public void onPageSelected(int position) {
		PhotoTagItemLayout currentView = getCurrentView();

		if (null != currentView) {
			PhotoUpload upload = currentView.getPhotoSelection();

			if (null != upload) {
				reloadView(getCurrentView());
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CROP_PHOTO:
			if (resultCode == RESULT_OK) {
				reloadView(getCurrentView());
			}
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_photo_viewer);
		mContentView = (ViewGroup) findViewById(R.id.fl_root);

		mController = PhotoUploadController.getFromContext(this);

		final Intent intent = getIntent();
		mMode = intent.getIntExtra(EXTRA_MODE, MODE_ALL_VALUE);

		if (mMode == MODE_ALL_VALUE) {
			mBucketId = intent.getStringExtra(EXTRA_BUCKET_ID);
		}

		back_btn = (LinearLayout) findViewById(R.id.back_btn);
		finish_btn = (Button) findViewById(R.id.finish_btn);
		checkImageView = (CheckableImageView) findViewById(R.id.iv_large_selection_btn);
		mViewPager = (ViewPager) findViewById(R.id.vp_photos);
		mViewPager.setOffscreenPageLimit(2);
		mViewPager.setPageMargin(getResources().getDimensionPixelSize(
				R.dimen.viewpager_margin));
		mViewPager.setOnPageChangeListener(this);
		checkImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				checkImageView.toggle();
				// Update the controller
				updateController();
				finish_btn.setText("完成(" + mController.getSelectedCount() + ")");
			}
		});
		back_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		finish_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				setResult(RESULT_PHOTOVIEW);
				finish();
			}
		});

		finish_btn.setText("完成(" + mController.getSelectedCount() + ")");

		if (mMode == MODE_ALL_VALUE) {
			mAdapter = new UserPhotosViewPagerAdapter(this);
			getLoaderManager().initLoader(0, null, this);
		} else {
			mAdapter = new SelectedPhotosViewPagerAdapter(this);
		}
		mViewPager.setAdapter(mAdapter);

		if (intent.hasExtra(EXTRA_POSITION)) {
			mRequestedPosition = intent.getIntExtra(EXTRA_POSITION, 0);
			mViewPager.setCurrentItem(mRequestedPosition);
		}

		/**
		 * Nasty hack, basically we need to know when the ViewPager is laid out,
		 * we then manually call onPageSelected. This is to fix onPageSelected
		 * not being called on the first item.
		 */
		mViewPager.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					@SuppressWarnings("deprecation")
					public void onGlobalLayout() {
						mViewPager.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
						onPageSelected(mViewPager.getCurrentItem());
					}
				});
	}

	@Override
	protected void onDestroy() {
		mController.updateDatabase();
		super.onDestroy();
	}

	private PhotoUpload getCurrentUpload() {
		PhotoTagItemLayout view = getCurrentView();
		if (null != view) {
			return view.getPhotoSelection();
		}
		return null;
	}

	private PhotoTagItemLayout getCurrentView() {
		final int currentPos = mViewPager.getCurrentItem();

		for (int i = 0, z = mViewPager.getChildCount(); i < z; i++) {
			PhotoTagItemLayout child = (PhotoTagItemLayout) mViewPager
					.getChildAt(i);
			if (null != child && child.getPosition() == currentPos) {
				return child;
			}
		}

		return null;
	}

	private void reloadView(PhotoTagItemLayout currentView) {
		if (null != currentView) {
			// MultiTouchImageView imageView = currentView.getImageView();
			PhotoUpload selection = currentView.getPhotoSelection();
			// imageView.requestFullSize(selection, true, false, null);
			checkImageView.setChecked(mController.isSelected(selection));
		}
	}

	public void onPhotoLoadStatusChanged(boolean finished) {
		// TODO Fix this setProgressBarIndeterminateVisibility(!finished);
	}

	public Loader<Cursor> onCreateLoader(int id, Bundle params) {
		String selection = null;
		String[] selectionArgs = null;
		if (null != mBucketId) {
			selection = Images.Media.BUCKET_ID + " = ?";
			selectionArgs = new String[] { mBucketId };
		}

		return new PhotupCursorLoader(this,
				MediaStoreCursorHelper.MEDIA_STORE_CONTENT_URI,
				MediaStoreCursorHelper.PHOTOS_PROJECTION, selection,
				selectionArgs, MediaStoreCursorHelper.PHOTOS_ORDER_BY, false);
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if (mAdapter instanceof CursorPagerAdapter) {
			((CursorPagerAdapter) mAdapter).swapCursor(cursor);
		}

		if (mRequestedPosition != -1) {
			mViewPager.setCurrentItem(mRequestedPosition, false);
			mRequestedPosition = -1;
		}
	}

	public void onLoaderReset(Loader<Cursor> loader) {
		onLoadFinished(loader, null);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		PhotoTagItemLayout currentView = getCurrentView();
		PhotoUpload upload = currentView.getPhotoSelection();
		updateController();
		reloadView(currentView);
	}

	void updateController() {
		final PhotoUpload currentUpload = getCurrentUpload();
		if (null != currentUpload) {
			if (checkImageView.isChecked()) {
				mController.addSelection(currentUpload);
			} else {
				mController.removeSelection(currentUpload);
			}
		}
	}

}
