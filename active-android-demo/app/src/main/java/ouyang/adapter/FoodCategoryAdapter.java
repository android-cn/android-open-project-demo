package ouyang.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import ouyang.activeandroid.R;
import ouyang.model.Category;

/**
 * Created by zuxiang on 2015/2/8.
 */
public class FoodCategoryAdapter extends SimpleBaseAdapter<Category> {

    public FoodCategoryAdapter(Context context, List<Category> data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_category;
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).id;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        TextView tvName= holder.getView(R.id.tv_name);
        tvName.setText(data.get(position).name);
        return convertView;
    }
}
