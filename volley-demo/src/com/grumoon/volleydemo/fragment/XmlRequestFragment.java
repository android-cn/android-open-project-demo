package com.grumoon.volleydemo.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

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
import com.grumoon.volleydemo.R;
import com.grumoon.volleydemo.custom.XmlRequest;
import com.grumoon.volleydemo.util.Constants;
import com.grumoon.volleydemo.util.StringUtil;
import com.grumoon.volleydemo.util.ToastUtil;
import com.grumoon.volleydemo.util.VolleyUtil;

public class XmlRequestFragment extends Fragment {
	public static final int INDEX = 31;

	private ListView lvWeather;
	private static final int[] ids = { R.id.tv_weather_city, R.id.tv_weather_detail, R.id.tv_weather_temp,
			R.id.tv_weather_wind };
	private static final String[] keys = { "city", "detail", "temp", "wind" };
	private List<Map<String, String>> weatherDataList;

	private SimpleAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fr_xml_request, container, false);

		weatherDataList = new ArrayList<Map<String,String>>();


		lvWeather = (ListView) view.findViewById(R.id.lv_weather);
		adapter = new SimpleAdapter(getActivity(), weatherDataList, R.layout.fr_xml_request_list_item, keys, ids);
		lvWeather.setAdapter(adapter);

		// 发起请求
		XmlRequest request = new XmlRequest(StringUtil.preUrl(Constants.DEFAULT_XML_REQUEST_URL),
				new Listener<XmlPullParser>() {

					@Override
					public void onResponse(XmlPullParser parser) {
						try {
							weatherDataList.clear();

							int eventType = parser.getEventType();
							while (eventType != XmlPullParser.END_DOCUMENT) {
								switch (eventType) {
								case XmlPullParser.START_TAG:
									String nodeName = parser.getName();
									if ("city".equals(nodeName)) {
										Map<String, String> weatherMap = new HashMap<String, String>();

										weatherMap.put("city", parser.getAttributeValue(2));
										weatherMap.put("detail", parser.getAttributeValue(5));
										weatherMap.put("temp", parser.getAttributeValue(7)+"℃ 到 "+parser.getAttributeValue(6)+"℃");
										weatherMap.put("wind", parser.getAttributeValue(8));

										weatherDataList.add(weatherMap);
									}
									break;
								}
								eventType = parser.next();
							}


							adapter.notifyDataSetChanged();
						} catch (XmlPullParserException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
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
