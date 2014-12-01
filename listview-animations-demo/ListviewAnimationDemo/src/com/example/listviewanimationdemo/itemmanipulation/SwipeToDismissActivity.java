package com.example.listviewanimationdemo.itemmanipulation;

import java.util.Arrays;

import com.example.listviewanimationdemo.R;
import com.example.listviewanimationdemo.R.id;
import com.example.listviewanimationdemo.R.layout;
import com.example.listviewanimationdemo.R.menu;
import com.example.listviewanimationdemo.base.BaseListActivity;
import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.contextualundo.ContextualUndoAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.contextualundo.ContextualUndoAdapter.CountDownFormatter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.contextualundo.ContextualUndoAdapter.DeleteItemCallback;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;

public class SwipeToDismissActivity extends BaseListActivity implements  OnDismissCallback, DeleteItemCallback {

	public static void actionToSwipeDismiss(Context context) {
		Intent intent = new Intent(context, SwipeToDismissActivity.class);
		context.startActivity(intent);
	}

    private ArrayAdapter<Integer> mAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("item滑动删除");
        mAdapter = createListAdapter();
        setSwipeDismissAdapter();
    }
    

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_swipe_to_dismiss, menu);
		return true;
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.swipe_menu_main:
		    setSwipeDismissAdapter();
			break;
		case R.id.swipe_menu_with_undo:    
			setContextualUndoAdapter();
			break;
		case R.id.swipe_menu_with_undo_2:   
			setContextualUndoWithTimedDeleteAdapter();
			break;
		case R.id.swipe_menu_with_undo_3:   
			setContextualUndoWithTimedDeleteAndCountDownAdapter();
			break;
		default:
			break;
		}
		return true;
	}


	void initView() {
		// findViewById(R.id.home_item_animation_btn).setOnClickListener(this);
		// findViewById(R.id.home_item_expend_btn).setOnClickListener(this);
		// findViewById(R.id.home_swipe_to_dismiss_btn).setOnClickListener(this);
	}


    private void setSwipeDismissAdapter() {
        SwipeDismissAdapter adapter = new SwipeDismissAdapter(mAdapter, this);
        adapter.setAbsListView(getListView());
        getListView().setAdapter(adapter);
    }

    @Override
    public void onDismiss(final AbsListView listView, final int[] reverseSortedPositions) {
        for (int position : reverseSortedPositions) {
            mAdapter.remove(position);
        }
        Toast.makeText(this, "Removed positions: " + Arrays.toString(reverseSortedPositions), Toast.LENGTH_SHORT).show();
    }

    private void setContextualUndoAdapter() {
        ContextualUndoAdapter adapter = new ContextualUndoAdapter(mAdapter, R.layout.item_undo_row, R.id.undo_row_undobutton, this);
        adapter.setAbsListView(getListView());
        getListView().setAdapter(adapter);
    }

    @Override
    public void deleteItem(final int position) {
        mAdapter.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    private void setContextualUndoWithTimedDeleteAdapter() {
        ContextualUndoAdapter adapter = new ContextualUndoAdapter(mAdapter, R.layout.item_undo_row, R.id.undo_row_undobutton, 3000, this);
        adapter.setAbsListView(getListView());
        getListView().setAdapter(adapter);
    }

    private void setContextualUndoWithTimedDeleteAndCountDownAdapter() {
        ContextualUndoAdapter adapter = new ContextualUndoAdapter(mAdapter, R.layout.item_undo_row, R.id.undo_row_undobutton, 3000, R.id.undo_row_texttv, this, new MyFormatCountDownCallback());
        adapter.setAbsListView(getListView());
        getListView().setAdapter(adapter);
    }

    private class MyFormatCountDownCallback implements CountDownFormatter {

        @Override
        public String getCountDownString(final long millisUntilFinished) {
            int seconds = (int) Math.ceil(millisUntilFinished / 1000.0);

            if (seconds > 0) {
                return seconds+"s";
            }
            return "...";
        }
    }


    private class AnimSelectionAdapter extends ArrayAdapter<String> {

        public AnimSelectionAdapter() {
            addAll("Swipe-To-Dismiss", "Contextual Undo", "CU - Timed Delete", "CU - Count Down");
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            TextView tv = (TextView) convertView;
            if (tv == null) {
                tv = (TextView) LayoutInflater.from(SwipeToDismissActivity.this).inflate(android.R.layout.simple_list_item_1, parent, false);
            }

            tv.setText(getItem(position));

            return tv;
        }
    }

}
