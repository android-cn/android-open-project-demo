package com.grumoon.androidultrapulltorefreshdemo.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.grumoon.androidultrapulltorefreshdemo.R;
import com.grumoon.androidultrapulltorefreshdemo.adapter.DrawerMenuAdapter;
import com.grumoon.androidultrapulltorefreshdemo.util.DataUtil;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrawerMenuFragment extends Fragment {

    private ListView lvMenu;

    private DrawerMenuAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drawer_menu, container, false);
        initView(v);
        return v;
    }


    private void initView(View v) {

        lvMenu = (ListView) v.findViewById(R.id.lv_menu);
        adapter = new DrawerMenuAdapter(getActivity(), DataUtil.getMenuList());
        lvMenu.setAdapter(adapter);

        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedIndex(position);

                if (getActivity() instanceof OnDrawerMenuItemClickListener) {
                    ((OnDrawerMenuItemClickListener) getActivity()).onItemClick(adapter.getItem(position));
                }
            }
        });


        //触发点击第一个menu
        try {
            lvMenu.performItemClick(lvMenu.getAdapter().getView(0, null, null), 0, lvMenu.getItemIdAtPosition(0));
        } catch (Exception e) {
        }


    }


    public interface OnDrawerMenuItemClickListener {
        public void onItemClick(Object itemData);
    }

}
