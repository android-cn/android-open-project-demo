package com.grumoon.androidultrapulltorefreshdemo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.grumoon.androidultrapulltorefreshdemo.R;
import com.grumoon.androidultrapulltorefreshdemo.util.DataUtil;


import java.util.HashMap;

public class AppHome extends ActionBarActivity implements DrawerMenuFragment.OnDrawerMenuItemClickListener {


    private Toolbar toolbar;
    private DrawerLayout dlHome;
    private ActionBarDrawerToggle drawerToggle;


    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_home);
        initView();
    }

    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dlHome = (DrawerLayout) findViewById(R.id.dl_home);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(this, dlHome, toolbar, 0, 0);
        drawerToggle.syncState();
        dlHome.setDrawerListener(drawerToggle);

        fm = getSupportFragmentManager();

        //添加菜单
        fm.beginTransaction().add(R.id.dl_menu, new DrawerMenuFragment()).commit();
    }

    @Override
    public void onItemClick(Object itemData) {
        dlHome.closeDrawers();
        try {
            HashMap<String, Object> itemDataMap = (HashMap<String, Object>) itemData;
            Fragment fragment = (Fragment) (itemDataMap.get(DataUtil.MENU_KEY_FRAGMENT));

            setTitle((String)itemDataMap.get(DataUtil.MENU_KEY_ITEM_NAME));

            fm.beginTransaction().replace(R.id.dl_container, fragment).commit();

        } catch (Exception e) {
        }
    }
}
