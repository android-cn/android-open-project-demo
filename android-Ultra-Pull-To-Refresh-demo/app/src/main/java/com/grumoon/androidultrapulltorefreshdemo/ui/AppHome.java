package com.grumoon.androidultrapulltorefreshdemo.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.grumoon.androidultrapulltorefreshdemo.R;

public class AppHome extends ActionBarActivity {


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


    }


    public void drawerMenuClose() {
        dlHome.closeDrawers();
    }


}
