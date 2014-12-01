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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import uk.co.senab.bitmapcache.BitmapLruCache;
import android.accounts.Account;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.service.textservice.SpellCheckerService.Session;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.zhaojian.jolly.constant.Constants;
import com.zhaojian.jolly.constant.PreferenceConstants;
import com.zhaojian.jolly.controller.PhotoUploadController;
import com.zhaojian.jolly.tasks.PhotupThreadFactory;
import com.zhaojian.jolly.utils.Utils;

public class SelectPhotoApplication {

	static final String LOG_TAG = "PhotupApplication";
	public static final String THREAD_FILTERS = "filters_thread";

	static final float EXECUTOR_POOL_SIZE_PER_CORE = 1.5f;

	private ExecutorService mMultiThreadExecutor, mSingleThreadExecutor,
			mDatabaseThreadExecutor;
	private BitmapLruCache mImageCache;

	private static PhotoUploadController mPhotoController;

	private static Context mContext;

	private static SelectPhotoApplication uniqueInstance = null;

	private SelectPhotoApplication() {
		// Exists only to defeat instantiation.
	}

	public static SelectPhotoApplication init(Context context) {
		if (uniqueInstance == null) {
			uniqueInstance = new SelectPhotoApplication();
			mContext = context;
			mPhotoController = new PhotoUploadController(mContext);
		}
		return uniqueInstance;
	}
	
	public static SelectPhotoApplication getInstanse(){
		if (uniqueInstance == null) {
			uniqueInstance = new SelectPhotoApplication();
		}
		return uniqueInstance;
	}

	public ExecutorService getMultiThreadExecutorService() {
		if (null == mMultiThreadExecutor || mMultiThreadExecutor.isShutdown()) {
			final int numThreads = Math.round(Runtime.getRuntime()
					.availableProcessors() * EXECUTOR_POOL_SIZE_PER_CORE);
			mMultiThreadExecutor = Executors.newFixedThreadPool(numThreads,
					new PhotupThreadFactory());
		}
		return mMultiThreadExecutor;
	}

	public ExecutorService getPhotoFilterThreadExecutorService() {
		if (null == mSingleThreadExecutor || mSingleThreadExecutor.isShutdown()) {
			mSingleThreadExecutor = Executors
					.newSingleThreadExecutor(new PhotupThreadFactory(
							THREAD_FILTERS));
		}
		return mSingleThreadExecutor;
	}

	public ExecutorService getDatabaseThreadExecutorService() {
		if (null == mDatabaseThreadExecutor
				|| mDatabaseThreadExecutor.isShutdown()) {
			mDatabaseThreadExecutor = Executors
					.newSingleThreadExecutor(new PhotupThreadFactory());
		}
		return mDatabaseThreadExecutor;
	}

	public BitmapLruCache getImageCache() {
		if (null == mImageCache) {
			mImageCache = new BitmapLruCache(mContext,
					Constants.IMAGE_CACHE_HEAP_PERCENTAGE);
		}
		return mImageCache;
	}

	public PhotoUploadController getPhotoUploadController() {
		return mPhotoController;
	}

	@SuppressWarnings("deprecation")
	public int getSmallestScreenDimension() {
		WindowManager wm = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		return Math.min(display.getHeight(), display.getWidth());
	}
	
	public Context getContext(){
		return mContext;
	}

}
