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
import uk.co.senab.photoview.PhotoViewAttacher.OnMatrixChangedListener;
import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class MultiTouchImageView extends PhotupImageView
        implements PhotoViewAttacher.OnPhotoTapListener {

    private final PhotoViewAttacher mAttacher;

    public MultiTouchImageView(Context context) {
        this(context, null);
    }

    public MultiTouchImageView(Context context, AttributeSet attr) {
        super(context, attr);
        mAttacher = new PhotoViewAttacher(this);
    }

    /**
     * Gets the Display Rectangle of the currently displayed Drawable. The Rectangle is relative to
     * this View and includes all scaling and translations.
     *
     * @return - RectF of Displayed Drawable
     */
    public RectF getDisplayRect() {
        return mAttacher.getDisplayRect();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        mAttacher.update();
    }

    /**
     * Register a callback to be invoked when the Matrix has changed for this View. An example would be
     * the user panning or scaling the Photo.
     *
     * @param listener - Listener to be registered.
     */
    public void setOnMatrixChangeListener(OnMatrixChangedListener listener) {
        mAttacher.setOnMatrixChangeListener(listener);
    }


    /**
     * Allows you to enable/disable the zoom functionality on the ImageView. When disable the ImageView
     * reverts to using the FIT_CENTER matrix.
     *
     * @param zoomable - Whether the zoom functionality is enabled.
     */
    public void setZoomable(boolean zoomable) {
        mAttacher.setZoomable(zoomable);
    }

	@Override
	public void onPhotoTap(View arg0, float arg1, float arg2) {
		// TODO Auto-generated method stub
		
	}

}
