package com.example.listviewanimationdemo;

import java.util.Arrays;

import com.example.listviewanimationdemo.base.BaseListActivity;
import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;

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

public class SwipeToDismissActivity extends BaseListActivity implements OnDismissCallback {

    public static void actionToSwipeDismiss(Context context) {
        Intent intent = new Intent(context, SwipeToDismissActivity.class);
        context.startActivity(intent);
    }

    private ArrayAdapter<Integer> mAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = createListAdapter();
        setSwipeDismissAdapter();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.swipe_to_dismiss, menu);
        return true;
    }

    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.swipe_menu_main:
                setSwipeDismissAdapter();
                break;
            case R.id.swipe_menu_with_undo:
                break;
            case R.id.swipe_menu_with_undo_2:
                break;
            case R.id.swipe_menu_with_undo_3:
                break;
            default:
                break;
        }
        return true;
    }


    private void setSwipeDismissAdapter() {
        SwipeDismissAdapter adapter = new SwipeDismissAdapter(mAdapter, this);
        adapter.setAbsListView(getListView());
        getListView().setAdapter(adapter);
    }


    @Override
    public void onDismiss(ViewGroup listView, int[] reverseSortedPositions) {

    }


    private class AnimSelectionAdapter extends ArrayAdapter<String> {

        public AnimSelectionAdapter() {
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            TextView tv = (TextView) convertView;
            if (tv == null) {
                tv = (TextView) LayoutInflater.from(SwipeToDismissActivity.this).
                        inflate(android.R.layout.simple_list_item_1, parent, false);
            }

            tv.setText(getItem(position));

            return tv;
        }
    }

}
