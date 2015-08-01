package com.skyace.test.flyrefreshtest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.race604.flyrefresh.FlyRefreshLayout;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private FlyRefreshLayout frl;
    private RecyclerView rv;
    private MyAdapter myAdapter;

//    private ArrayAdapter<String> adapter;

    private ArrayList<Integer> mDataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frl = (FlyRefreshLayout) findViewById(R.id.fly_layout);
        rv = (RecyclerView) findViewById(R.id.list);
//        adapter = new ArrayAdapter<String>();
//        adapter.add();
//        mDataset = new int[] { R.drawable.image, R.drawable.image, //
//                R.drawable.image, R.drawable.image };
              mDataset = new ArrayList<Integer>();
              mDataset.add(R.drawable.image1);
              mDataset.add(R.drawable.image2);
              mDataset.add(R.drawable.image3);
              mDataset.add(R.drawable.image4);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);

        myAdapter = new MyAdapter(mDataset);
        rv.setAdapter(myAdapter);

        frl.setOnPullRefreshListener(new FlyRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh(FlyRefreshLayout flyRefreshLayout) {
                new AsyncTask<Void,Void,Void>(){

                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        mDataset.add(0,R.drawable.refresh_image);//添加一条记录
                        myAdapter.notifyDataSetChanged();
                        frl.onRefreshFinish();
                    }

                }.execute();
            }


            @Override
            public void onRefreshAnimationEnd(FlyRefreshLayout flyRefreshLayout) {
                System.out.println("Refresh end");
            }
        });
    }
}
