package com.grumoon.volleydemo.fragment;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.grumoon.volleydemo.R;
import com.grumoon.volleydemo.util.Constants;
import com.grumoon.volleydemo.util.StringUtil;
import com.grumoon.volleydemo.util.ToastUtil;
import com.grumoon.volleydemo.util.VolleyUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PostRequestFragment extends Fragment {
	public static final int INDEX = 41;
	private EditText etUrl;
	private EditText etParams;
	private Button btnSend;
	private TextView tvResult;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fr_post_request, container, false);

		initView(view);

		return view;
	}

	private void initView(View view) {
		etUrl = (EditText) view.findViewById(R.id.et_url);
		etParams = (EditText) view.findViewById(R.id.et_params);
		btnSend = (Button) view.findViewById(R.id.btn_send);
		tvResult = (TextView) view.findViewById(R.id.tv_result);

		etUrl.setText(Constants.DEFAULT_POST_REQUEST_URL);
		etParams.setText("mobileCode=13636527078;\nuserID=;");

		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (StringUtil.isEmpty(etUrl.getText().toString())) {
					ToastUtil.showToast(getActivity(), "请输入请求地址");
					return;
				}
				// 请求之前，先取消之前的请求（取消还没有进行完的请求）
				VolleyUtil.getQueue(getActivity()).cancelAll(this);

				tvResult.setText("");

				Listener<String> listener = new Listener<String>() {

					@Override
					public void onResponse(String response) {
						tvResult.setText(response);

					}
				};

				ErrorListener errorListener = new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						ToastUtil.showToast(getActivity(), getResources().getString(R.string.request_fail_text));

					}
				};

				StringRequest request = new StringRequest(Method.POST, StringUtil.preUrl(etUrl.getText().toString().trim()), listener, errorListener) {

					//重写getParams设置post请求的参数
					@Override
					protected Map<String, String> getParams() throws AuthFailureError {
						Map<String, String> paramMap = new HashMap<String, String>();
						try {
							String paramsStr = etParams.getText().toString().trim();
							String[] paramsArray = paramsStr.split(";");
							for (String param : paramsArray) {
								if (StringUtil.isEmpty(param)) {
									continue;
								}
								String[] keyValueArray = param.split("=");
								if (keyValueArray.length < 1) {
									continue;
								}
								if (StringUtil.isEmpty(keyValueArray[0])) {
									continue;
								}

								paramMap.put(keyValueArray[0].trim(), keyValueArray.length > 1 ? keyValueArray[1].trim() : "");

							}
						} catch (Exception e) {
							ToastUtil.showToast(getActivity(), "参数格错误");
						}
						return paramMap;
					}

				};

				// 请求加上Tag,用于取消请求
				request.setTag(this);

				VolleyUtil.getQueue(getActivity()).add(request);

			}
		});
	}

	@Override
	public void onDestroyView() {
		VolleyUtil.getQueue(getActivity()).cancelAll(this);
		super.onDestroyView();
	}

}
