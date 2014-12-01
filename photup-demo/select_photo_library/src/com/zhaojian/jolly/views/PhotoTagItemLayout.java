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
package com.zhaojian.jolly.views;

import uk.co.senab.photoview.PhotoViewAttacher;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.zhaojian.jolly.controller.PhotoUploadController;
import com.zhaojian.jolly.model.PhotoUpload;
import com.zhaojian.select_photo_library.R;

@SuppressLint("ViewConstructor")
@SuppressWarnings("deprecation")
public class PhotoTagItemLayout extends FrameLayout
        implements
        PhotoViewAttacher.OnMatrixChangedListener {

    static final String LOG_TAG = "PhotoTagItemLayout";

    private final MultiTouchImageView mImageView;

    private final LayoutInflater mLayoutInflater;

    private int mPosition;
    private final PhotoUpload mUpload;
    private final PhotoUploadController mController;

    public PhotoTagItemLayout(Context context, PhotoUploadController controller, PhotoUpload upload) {
        super(context);

        mController = controller;

        mLayoutInflater = LayoutInflater.from(context);

        mImageView = new MultiTouchImageView(context);
        mImageView.setOnMatrixChangeListener(this);
        addView(mImageView, FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);

        mUpload = upload;

    }

    public MultiTouchImageView getImageView() {
        return mImageView;
    }

    public PhotoUpload getPhotoSelection() {
        return mUpload;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }
    
    public void onMatrixChanged(RectF rect) {
        layoutTags(rect);
    }

    private void layoutTags(final RectF rect) {}


    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    void updateController() {

    }

}
