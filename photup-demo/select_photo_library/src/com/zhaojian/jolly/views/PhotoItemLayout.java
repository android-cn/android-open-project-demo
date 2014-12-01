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

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.zhaojian.jolly.controller.PhotoUploadController;
import com.zhaojian.jolly.model.PhotoUpload;
import com.zhaojian.select_photo_library.R;

public class PhotoItemLayout extends CheckableFrameLayout implements View.OnClickListener {

    private final PhotupImageView mImageView;
    private final CheckableImageView mButton;

    private PhotoUpload mSelection;

    private boolean mShowCaption = false;

    private final PhotoUploadController mController;

    public PhotoItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.item_grid_photo_internal, this);

        mController = PhotoUploadController.getFromContext(context);

        mImageView = (PhotupImageView) findViewById(R.id.iv_photo);

        mButton = (CheckableImageView) findViewById(R.id.civ_button);
        mButton.setOnClickListener(this);
    }

    public PhotupImageView getImageView() {
        return mImageView;
    }

    public void setShowCheckbox(boolean visible) {
        if (visible) {
            mButton.setVisibility(View.VISIBLE);
            mButton.setOnClickListener(this);
        } else {
            mButton.setVisibility(View.GONE);
            mButton.setOnClickListener(null);
        }
    }

    public void setShowCaption(boolean show) {
        mShowCaption = show;
    }

    public void onClick(View v) {
        if (null != mSelection) {

            // Toggle check to show new state
            toggle();

            // Update the controller
            if (isChecked()) {
                mController.addSelection(mSelection);
                
            } else {
                mController.removeSelection(mSelection);
            }
        }
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // For simple implementation, or internal size is always 0.
        // We depend on the container to specify the layout size of
        // our view. We can't really know what it is since we will be
        // adding and removing different arbitrary views and do not
        // want the layout to change as this happens.
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
 
        // Children are just made to fill our space.
        int childWidthSize = getMeasuredWidth();
        int childHeightSize = getMeasuredHeight();
        //高度和宽度一样
        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void setChecked(final boolean b) {
        super.setChecked(b);
        if (View.VISIBLE == mButton.getVisibility()) {
            mButton.setChecked(b);
        }
    }

    public PhotoUpload getPhotoSelection() {
        return mSelection;
    }

    public void setPhotoSelection(PhotoUpload selection) {
        if (mSelection != selection) {
            mButton.clearAnimation();
            mSelection = selection;
        }

    }

}
