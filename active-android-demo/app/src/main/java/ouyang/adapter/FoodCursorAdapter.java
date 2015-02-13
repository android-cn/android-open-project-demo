package ouyang.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import ouyang.activeandroid.R;
import ouyang.model.Category;
import ouyang.model.Food;

/**
 * Created by zuxiang on 2015/2/12.
 */
public class FoodCursorAdapter extends CursorAdapter {
    private Cursor mCursor;

    public FoodCursorAdapter(Context context) {
        super(context, null, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        mCursor = cursor;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return layoutInflater.inflate(R.layout.item_cpfood, null);
    }

    @Override
    public Object getItem(int position) {
        mCursor.moveToPosition(position);
        return Food.getFood(mCursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Food food = Food.getFood(cursor);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        TextView tvPrice = (TextView) view.findViewById(R.id.tv_price);
        tvName.setText(food.name);
        tvPrice.setText("￥" + food.price);
    }
}
