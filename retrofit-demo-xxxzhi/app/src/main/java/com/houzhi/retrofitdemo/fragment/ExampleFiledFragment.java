package com.houzhi.retrofitdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.houzhi.retrofitdemo.R;
import com.houzhi.retrofitdemo.model.HttpbinRequest;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.Call;

/**
 * @author houzhi
 */
public class ExampleFiledFragment extends BaseRequestExampleFragment {


    @Bind(R.id.et_value1)
    TextView etValue1;

    @Bind(R.id.et_value2)
    TextView etValue2;

    @Bind(R.id.et_value3)
    TextView etValue3;


    @OnClick(R.id.bt_request)
    void request() {

        final Map<String, String> map = new HashMap<>();
        map.put("log", etValue1.getText().toString());
        map.put("pwd", etValue2.getText().toString());

        final String type = etValue3.getText().toString();

        Call<HttpbinRequest> call = httpbinService.postFieldUrlEncode(type, map);
        processCall(call);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_example_field, container, false);

        return view;
    }


}
