package com.grumoon.androidultrapulltorefreshdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.grumoon.androidultrapulltorefreshdemo.R;
import com.grumoon.androidultrapulltorefreshdemo.util.DataUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by grumoon on 15/1/11.
 */
public class DrawerMenuAdapter extends BaseAdapter {

    private int selectedIndex = 0;

    private List<HashMap<String, Object>> menuList;
    private LayoutInflater layoutInflater;

    public DrawerMenuAdapter(Context context, List<HashMap<String, Object>> menuList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.menuList = menuList;
    }

    @Override
    public int getCount() {
        if (menuList == null) {
            return 0;
        }
        return menuList.size();
    }

    @Override
    public Object getItem(int position) {
        if (menuList == null || position > menuList.size() - 1) {
            return null;
        }
        return menuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.fragment_drawer_menu_item, parent, false);
            viewHolder.tvGroupName = (TextView) convertView.findViewById(R.id.tv_group_name);
            viewHolder.tvItemName = (TextView) convertView.findViewById(R.id.tv_item_name);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Map<String, Object> menuItem = menuList.get(position);
        if (menuItem.containsKey(DataUtil.MENU_KEY_GROUP_NAME)) {
            viewHolder.tvGroupName.setText(menuItem.get(DataUtil.MENU_KEY_GROUP_NAME).toString());
            viewHolder.tvGroupName.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvGroupName.setVisibility(View.GONE);
        }

        viewHolder.tvItemName.setText(menuItem.get(DataUtil.MENU_KEY_ITEM_NAME).toString());

        if (position == selectedIndex) {
            viewHolder.tvItemName.setBackgroundResource(R.drawable.drawer_menu_item_selected_bg);
        } else {
            viewHolder.tvItemName.setBackgroundResource(R.drawable.drawer_menu_item_bg);
        }

        return convertView;
    }


    static class ViewHolder {
        TextView tvGroupName;
        TextView tvItemName;
    }


    public void setSelectedIndex(int position) {
        if (menuList != null && position < menuList.size()) {
            selectedIndex = position;
            notifyDataSetChanged();
        }
    }
}
