package com.grumoon.volleydemo.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.grumoon.volleydemo.R;
import com.grumoon.volleydemo.util.Constants;
import com.grumoon.volleydemo.util.StringUtil;
import com.grumoon.volleydemo.util.ToastUtil;
import com.grumoon.volleydemo.util.VolleyUtil;

public class JsonRequestFragment extends Fragment {
	public static final int INDEX = 12;

	private ListView lvCar;
	private static final int[] ids = { R.id.tv_car_name, R.id.tv_car_level, R.id.tv_car_price };
	private static final String[] keys = { "name", "level", "price" };
	private List<Map<String, String>> carDataList;

	private SimpleAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fr_json_request, container,false);

		carDataList = new ArrayList<Map<String, String>>();

		lvCar = (ListView) view.findViewById(R.id.lv_car);
		adapter = new SimpleAdapter(getActivity(), carDataList, R.layout.fr_json_request_list_item, keys, ids);
		lvCar.setAdapter(adapter);

		// 发起请求

		JsonObjectRequest request = new JsonObjectRequest(StringUtil.preUrl(Constants.DEFAULT_JSON_REQUEST_URL), null,
				new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							if (!response.has("result")) {
								return;
							}

							JSONObject result = response.getJSONObject("result");

							if (!result.has("fctlist")) {
								return;
							}

							JSONArray factoryArray = result.getJSONArray("fctlist");

							if (factoryArray.length() == 0) {
								return;
							}

							JSONObject factory = factoryArray.getJSONObject(0);

							if (!factory.has("serieslist")) {
								return;
							}

							JSONArray seriesArray = factory.getJSONArray("serieslist");

							carDataList.clear();

							for (int i = 0; i < seriesArray.length(); i++) {
								JSONObject series = seriesArray.getJSONObject(i);
								Map<String, String> seriesMap = new HashMap<String, String>();

								seriesMap.put("name", series.getString("name"));
								seriesMap.put("level", "级别："+series.getString("levelname"));
								seriesMap.put("price", "价格："+series.getString("price"));

								carDataList.add(seriesMap);

							}
							
							adapter.notifyDataSetChanged();

						} catch (Exception e) {
							ToastUtil.showToast(getActivity(), getResources().getString(R.string.request_fail_text));
						}

					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						ToastUtil.showToast(getActivity(), getResources().getString(R.string.request_fail_text));
					}
				});
		// 请求加上Tag,用于取消请求
		request.setTag(this);

		VolleyUtil.getQueue(getActivity()).add(request);

		return view;
	}

	@Override
	public void onDestroyView() {
		VolleyUtil.getQueue(getActivity()).cancelAll(this);
		super.onDestroyView();
	}

}
