package ouyang.activeandroid;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.activeandroid.content.ContentProvider;
import com.activeandroid.query.Delete;
import com.tjerkw.slideexpandable.library.ActionSlideExpandableListView;

import java.util.List;

import ouyang.adapter.FoodAdapter;
import ouyang.adapter.FoodCategoryCursorAdapter;
import ouyang.adapter.FoodCursorAdapter;
import ouyang.model.Category;
import ouyang.model.Food;

/**
 * Created by zuxiang on 2015/2/12.
 */
public class FoodCpActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    ListView mListView;
    List<Food> mFoods;
    FoodCursorAdapter mAdapter;

    private long mId;
    public static final int FOODCP_RESULT = 0 << 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activeandroid);
        getActionBar().setDisplayHomeAsUpEnabled(true);


        mListView = (ListView) findViewById(R.id.listview);
        mId = getIntent().getLongExtra("id", 0L);
        mAdapter = new FoodCursorAdapter(this);
        mListView.setAdapter(mAdapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                ContentProvider.createUri(Food.class, null),
                null, "cid=?", new String[]{String.valueOf(mId)}, null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
