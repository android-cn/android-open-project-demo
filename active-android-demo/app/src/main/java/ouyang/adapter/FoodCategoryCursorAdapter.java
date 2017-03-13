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

/**
 * Created by zuxiang on 2015/2/11.
 */
public class FoodCategoryCursorAdapter extends CursorAdapter {
    private Cursor mCursor;

    public FoodCategoryCursorAdapter(Context context) {
        super(context, null, false);
    }


    @Override
    public Object getItem(int position) {
        mCursor.moveToPosition(position);
        return Category.getCategory(mCursor);
    }

    @Override
    public long getItemId(int position) {
        mCursor.moveToPosition(position);
        return Category.getCategory(mCursor).id;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        mCursor = cursor;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return layoutInflater.inflate(R.layout.item_category,null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Category category = Category.getCategory(cursor);
        TextView tvName = (TextView)view.findViewById(R.id.tv_name);
        tvName.setText(category.name);
    }
}
