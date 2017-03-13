package ouyang.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

import ouyang.activeandroid.R;
import ouyang.model.Food;

/**
 * Created by zuxiang on 2015/2/8.
 */
public class FoodAdapter extends SimpleBaseAdapter<Food> {
    private long cid;

    public FoodAdapter(Context context, List<Food> data, long cid) {
        super(context, data);
        this.cid = cid;
    }

    @Override
    public int getItemResource() {
        return R.layout.item_food;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        Food food = data.get(position);
        TextView tvName = holder.getView(R.id.tv_name);
        TextView tvPrice = holder.getView(R.id.tv_price);
        tvName.setText(food.name);
        tvPrice.setText("￥" + food.price);
        return convertView;
    }

    public void delete(int id) {
        new Delete().from(Food.class).where("pk_id = ? ", id).execute();
        data = new Select().from(Food.class).where("cid=?", cid).execute();
        notifyDataSetChanged();
    }

}
