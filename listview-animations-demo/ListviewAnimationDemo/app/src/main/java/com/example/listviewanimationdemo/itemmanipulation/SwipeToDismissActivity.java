package com.example.listviewanimationdemo.itemmanipulation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.example.listviewanimationdemo.R;
import com.example.listviewanimationdemo.base.BaseListActivity;
import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;

public class SwipeToDismissActivity extends BaseListActivity implements OnDismissCallback {

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


    private void setSwipeDismissAdapter() {
        SwipeDismissAdapter adapter = new SwipeDismissAdapter(mAdapter, this);
        adapter.setAbsListView(getListView());
        getListView().setAdapter(adapter);
    }

    private void setContextualUndoAdapter() {
    }

    private void setContextualUndoWithTimedDeleteAdapter() {
    }

    private void setContextualUndoWithTimedDeleteAndCountDownAdapter() {

    }

    @Override
    public void onDismiss(ViewGroup listView, int[] reverseSortedPositions) {

    }
}
