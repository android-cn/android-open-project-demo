/*
 * Copyright 2013 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 package com.example.listviewanimationdemo.itemmanipulation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.listviewanimationdemo.R;
import com.example.listviewanimationdemo.R.id;
import com.example.listviewanimationdemo.R.layout;
import com.example.listviewanimationdemo.base.BaseListActivity;
import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.widget.DynamicListView;

public class DragAndDropActivity extends BaseListActivity {

	public static void actionToDragAndDrop(Context context) {
		Intent intent = new Intent(context, DragAndDropActivity.class);
		context.startActivity(intent);
	}
	
    @Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("item拖动");
		setContentView(R.layout.activity_draganddrop);

        DynamicListView listView = (DynamicListView) findViewById(R.id.activity_draganddrop_listview);
		listView.setDivider(null);

        TextView headerView =new TextView(this);
        headerView.setText("这是头部");
        headerView.setTextColor(Color.RED);
        headerView.setGravity(Gravity.CENTER);
        listView.addHeaderView(headerView);

		final ArrayAdapter<Integer> adapter = createListAdapter();
		AlphaInAnimationAdapter animAdapter = new AlphaInAnimationAdapter(adapter);
		animAdapter.setInitialDelayMillis(100);
		animAdapter.setAbsListView(listView);
		listView.setAdapter(animAdapter);

		Toast.makeText(this, "长按item开始拖拽", Toast.LENGTH_LONG).show();
        listView.setOnItemMovedListener(new DynamicListView.OnItemMovedListener() {
            @Override
            public void onItemMoved(final int newPosition) {
                Toast.makeText(getApplicationContext(), adapter.getItem(newPosition) + " 移动到位置" + newPosition, Toast.LENGTH_SHORT).show();
            }
        });
	}
}
