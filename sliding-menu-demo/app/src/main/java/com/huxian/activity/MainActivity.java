package com.huxian.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.huxian.R;
import com.huxian.adapter.MainAdapter;


public class MainActivity extends Activity {

    private String[] data = new String[]{ "Default", "ViewPager", "ListView", "RecyclerView", "WebView"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MainAdapter adapter = new MainAdapter(this, data);
        recyclerView.setAdapter(adapter);

    }

}
