package ouyang.activeandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.tjerkw.slideexpandable.library.ActionSlideExpandableListView;

import java.util.List;

import ouyang.adapter.FoodAdapter;
import ouyang.model.Food;

/**
 * Created by zuxiang on 2015/2/8.
 */
public class FoodActivity extends Activity {
    ActionSlideExpandableListView mListView;
    List<Food> mFoods;
    FoodAdapter mAdapter;

    private long mId;
    public static final int FOOD_RESULT = 0 << 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mListView = (ActionSlideExpandableListView) findViewById(R.id.listview);
        mId = getIntent().getLongExtra("id", 0L);
        mFoods = new Select().from(Food.class).where("cid=?", mId).execute();
        mAdapter = new FoodAdapter(this, mFoods, mId);
        mListView.setAdapter(mAdapter);
        mListView.setItemActionListener(new ActionSlideExpandableListView.OnActionClickListener() {
            @Override
            public void onClick(View listView, View buttonview, int position) {
                Food food = (Food) mAdapter.getItem(position);
                if (buttonview.getId() == R.id.btn_update) {
                    Intent intent = new Intent(FoodActivity.this, UpdateFoodActivity.class);
                    intent.putExtra("food", food);
                    startActivityForResult(intent, FOOD_RESULT);
                }

                if (buttonview.getId() == R.id.btn_delete) {
                    mAdapter.delete(food.pk_id);
                }
            }
        }, R.id.btn_update, R.id.btn_delete);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == FOOD_RESULT) {
            mFoods = new Select().from(Food.class).where("cid=?", mId).execute();
            mAdapter.replaceAll(mFoods);
        }
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





