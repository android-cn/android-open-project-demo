package com.caesar.PSL_demo;

import java.util.ArrayList;
import java.util.Random;

import com.caesar.PSL_demo.adapter.PinnedAdapter;
import com.caesar.PSL_demo.bean.Item;
import com.caesar.PSL_demo.library.PinnedSectionListView;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class MainActivity extends Activity {
	
	private static final String TAG  ="MainActivity";
	
	private PinnedAdapter mAdapter;
	
	private PinnedSectionListView mListView;

    Handler handler = new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            mListView.stopRefresh();

        }
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		
		mListView = (PinnedSectionListView) this.findViewById(R.id.listview);
		mAdapter = new PinnedAdapter(this, getDate());
		mListView.setAdapter(mAdapter);
        mListView.setOnRefreshListener(new PinnedSectionListView.OnRefreshListener() {
            @Override
            public void OnRefresh() {
                Log.d(TAG, "OnRefresh");
                handler.sendEmptyMessageDelayed(0,2000);
            }
        });

        Log.d(TAG,"onCreate");


	}

	
	
	
	
	/**
	 * 
	 * 
	 * 获得测试数据
	 */
	private ArrayList<Item> getDate() {
		String [] ary = getResources().getStringArray(R.array.mall_name);
		ArrayList<Item> items = new ArrayList<Item>();
		int sectionPosition = 0, listPosition = 0;
		Random  random = new Random();
		for (int i = 0; i < ary.length; i++) {
			Item section = new Item();
			section.content = ary[i];
			section.sectionPosition = sectionPosition;
			section.type = Item.SECTION;
			section.listPostition = listPosition++;
			items.add(section);
			for (int j = 0; j < random.nextInt(20); j++) {
				Item item = new Item();
				item.content = ary[i]+"商品"+j;
				item.type=Item.ITEM;
				item.sectionPosition = sectionPosition;
				item.listPostition = listPosition++;
				items.add(item);
			}
			sectionPosition++;
		}
		
		return items;
	}

}
