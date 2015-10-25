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
 */
public class ExampleManipulationFragment extends BaseRequestExampleFragment {

    @Bind(R.id.et_path)
    TextView etPath;

    @Bind(R.id.et_value1)
    TextView etValue1;

    @Bind(R.id.et_value2)
    TextView etValue2;

    @Bind(R.id.et_value3)
    TextView etValue3;

    @OnClick(R.id.bt_request)
    void request() {
        final String path = etPath.getText().toString();

        final Map<String, String> map = new HashMap<>();
        map.put("arg2", etValue2.getText().toString());
        map.put("arg3", etValue3.getText().toString());

        final String arg1 = etValue1.getText().toString();

        Call<HttpbinRequest> call = httpbinService.testManipulation(path, arg1, map);
        processCall(call);

    }

    public ExampleManipulationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_example_manipulation, container, false);
    }


}
