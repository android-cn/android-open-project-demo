package com.example.zhy_baseadapterhelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

public class MainActivity extends Activity
{

	private ListView mListView;
	private QuickAdapter<Bean> mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mListView = (ListView) findViewById(R.id.id_lv_main);

		// 设置适配器

		mListView.setAdapter(mAdapter = new QuickAdapter<Bean>(
				MainActivity.this, R.layout.item_list, Bean.beans)
		{

			@Override
			protected void convert(BaseAdapterHelper helper, final Bean item)
			{
				helper.setText(R.id.tv_title, item.getTitle());
				helper.setText(R.id.tv_describe, item.getDesc());
				helper.setText(R.id.tv_phone, item.getPhone());
				helper.setText(R.id.tv_time, item.getTime());
				helper.setImageUrl(R.id.id_icon, item.getUrl());

				helper.setOnClickListener(R.id.id_icon, new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Toast.makeText(MainActivity.this,
								"I am icon in " + item.getTitle(),
								Toast.LENGTH_SHORT).show();
					}
				});

			}
		});

	}


}
