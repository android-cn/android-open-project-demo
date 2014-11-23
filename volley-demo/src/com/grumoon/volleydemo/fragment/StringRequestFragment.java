package com.grumoon.volleydemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.grumoon.volleydemo.R;
import com.grumoon.volleydemo.util.Constants;
import com.grumoon.volleydemo.util.StringUtil;
import com.grumoon.volleydemo.util.ToastUtil;
import com.grumoon.volleydemo.util.VolleyUtil;

public class StringRequestFragment extends Fragment {
	public static final int INDEX = 11;

	private EditText etUrl;
	private Button btnSend;
	private TextView tvResult;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fr_string_request, container,false);

		initView(view);

		return view;
	}

	private void initView(View view) {
		etUrl = (EditText) view.findViewById(R.id.et_url);
		btnSend = (Button) view.findViewById(R.id.btn_send);
		tvResult = (TextView) view.findViewById(R.id.tv_result);

		etUrl.setText(Constants.DEFAULT_STRING_REQUEST_URL);

		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (StringUtil.isEmpty(etUrl.getText().toString())) {
					ToastUtil.showToast(getActivity(), "请输入请求地址");
					return;
				}
				//请求之前，先取消之前的请求（取消还没有进行完的请求）
				VolleyUtil.getQueue(getActivity()).cancelAll(this);
				tvResult.setText("");

				StringRequest request = new StringRequest(StringUtil.preUrl(etUrl.getText().toString().trim()),
						new Listener<String>() {

							@Override
							public void onResponse(String response) {
								tvResult.setText(response);

							}
						}, new ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError arg0) {
								ToastUtil.showToast(getActivity(), getResources().getString(R.string.request_fail_text));

							}
						});
				//请求加上Tag,用于取消请求
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
