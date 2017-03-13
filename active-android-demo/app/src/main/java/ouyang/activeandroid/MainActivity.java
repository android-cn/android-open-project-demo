package ouyang.activeandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;

import java.util.List;

import ouyang.model.Category;
import ouyang.model.CategoryResult;
import ouyang.model.Food;
import ouyang.model.FoodResult;


public class MainActivity extends Activity{
    Button mBtnActive;
    Button mBtnActiveContentProvider;

    private static final int ACTIVE = 1<<0;
    private static final int CONTENTPROVIDER = 1<<1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==ACTIVE) {
                startActivity(new Intent(MainActivity.this, FoodCategoryActivity.class));
            }
            if(msg.what==CONTENTPROVIDER){
                startActivity(new Intent(MainActivity.this, FoodCategoryCpActivity.class));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mBtnActive = (Button) findViewById(R.id.btn_activesimple);
        mBtnActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        mHandler.sendEmptyMessage(ACTIVE);
                    }
                }).start();
            }
        });

        mBtnActiveContentProvider=  (Button)findViewById(R.id.btn_activecontentprovdier);
        mBtnActiveContentProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        mHandler.sendEmptyMessage(CONTENTPROVIDER);
                    }
                }).start();
            }
        });

    }

    private void initData(){
        try {
            new Delete().from(Category.class).execute();
            new Delete().from(Food.class).execute();
            ActiveAndroid.beginTransaction();
            List<Category> categoryList = CategoryResult.getCategorys(CategoryResult.CATEGORY_JSON);
            for (Category category : categoryList) {
                category.save();
            }
            List<Food> foodList1 = FoodResult.getFoods(FoodResult.F1_JSON);
            for (Food food : foodList1) {
                food.save();
            }

            List<Food> foodList2 = FoodResult.getFoods(FoodResult.F2_JSON);
            for (Food food : foodList2) {
                food.save();
            }
            List<Food> foodList3 = FoodResult.getFoods(FoodResult.F3_JSON);
            for (Food food : foodList3) {
                food.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
