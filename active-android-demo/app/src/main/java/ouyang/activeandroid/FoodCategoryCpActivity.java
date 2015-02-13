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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.activeandroid.content.ContentProvider;

import java.util.ArrayList;
import java.util.List;

import ouyang.adapter.FoodAdapter;
import ouyang.adapter.FoodCategoryAdapter;
import ouyang.adapter.FoodCategoryCursorAdapter;
import ouyang.model.Category;

/**
 * Created by zuxiang on 2015/2/11.
 */
public class FoodCategoryCpActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>{
    ListView mListView;
    private FoodCategoryCursorAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activeandroid);
        getActionBar().setDisplayHomeAsUpEnabled(true);


        mListView = (ListView)findViewById(R.id.listview);
        mAdapter = new FoodCategoryCursorAdapter(this);
        mListView.setAdapter(mAdapter);


        getLoaderManager().initLoader(0, null,this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FoodCategoryCpActivity.this, FoodCpActivity.class);
                intent.putExtra("id", parent.getAdapter().getItemId(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                ContentProvider.createUri(Category.class, null),
                null, null, null, null
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
