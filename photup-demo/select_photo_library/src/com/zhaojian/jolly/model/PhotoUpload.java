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
package com.zhaojian.jolly.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images.Thumbnails;
import android.text.TextUtils;

import com.zhaojian.jolly.selectphotos.SelectPhotoApplication;
import com.zhaojian.jolly.utils.Utils;

public class PhotoUpload {

	private static final HashMap<Uri, PhotoUpload> SELECTION_CACHE = new HashMap<Uri, PhotoUpload>();

	public static final int STATE_UPLOAD_COMPLETED = 5;
	public static final int STATE_UPLOAD_ERROR = 4;
	public static final int STATE_UPLOAD_IN_PROGRESS = 3;
	public static final int STATE_UPLOAD_WAITING = 2;
	public static final int STATE_SELECTED = 1;
	public static final int STATE_NONE = 0;

	public static final String FIELD_STATE = "state";
	static final String FIELD_URI = "uri";
	static final String FIELD_COMPLETED_DETECTION = "tag_detection";
	static final String FIELD_USER_ROTATION = "user_rotation";
	static final String FIELD_FILTER = "filter";
	static final String FIELD_CROP_L = "crop_l";
	static final String FIELD_CROP_T = "crop_t";
	static final String FIELD_CROP_R = "crop_r";
	static final String FIELD_CROP_B = "crop_b";
	static final String FIELD_ACCOUNT_ID = "acc_id";
	static final String FIELD_TARGET_ID = "target_id";
	static final String FIELD_QUALITY = "quality";
	static final String FIELD_RESULT_POST_ID = "r_post_id";
	static final String FIELD_CAPTION = "caption";
	static final String FIELD_TAGS_JSON = "tags";
	static final String FIELD_PLACE_NAME = "place_name";
	static final String FIELD_PLACE_ID = "place_id";

	static final String LOG_TAG = "PhotoUpload";
	static final float CROP_THRESHOLD = 0.01f; // 1%
	static final int MINI_THUMBNAIL_SIZE = 300;
	static final float MIN_CROP_VALUE = 0.0f;
	static final float MAX_CROP_VALUE = 1.0f;

	public static PhotoUpload getSelection(Uri uri) {
		// Check whether we've already got a Selection cached
		PhotoUpload item = SELECTION_CACHE.get(uri);

		if (null == item) {
			item = new PhotoUpload(uri);
			SELECTION_CACHE.put(uri, item);
		}

		return item;
	}

	public static void clearCache() {
		SELECTION_CACHE.clear();
	}

	public static void populateCache(List<PhotoUpload> uploads) {
		for (PhotoUpload upload : uploads) {
			SELECTION_CACHE.put(upload.getOriginalPhotoUri(), upload);
		}
	}

	public static PhotoUpload getSelection(Uri baseUri, long id) {
		return getSelection(Uri.withAppendedPath(baseUri, String.valueOf(id)));
	}

	private static boolean checkCropValues(float left, float top, float right,
			float bottom) {
		return Math.max(left, top) >= (MIN_CROP_VALUE + CROP_THRESHOLD)
				|| Math.min(right, bottom) <= (MAX_CROP_VALUE - CROP_THRESHOLD);
	}

	private static float santizeCropValue(float value) {
		return Math.min(1f, Math.max(0f, value));
	}

	/**
	 * Uri and Database Key
	 */
	private Uri mFullUri;
	private String mFullUriString;

	/**
	 * Edit Variables
	 */
	private boolean mCompletedDetection;
	private int mUserRotation;
	private float mCropLeft;
	private float mCropTop;
	private float mCropRight;
	private float mCropBottom;

	/**
	 * Upload Variables
	 */
	private String mAccountId;
	private String mTargetId;
	private String mResultPostId;
	private int mState;
	private String mCaption;
	String tagJson;
	private String mPlaceName;
	private String mPlaceId;

	private Account mAccount;
	private int mProgress;
	private Bitmap mBigPictureNotificationBmp;

	private boolean mNeedsSaveFlag = false;

	PhotoUpload() {
		// NO-Arg for Ormlite
	}

	private PhotoUpload(Uri uri) {
		mFullUri = uri;
		mFullUriString = uri.toString();
		reset();
	}

	public boolean beenCropped() {
		return checkCropValues(mCropLeft, mCropTop, mCropRight, mCropBottom);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PhotoUpload) {
			return getOriginalPhotoUri().equals(
					((PhotoUpload) obj).getOriginalPhotoUri());
		}
		return false;
	}

	public boolean isValid(Context context) {
		final String path = Utils.getPathFromContentUri(
				context.getContentResolver(), getOriginalPhotoUri());
		if (null != path) {
			File file = new File(path);
			return file.exists();
		}
		return false;
	}

	public Account getAccount() {
		return mAccount;
	}

	public Bitmap getBigPictureNotificationBmp() {
		return mBigPictureNotificationBmp;
	}

	public String getCaption() {
		return mCaption;
	}

	public RectF getCropValues() {
		return new RectF(mCropLeft, mCropTop, mCropRight, mCropBottom);
	}

	public RectF getCropValues(final int width, final int height) {
		return new RectF(mCropLeft * width, mCropTop * height, mCropRight
				* width, mCropBottom * height);
	}

	public Bitmap getDisplayImage(Context context) {
		try {
			final int size = SelectPhotoApplication.getInstanse()
					.getSmallestScreenDimension();
			Bitmap bitmap = Utils.decodeImage(context.getContentResolver(),
					getOriginalPhotoUri(), size);
			bitmap = Utils.rotate(bitmap, getExifRotation(context));
			return bitmap;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getDisplayImageKey() {
		return "dsply_" + getOriginalPhotoUri();
	}

	public Location getExifLocation(Context context) {
		final String filePath = Utils.getPathFromContentUri(
				context.getContentResolver(), getOriginalPhotoUri());
		if (null != filePath) {
			return Utils.getExifLocation(filePath);
		}
		return null;
	}

	public int getExifRotation(Context context) {
		return Utils.getOrientationFromContentUri(context.getContentResolver(),
				getOriginalPhotoUri());
	}

	public Uri getOriginalPhotoUri() {
		if (null == mFullUri && !TextUtils.isEmpty(mFullUriString)) {
			mFullUri = Uri.parse(mFullUriString);
		}
		return mFullUri;
	}

	public String getPlaceId() {
		return mPlaceId;
	}

	public String getResultPostId() {
		return mResultPostId;
	}

	public Bitmap getThumbnailImage(Context context) {
		if (ContentResolver.SCHEME_CONTENT.equals(getOriginalPhotoUri()
				.getScheme())) {
			return getThumbnailImageFromMediaStore(context);
		}

		final Resources res = context.getResources();
		int size = MINI_THUMBNAIL_SIZE;

		try {
			Bitmap bitmap = Utils.decodeImage(context.getContentResolver(),
					getOriginalPhotoUri(), size);
			// bitmap = Utils.rotate(bitmap, getExifRotation(context));
			return bitmap;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getThumbnailImageKey() {
		return "thumb_" + getOriginalPhotoUri();
	}

	public int getTotalRotation(Context context) {
		return (getExifRotation(context) + getUserRotation()) % 360;
	}

	public int getUploadProgress() {
		return mProgress;
	}

	public File getUploadSaveFile() {
		File dir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"photup");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		return new File(dir, System.currentTimeMillis() + ".jpg");
	}

	public int getUploadState() {
		return mState;
	}

	public String getUploadTargetId() {
		return mTargetId;
	}

	public int getUserRotation() {
		return mUserRotation % 360;
	}

	@Override
	public int hashCode() {
		return getOriginalPhotoUri().hashCode();
	}

	public boolean hasPlace() {
		return null != mPlaceId && null != mPlaceName;
	}

	public void populateFromAccounts(HashMap<String, Account> accounts) {
		if (null == mAccount && !TextUtils.isEmpty(mAccountId)) {
			mAccount = accounts.get(mAccountId);
		}
	}

	public Bitmap processBitmap(Bitmap bitmap, final boolean fullSize,
			final boolean modifyOriginal) {

		return bitmap;

	}

	public boolean requiresFaceDetectPass() {
		return !mCompletedDetection;
	}

	public boolean requiresProcessing(final boolean fullSize) {
		return getUserRotation() != 0 || (fullSize && beenCropped());
	}

	public boolean requiresSaving() {
		return mNeedsSaveFlag;
	}

	public void reset() {
		mState = STATE_NONE;
		mUserRotation = 0;
		mCaption = null;
		mCropLeft = mCropTop = MIN_CROP_VALUE;
		mCropRight = mCropBottom = MAX_CROP_VALUE;
		mCompletedDetection = false;

		setRequiresSaveFlag();
	}

	public void resetSaveFlag() {
		mNeedsSaveFlag = false;
	}

	public void rotateClockwise() {
		mUserRotation += 90;
		setRequiresSaveFlag();
	}

	public void setCaption(String caption) {
		if (TextUtils.isEmpty(caption)) {
			mCaption = null;
		} else {
			mCaption = caption;
		}

		setRequiresSaveFlag();
	}

	public void setCropValues(RectF cropValues) {
		if (checkCropValues(cropValues.left, cropValues.top, cropValues.right,
				cropValues.bottom)) {

			mCropLeft = santizeCropValue(cropValues.left);
			mCropTop = santizeCropValue(cropValues.top);
			mCropRight = santizeCropValue(cropValues.right);
			mCropBottom = santizeCropValue(cropValues.bottom);

		} else {

			mCropLeft = mCropTop = MIN_CROP_VALUE;
			mCropRight = mCropBottom = MAX_CROP_VALUE;
		}

		setRequiresSaveFlag();
	}

	public void setResultPostId(String resultPostId) {
		mResultPostId = resultPostId;
		setRequiresSaveFlag();
	}

	public void setUploadProgress(int progress) {
		if (progress != mProgress) {
			mProgress = progress;
		}
	}

	public void setUploadState(final int state) {
		if (mState != state) {
			mState = state;

			switch (state) {
			case STATE_UPLOAD_ERROR:
			case STATE_UPLOAD_COMPLETED:
				mBigPictureNotificationBmp = null;
				break;
			case STATE_SELECTED:
			case STATE_UPLOAD_WAITING:
				mProgress = -1;
				break;
			}

			setRequiresSaveFlag();
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		String caption = getCaption();
		if (null != caption) {
			sb.append(caption).append(" ");
		}

		if (hasPlace()) {
			sb.append("(").append(mPlaceName).append(")");
		}

		return sb.toString();
	}

	private Bitmap getThumbnailImageFromMediaStore(Context context) {
		Resources res = context.getResources();

		final int kind = Thumbnails.MINI_KIND;

		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts = new BitmapFactory.Options();

		try {
			final long id = Long.parseLong(getOriginalPhotoUri()
					.getLastPathSegment());
			Bitmap bitmap = Thumbnails.getThumbnail(
					context.getContentResolver(), id, kind, opts);
			bitmap = Utils.rotate(bitmap, getExifRotation(context));
			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}

	private void setRequiresSaveFlag() {
		mNeedsSaveFlag = true;
	}
}
