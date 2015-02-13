package ouyang.activeandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.activeandroid.query.Update;

import ouyang.model.Food;

/**
 * Created by zuxiang on 2015/2/11.
 */
public class UpdateFoodActivity extends Activity {
    EditText mEtName;
    EditText mEtPrice;
    Button mBtnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatefood);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtPrice = (EditText) findViewById(R.id.et_price);
        mBtnUpdate = (Button) findViewById(R.id.btn_update);

        final Food food = (Food) getIntent().getSerializableExtra("food");
        mEtName.setText(food.name);
        mEtPrice.setText(String.valueOf(food.price));
        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEtName.getText().toString();
                String price = mEtPrice.getText().toString();
                if (name.equals("") || price.equals("")) {
                    return;
                }
                new Update(Food.class).set("Name=?,Price=?", name, price).where("pk_id=?",food.pk_id).execute();
                setResult(FoodActivity.FOOD_RESULT);
                finish();
            }
        });

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
