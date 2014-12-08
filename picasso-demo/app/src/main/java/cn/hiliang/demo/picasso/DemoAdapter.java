package cn.hiliang.demo.picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

/**
 * Created by liang on 11/26/14.
 */
final class DemoAdapter extends BaseAdapter {
    private final Context context;
    private final List<People> peoples = new ArrayList<People>();

    public DemoAdapter(Context context) {
        this.context = context;

        for(Data.ANDROID_CN_PEOPLE data : Data.ANDROID_CN_PEOPLE.values()){
            peoples.add(data.getPeople());
        }
        Collections.shuffle(peoples);
        ArrayList<People> copy = new ArrayList<People>(peoples);
        peoples.addAll(copy);
        peoples.addAll(copy);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        People people = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, null);
        }

        SquaredImageView avatar_iv = (SquaredImageView) convertView.findViewById(R.id.avatar_iv);
        TextView name_tv = (TextView) convertView.findViewById(R.id.name_tv);

        name_tv.setText(people.getName());
        Picasso.with(context)
                .load(people.getAvatarUrl())
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher)
                .fit()
                .tag(context)
                .into(avatar_iv);

        return convertView;
    }

    @Override
    public int getCount() {
        return peoples.size();
    }

    @Override
    public People getItem(int position) {
        return peoples.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
