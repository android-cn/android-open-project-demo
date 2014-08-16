package com.mingy.fancycoverflow.demo;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;

import com.mingy.fancycoverflow.base.FancyCoverFlowBaseAdapter;


/**
 * 一、FancyCoverFlow使用步骤：
 * 1、在activity的布局文件中引用FancyCoverFlow，eg：
 * <at.technikum.mti.fancycoverflow.FancyCoverFlow
		android:id="@+id/fancyCoverFlowId"
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"
		android:layout_centerVertical="true"
		fcf:maxRotation="45"
		fcf:unselectedAlpha="0.3"
		fcf:unselectedSaturation="0.0"
		fcf:unselectedScale="0.4"
		fcf:scaleDownGravity="0.5"
		/>
 * 
 * 
 * 2、自定义Adapter，Adapter继承自FancyCoverFlowBaseAdapter,eg：
 * 	public class FancyCoverFlowBaseAdapter extends FancyCoverFlowAdapter {
		public FancyCoverFlowBaseAdapter( Context context, Integer[] dataList ){
			mDataList = dataList;
		}
		
		@Override
		public int getCount() {
			return mDataList.length;
		}
	
		@Override
		public Object getItem(int position) {
			return mDataList[ position ];
		}
	
		@Override
		public long getItemId(int position) {
			return position;
		}
	
		@Override
		public View getCoverFlowItem(int position, View reusableView, ViewGroup parent) {
			ImageView imageView = null;
	
	        if (reusableView != null) {
	            imageView = (ImageView) reusableView;
	        } else {
	            imageView = new ImageView(parent.getContext());
	            imageView.setLayoutParams(new FancyCoverFlow.LayoutParams(LayoutParams.WRAP_CONTENT,256));
	        }
	
	        imageView.setBackgroundResource( mDataList[ position ] );
	        
	        return imageView;
		}
	
		private Integer[] mDataList = null;
	}
 * 
 * 
 * 3、实例化FancyCoverFlow，为其设置Adapter，eg
 * FancyCoverFlowBaseAdapter fancyCoverFlowBaseAdapter = new FancyCoverFlowBaseAdapter( this, getBaseDataList( ) );
   mFancyCoverFlow.setAdapter( fancyCoverFlowBaseAdapter );
 * 
 * 
 * 二、注意事项：
 * 1、在布局文件中引用FancyCoverFlow时，如果需要使用自定义属性，必须要在跟布局文件中定义自定义属性的命名空间“xmlns:fcf="http://schemas.android.com/apk/res-auto"”；
 * 2、FancyCoverFlow是效果是基于画廊Gallery的，所以在自定义Adapter时，在其getCoverFlowItem方法中一定要注意自定义view的属性，详情参见本demo中的FancyCoverFlowBaseAdapter类；
 * */
public class FancyCoverFlowMainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_fancycoverflow_layout);
	}
	
	@Override
	public void findViews() {
		mFancyCoverFlowList = ( ListView )findViewById( R.id.fancyCoverFlowListId );
		mFancyCoverFlow = ( FancyCoverFlow )findViewById( R.id.fancyCoverFlowId );
	}
	
	@Override
	public void getData() {
		
	}
	
	@Override
	public void showContent() {
		initFancyCoverFlowList( );
		showFancyCoverFlow( 0 );
	}
	
	private void initFancyCoverFlowList( ){
		mFancyCoverFlowList.setOnItemClickListener( new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				showFancyCoverFlow( arg2 );
			}
		});
		
		mFancyCoverFlowList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getListData()));
	}
	
	private void showFancyCoverFlow( int position ){
		switch( position ){
		case 0:{
			showBaseCoverFlow( );
		}
		break;
		default:{
			
		}
		break;
		}
	}
	
	private void showBaseCoverFlow( ){
		FancyCoverFlowBaseAdapter fancyCoverFlowBaseAdapter = new FancyCoverFlowBaseAdapter( this, getBaseDataList( ) );
		mFancyCoverFlow.setAdapter( fancyCoverFlowBaseAdapter );
	}
	
	private ArrayList<String> getListData( ){
		ArrayList<String> listData = new ArrayList<String>( );
		
		listData.add( "Base FancyCoverFlow" );
		
		return listData;
	}
	
	private Integer[] getBaseDataList( ){
		Integer[] baseData = new Integer[]{R.drawable.h5, R.drawable.h8, R.drawable.h8s, R.drawable.h9, R.drawable.h10 };
		
		return baseData;
	}
	
	private ListView mFancyCoverFlowList = null;
	private FancyCoverFlow mFancyCoverFlow = null;
}
