package com.android.caij.xutilsdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.caij.xutilsdemo.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class HttpFragment extends Fragment {

	@ViewInject(R.id.get)
	private Button get;
	@ViewInject(R.id.post)
	private Button post;
	@ViewInject(R.id.tv_data)
	private TextView data;
	private HttpUtils http;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.http_fragment_view, container,
				false);
		ViewUtils.inject(this, view);
		http = new HttpUtils();
		return view;
	}

	/**
	 * get请求数据
	 * @param view
	 */
	@OnClick(R.id.get)
	public void get(View view) {
		String url = "http://www.weather.com.cn/data/cityinfo/101010100.html";
		RequestParams params = new RequestParams();
/*		//添加请求参数
		params.addBodyParameter(key, value);*/
		
/*		//添加请求头
		params.addHeader(name, value);*/
		http.send(HttpMethod.GET, url, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				data.setText(responseInfo.result);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(getActivity(), "访问失败" + msg, Toast.LENGTH_SHORT).show();
			}
			
		});
	}

	/**
	 * post请求数据
	 * @param view
	 */
	@OnClick(R.id.post)
	public void post(View view) {
		//这个地址只支持get， 这里只是示范。
		String url = "http://www.weather.com.cn/data/cityinfo/101010100.html"; 
		RequestParams params = new RequestParams();
		/*		//添加请求参数
		params.addBodyParameter(key, value);*/
		
/*		//添加请求头
		params.addHeader(name, value);*/
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				data.setText(responseInfo.result);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(getActivity(), "访问失败" + msg, Toast.LENGTH_SHORT).show();
			}
			
		});
		
	}
}
