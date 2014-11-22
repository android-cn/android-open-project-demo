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
	public static final int INDEX = 1;

	private EditText etUrl;
	private Button btnSend;
	private Button btnCancel;
	private TextView tvResult;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fr_common_request, null);

		initView(view);

		return view;
	}

	private void initView(View view) {
		etUrl = (EditText) view.findViewById(R.id.et_url);
		btnSend = (Button) view.findViewById(R.id.btn_send);
		btnCancel = (Button) view.findViewById(R.id.btn_cancel);
		tvResult = (TextView) view.findViewById(R.id.tv_result);

		etUrl.setText(Constants.DEFAULT_STRING_REQUEST_URL);

		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (StringUtil.isEmpty(etUrl.getText().toString())) {
					ToastUtil.showToast(getActivity(), "请输入请求地址");
					return;
				}

				StringRequest request = new StringRequest(StringUtil.preUrl(etUrl.getText().toString().trim()),
						new Listener<String>() {

							@Override
							public void onResponse(String response) {
								tvResult.setText(response);

							}
						}, new ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError arg0) {
								// TODO Auto-generated method stub

							}
						});
				
				VolleyUtil.getQueue(getActivity()).add(request);

			}
		});

		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
	}

}
