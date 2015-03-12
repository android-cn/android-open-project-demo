package com.huxian.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.huxian.Constants;
import com.huxian.R;
import com.huxian.adapter.RecyclerViewAdapter;

public class RecyclerViewActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, Constants.NBA_LOGO_LIST);
        recyclerView.setAdapter(adapter);
    }
}
