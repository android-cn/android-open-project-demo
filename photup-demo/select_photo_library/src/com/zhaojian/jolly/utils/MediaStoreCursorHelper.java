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
package com.zhaojian.jolly.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Log;

import com.zhaojian.jolly.model.MediaStoreBucket;
import com.zhaojian.jolly.model.PhotoUpload;

public class MediaStoreCursorHelper {

	public static final String[] PHOTOS_PROJECTION = { Images.Media._ID,
			Images.Media.MINI_THUMB_MAGIC, Images.Media.DATA,
			Images.Media.BUCKET_DISPLAY_NAME, Images.Media.BUCKET_ID,
			Images.Media.SIZE };
	public static final String PHOTOS_ORDER_BY = Images.Media.DATE_ADDED
			+ " desc";

	public static final Uri MEDIA_STORE_CONTENT_URI = Images.Media.EXTERNAL_CONTENT_URI;

	public static ArrayList<PhotoUpload> photosCursorToSelectionList(
			Uri contentUri, Cursor cursor) {
		ArrayList<PhotoUpload> items = new ArrayList<PhotoUpload>(
				cursor.getCount());
		PhotoUpload item;

		if (cursor.moveToFirst()) {
			do {
				try {
					item = photosCursorToSelection(contentUri, cursor);
					if (null != item) {
						items.add(item);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} while (cursor.moveToNext());
		}

		// Need to reset the List so that oldest is first
		Collections.reverse(items);

		return items;
	}

	public static PhotoUpload photosCursorToSelection(Uri contentUri,
			Cursor cursor) {
		PhotoUpload item = null;

		try {
			File file = new File(cursor.getString(cursor
					.getColumnIndexOrThrow(ImageColumns.DATA)));
			if (file.exists()) {
				item = PhotoUpload
						.getSelection(contentUri, cursor.getInt(cursor
								.getColumnIndexOrThrow(ImageColumns._ID)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return item;
	}

	public static void photosCursorToBucketList(Cursor cursor,
			ArrayList<MediaStoreBucket> items) {
		final HashSet<String> bucketIds = new HashSet<String>();

		final int idColumn = cursor.getColumnIndex(ImageColumns.BUCKET_ID);
		final int nameColumn = cursor
				.getColumnIndex(ImageColumns.BUCKET_DISPLAY_NAME);
		final int dataColumn = cursor.getColumnIndex(ImageColumns.SIZE);
		if (cursor.moveToFirst()) {
			do {
				try {
					final String bucketId = cursor.getString(idColumn);
					final long size = cursor.getLong(dataColumn);
					File file = new File(cursor.getString(cursor
							.getColumnIndexOrThrow(ImageColumns.DATA)));
					if (file.exists()) {
						if (bucketIds.add(bucketId)) {
							items.add(new MediaStoreBucket(bucketId, cursor
									.getString(nameColumn)));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} while (cursor.moveToNext());
		}
	}

	public static Cursor openPhotosCursor(Context context, Uri contentUri) {
		return context.getContentResolver().query(contentUri,
				PHOTOS_PROJECTION, null, null, PHOTOS_ORDER_BY);
	}

}
