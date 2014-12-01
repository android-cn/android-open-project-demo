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
package com.zhaojian.jolly.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.commonsware.cwac.merge.MergeAdapter;
import com.zhaojian.jolly.adapter.CameraBaseAdapter;
import com.zhaojian.jolly.adapter.UsersPhotosCursorAdapter;
import com.zhaojian.jolly.constant.PreferenceConstants;
import com.zhaojian.jolly.controller.PhotoUploadController;
import com.zhaojian.jolly.listener.OnNumChangeListener;
import com.zhaojian.jolly.model.MediaStoreBucket;
import com.zhaojian.jolly.model.PhotoUpload;
import com.zhaojian.jolly.selectphotos.PhotoViewerActivity;
import com.zhaojian.jolly.tasks.MediaStoreBucketsAsyncTask;
import com.zhaojian.jolly.tasks.MediaStoreBucketsAsyncTask.MediaStoreBucketsResultListener;
import com.zhaojian.jolly.utils.MediaStoreCursorHelper;
import com.zhaojian.jolly.utils.PhotupCursorLoader;
import com.zhaojian.jolly.utils.Utils;
import com.zhaojian.select_photo_library.R;

public class UserPhotosFragment extends Fragment implements
		OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor>,
		MediaStoreBucketsResultListener, OnItemSelectedListener,
		OnScanCompletedListener, OnNumChangeListener {

	static final int RESULT_CAMERA = 101;
	static final int RESULT_PHOTOVIEW = 102;
	
	static final String PHOTO_URIS="photo_uris";

	static final String SAVE_PHOTO_URI = "camera_photo_uri";

	static final String LOADER_PHOTOS_BUCKETS_PARAM = "bucket_id";
	static final int LOADER_USER_PHOTOS_EXTERNAL = 0x01;

	private MergeAdapter mAdapter;
	private UsersPhotosCursorAdapter mPhotoAdapter;
	private GridView mPhotoGrid;
	private LinearLayout back_btn;
	private Button finish_btn;

	private ArrayAdapter<MediaStoreBucket> mBucketAdapter;
	private Spinner mBucketSpinner;
	private final ArrayList<MediaStoreBucket> mBuckets = new ArrayList<MediaStoreBucket>();

	private PhotoUploadController mPhotoSelectionController;
	private File mPhotoFile;
	private SharedPreferences mPrefs;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (null != savedInstanceState) {
			if (savedInstanceState.containsKey(SAVE_PHOTO_URI)) {
				mPhotoFile = new File(
						savedInstanceState.getString(SAVE_PHOTO_URI));
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case RESULT_CAMERA:
			if (null != mPhotoFile) {
				if (resultCode == Activity.RESULT_OK) {
					Utils.scanMediaJpegFile(getActivity(), mPhotoFile, this);
				} else {
					mPhotoFile.delete();
				}
				mPhotoFile = null;
			}
			return;
		}
		switch(resultCode){

		case RESULT_PHOTOVIEW:
			excuteFinish();
			break;
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onAttach(Activity activity) {
		mPhotoSelectionController = PhotoUploadController
				.getFromContext(activity);
		mPhotoSelectionController.addMyEventListener(this);
		super.onAttach(activity);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mPhotoAdapter != null) {
			mPhotoAdapter.notifyDataSetChanged();
		}
	}

	public void onBucketsLoaded(final List<MediaStoreBucket> buckets) {
		if (null != buckets && !buckets.isEmpty()) {
			mBuckets.clear();
			mBuckets.addAll(buckets);
			mBucketAdapter.notifyDataSetChanged();
			// setSelectedBucketFromPrefs();
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mAdapter = new MergeAdapter();

		if (Utils.hasCamera(getActivity())) {
			mAdapter.addAdapter(new CameraBaseAdapter(getActivity()));
		}
		mPhotoAdapter = new UsersPhotosCursorAdapter(getActivity(), null);
		mAdapter.addAdapter(mPhotoAdapter);

		mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

		mBucketAdapter = new ArrayAdapter<MediaStoreBucket>(getActivity(),
				Utils.getSpinnerItemResId(), mBuckets);
		mBucketAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

	}

	public Loader<Cursor> onCreateLoader(final int id, Bundle bundle) {

		CursorLoader cursorLoader = null;

		switch (id) {
		case LOADER_USER_PHOTOS_EXTERNAL:
			String selection = null;
			String[] selectionArgs = null;

			if (null != bundle
					&& bundle.containsKey(LOADER_PHOTOS_BUCKETS_PARAM)) {
				selection = Images.Media.BUCKET_ID + " = ?";
				selectionArgs = new String[] { bundle
						.getString(LOADER_PHOTOS_BUCKETS_PARAM) };
			}

			cursorLoader = new PhotupCursorLoader(getActivity(),
					MediaStoreCursorHelper.MEDIA_STORE_CONTENT_URI,
					MediaStoreCursorHelper.PHOTOS_PROJECTION, selection,
					selectionArgs, MediaStoreCursorHelper.PHOTOS_ORDER_BY,
					false);
			break;
		}

		return cursorLoader;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_photos, null);

		mPhotoGrid = (GridView) view.findViewById(R.id.gv_photos);
		mPhotoGrid.setAdapter(mAdapter);
		mPhotoGrid.setOnItemClickListener(this);

		mBucketSpinner = (Spinner) view.findViewById(R.id.sp_buckets);
		mBucketSpinner.setOnItemSelectedListener(this);
		mBucketSpinner.setAdapter(mBucketAdapter);

		back_btn = (LinearLayout) view.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				getActivity().finish();
			}
		});

		finish_btn = (Button) view.findViewById(R.id.finish_btn);
		finish_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				excuteFinish();
			}
		});

		OnChangeNum();
		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		saveSelectedBucketToPrefs();
	}

	public void onItemClick(AdapterView<?> gridView, View view, int position,
			long id) {
		if (view.getId() == R.id.iv_camera_button) {
			takePhoto();
		} else {
			Bundle b = null;
			if (VERSION.SDK_INT >= 16) {
				ActivityOptionsCompat options = ActivityOptionsCompat
						.makeThumbnailScaleUpAnimation(view,
								Utils.drawViewOntoBitmap(view), 0, 0);
				b = options.toBundle();
			}

			Intent intent = new Intent(getActivity(), PhotoViewerActivity.class);

			// Need take Camera icon into account so minus 1
			intent.putExtra(PhotoViewerActivity.EXTRA_POSITION, position - 1);
			intent.putExtra(PhotoViewerActivity.EXTRA_MODE,
					PhotoViewerActivity.MODE_ALL_VALUE);

			MediaStoreBucket bucket = (MediaStoreBucket) mBucketSpinner
					.getSelectedItem();
			intent.putExtra(PhotoViewerActivity.EXTRA_BUCKET_ID, bucket.getId());

			ActivityCompat.startActivityForResult(getActivity(), intent,
					RESULT_PHOTOVIEW, b);
		}

	}

	public void onItemSelected(AdapterView<?> adapterView, View view,
			int position, long id) {
		MediaStoreBucket item = (MediaStoreBucket) adapterView
				.getItemAtPosition(position);
		if (null != item) {
			loadBucketId(item.getId());
		}
	}

	public void onLoaderReset(Loader<Cursor> loader) {

	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		switch (loader.getId()) {
		case LOADER_USER_PHOTOS_EXTERNAL:
			mPhotoAdapter.swapCursor(data);
			mPhotoGrid.setSelection(0);
			break;
		}
	}

	public void onNothingSelected(AdapterView<?> view) {
		// NO-OP
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (null != mPhotoFile) {
			outState.putString(SAVE_PHOTO_URI, mPhotoFile.getAbsolutePath());
		}
		super.onSaveInstanceState(outState);
	}

	public void onScanCompleted(String path, Uri uri) {
		getActivity().runOnUiThread(new Runnable() {
			public void run() {
				MediaStoreBucket bucket = getSelectedBucket();
				if (null != bucket) {
					loadBucketId(bucket.getId());
				}
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();

		// Load buckets
		MediaStoreBucketsAsyncTask.execute(getActivity(), this);
	}

	public void selectAll() {
		Cursor cursor = mPhotoAdapter.getCursor();
		if (null != cursor) {
			ArrayList<PhotoUpload> selections = MediaStoreCursorHelper
					.photosCursorToSelectionList(
							MediaStoreCursorHelper.MEDIA_STORE_CONTENT_URI,
							cursor);
			mPhotoSelectionController.addSelections(selections);
		}
	}

	private MediaStoreBucket getSelectedBucket() {
		if (null != mBucketSpinner) {
			return (MediaStoreBucket) mBucketSpinner.getSelectedItem();
		}
		return null;
	}

	private void loadBucketId(String id) {
		if (isAdded()) {
			Bundle bundle = new Bundle();
			if (null != id) {
				bundle.putString(LOADER_PHOTOS_BUCKETS_PARAM, id);
			}
			try {
				getLoaderManager().restartLoader(LOADER_USER_PHOTOS_EXTERNAL,
						bundle, this);
			} catch (IllegalStateException e) {
				e.printStackTrace();
				// Can sometimes catch with: Fragment not attached to Activity.
				// Not much we can do to recover
			}
		}
	}

	private void saveSelectedBucketToPrefs() {
		MediaStoreBucket bucket = getSelectedBucket();
		if (null != bucket && null != mPrefs) {
			mPrefs.edit()
					.putString(
							PreferenceConstants.PREF_SELECTED_MEDIA_BUCKET_ID,
							bucket.getId()).commit();
		}
	}

	private void setSelectedBucketFromPrefs() {
		if (null != mBucketSpinner) {
			int newSelection = 0;

			if (null != mPrefs) {
				final String lastBucketId = mPrefs
						.getString(
								PreferenceConstants.PREF_SELECTED_MEDIA_BUCKET_ID,
								null);
				if (null != lastBucketId) {
					for (int i = 0, z = mBuckets.size(); i < z; i++) {
						if (lastBucketId.equals(mBuckets.get(i).getId())) {
							newSelection = i;
							break;
						}
					}
				}
			}

			// If we have a new position, then just set it. If it's our current
			// position, then call onItemSelected manually
			if (newSelection != mBucketSpinner.getSelectedItemPosition()) {
				mBucketSpinner.setSelection(newSelection);
			} else {
				onItemSelected(mBucketSpinner, null, newSelection, 0);
			}
		}
	}

	private void takePhoto() {
		if (null == mPhotoFile) {
			Intent takePictureIntent = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
			mPhotoFile = Utils.getCameraPhotoFile();
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(mPhotoFile));
			startActivityForResult(takePictureIntent, RESULT_CAMERA);
		}
	}

	@Override
	public void OnChangeNum() {
		finish_btn.setText("完成(" + mPhotoSelectionController.getSelectedCount()
				+ ")");
	}

	private void excuteFinish() {
		if (mPhotoSelectionController.getSelectedCount() > 0) {
			Intent intent = new Intent();
			ArrayList<String> uri = new ArrayList<String>();
			for(PhotoUpload photoUpload:mPhotoSelectionController.getSelected()){
				String filePath = Utils.getPathFromContentUri(
						getActivity().getContentResolver(), photoUpload.getOriginalPhotoUri());
				uri.add(filePath);
			}
			intent.putStringArrayListExtra(PHOTO_URIS,uri);
			getActivity().setResult(Activity.RESULT_OK,intent);
			mPhotoSelectionController.clearSelected();
			getActivity().finish();
		}
		
	}
}
