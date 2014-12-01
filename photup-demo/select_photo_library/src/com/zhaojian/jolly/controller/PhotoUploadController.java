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
package com.zhaojian.jolly.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import android.content.Context;

import com.zhaojian.jolly.listener.OnNumChangeListener;
import com.zhaojian.jolly.model.PhotoUpload;
import com.zhaojian.jolly.selectphotos.SelectPhotoApplication;

public class PhotoUploadController {

	private Vector list = new Vector();

	public void addMyEventListener(OnNumChangeListener me) {
		list.add(me);
	}

	public void deleteMyEventListener(OnNumChangeListener me) {
		list.remove(me);
	}

	public void notifyMyEvent() {
		Iterator it = list.iterator();
		while (it.hasNext()) {
			((OnNumChangeListener) it.next()).OnChangeNum();
		}
	}

	public static PhotoUploadController getFromContext(Context context) {
		return SelectPhotoApplication.getInstanse().getPhotoUploadController();
	}

	private static List<PhotoUpload> checkListForInvalid(final Context context,
			final List<PhotoUpload> uploads) {
		ArrayList<PhotoUpload> toBeRemoved = null;

		for (PhotoUpload upload : uploads) {
			if (!upload.isValid(context)) {
				if (null == toBeRemoved) {
					toBeRemoved = new ArrayList<PhotoUpload>();
				}
				toBeRemoved.add(upload);
			}
		}

		if (null != toBeRemoved) {
			uploads.removeAll(toBeRemoved);
		}
		return toBeRemoved;
	}

	private final Context mContext;
	private final ArrayList<PhotoUpload> mSelectedPhotoList;

	private final ArrayList<PhotoUpload> mUploadingList;

	public PhotoUploadController(Context context) {
		mContext = context;
		mSelectedPhotoList = new ArrayList<PhotoUpload>();
		mUploadingList = new ArrayList<PhotoUpload>();

		populateFromDatabase();
	}

	public synchronized boolean addSelection(final PhotoUpload selection) {
		boolean result = false;

		if (!mSelectedPhotoList.contains(selection)) {
			selection.setUploadState(PhotoUpload.STATE_SELECTED);
			mSelectedPhotoList.add(selection);

			result = true;
		}

		// Remove it from Upload list if it's there
		if (mUploadingList.contains(selection)) {
			mUploadingList.remove(selection);
		}
		
		notifyMyEvent();
		
		return result;
	}

	public synchronized void addSelections(List<PhotoUpload> selections) {
		final HashSet<PhotoUpload> currentSelectionsSet = new HashSet<PhotoUpload>(
				mSelectedPhotoList);
		final HashSet<PhotoUpload> currentUploadSet = new HashSet<PhotoUpload>(
				mUploadingList);
		boolean listModified = false;

		for (final PhotoUpload selection : selections) {
			if (!currentSelectionsSet.contains(selection)) {

				// Remove it from Upload list if it's there
				if (currentUploadSet.contains(selection)) {
					mUploadingList.remove(selection);
				}

				selection.setUploadState(PhotoUpload.STATE_SELECTED);
				mSelectedPhotoList.add(selection);
				listModified = true;
			}
		}

	}

	public boolean addUpload(PhotoUpload selection) {
		if (null != selection && selection.isValid(mContext)) {
			synchronized (this) {
				if (!mUploadingList.contains(selection)) {
					selection.setUploadState(PhotoUpload.STATE_UPLOAD_WAITING);

					mUploadingList.add(selection);
					mSelectedPhotoList.remove(selection);

					return true;
				}
			}
		}

		return false;
	}

	public synchronized void addUploadsFromSelected() {

		ArrayList<PhotoUpload> eventResult = new ArrayList<PhotoUpload>(
				mSelectedPhotoList);

		mUploadingList.addAll(mSelectedPhotoList);
		mSelectedPhotoList.clear();

	}

	public synchronized void clearSelected() {
		if (!mSelectedPhotoList.isEmpty()) {

			// Reset States (as may still be in cache)
			for (PhotoUpload upload : mSelectedPhotoList) {
				upload.setUploadState(PhotoUpload.STATE_NONE);
			}

			ArrayList<PhotoUpload> eventResult = new ArrayList<PhotoUpload>(
					mSelectedPhotoList);

			// Clear from memory
			mSelectedPhotoList.clear();

		}
	}

	public synchronized int getActiveUploadsCount() {
		int count = 0;
		for (PhotoUpload upload : mUploadingList) {
			if (upload.getUploadState() != PhotoUpload.STATE_UPLOAD_COMPLETED) {
				count++;
			}
		}
		return count;
	}

	public synchronized PhotoUpload getNextUpload() {
		for (PhotoUpload selection : mUploadingList) {
			if (selection.getUploadState() == PhotoUpload.STATE_UPLOAD_WAITING) {
				return selection;
			}
		}
		return null;
	}

	public synchronized List<PhotoUpload> getSelected() {
		checkSelectedForInvalid(true);
		return new ArrayList<PhotoUpload>(mSelectedPhotoList);
	}

	public synchronized int getSelectedCount() {
		return mSelectedPhotoList.size();
	}

	public synchronized List<PhotoUpload> getUploadingUploads() {
		return new ArrayList<PhotoUpload>(mUploadingList);
	}

	public synchronized int getUploadsCount() {
		return mUploadingList.size();
	}

	public synchronized boolean hasSelections() {
		return !mSelectedPhotoList.isEmpty();
	}

	public synchronized boolean hasSelectionsWithPlace() {
		for (PhotoUpload selection : mSelectedPhotoList) {
			if (selection.hasPlace()) {
				return true;
			}
		}
		return false;
	}

	public synchronized boolean hasUploads() {
		return !mUploadingList.isEmpty();
	}

	public synchronized boolean hasWaitingUploads() {
		for (PhotoUpload upload : mUploadingList) {
			if (upload.getUploadState() == PhotoUpload.STATE_UPLOAD_WAITING) {
				return true;
			}
		}
		return false;
	}

	public synchronized boolean isOnUploadList(PhotoUpload selection) {
		return mUploadingList.contains(selection);
	}

	public synchronized boolean isSelected(PhotoUpload selection) {
		return mSelectedPhotoList.contains(selection);
	}

	public synchronized boolean moveFailedToSelected() {
		boolean result = false;

		final Iterator<PhotoUpload> iterator = mUploadingList.iterator();
		PhotoUpload upload;

		while (iterator.hasNext()) {
			upload = iterator.next();

			if (upload.getUploadState() == PhotoUpload.STATE_UPLOAD_ERROR) {
				// Reset State and add to selection list
				upload.setUploadState(PhotoUpload.STATE_SELECTED);
				mSelectedPhotoList.add(upload);

				// Remove from Uploading list
				iterator.remove();
				result = true;
			}
		}

		return result;
	}

	public boolean removeSelection(final PhotoUpload selection) {
		boolean removed = false;
		synchronized (this) {
			removed = mSelectedPhotoList.remove(selection);
		}

		if (removed) {
			// Reset State (as may still be in cache)
			selection.setUploadState(PhotoUpload.STATE_NONE);

		}
		
		notifyMyEvent();

		return removed;
	}

	public void removeUpload(final PhotoUpload selection) {
		boolean removed = false;
		synchronized (this) {
			removed = mUploadingList.remove(selection);
		}

		if (removed) {

			// Reset State (as may still be in cache)
			selection.setUploadState(PhotoUpload.STATE_NONE);

		}
	}

	public void reset() {
		// Clear the cache
		PhotoUpload.clearCache();

		synchronized (this) {
			// Clear the internal lists
			mSelectedPhotoList.clear();
			mUploadingList.clear();
		}

	}

	public synchronized void updateDatabase() {

	}

	void populateFromDatabase() {
	}

	private void checkSelectedForInvalid(final boolean sendEvent) {
	}

	private void checkUploadsForInvalid(final boolean sendEvent) {
	}

	private void postEvent(Object event) {
	}

}
