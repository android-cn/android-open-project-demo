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
package com.zhaojian.jolly.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zhaojian.jolly.model.PhotoUpload;
import com.zhaojian.select_photo_library.R;

public class CameraBaseAdapter extends BaseAdapter {

    private final LayoutInflater mLayoutInflater;

    public CameraBaseAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return 1;
    }

    public long getItemId(int position) {
        return position;
    }

    public PhotoUpload getItem(int position) {
        return null;
    }

    public View getView(int position, View view, ViewGroup parent) {
        if (null == view) {
            view = mLayoutInflater.inflate(R.layout.item_grid_camera, parent, false);
        }

        return view;
    }

}
