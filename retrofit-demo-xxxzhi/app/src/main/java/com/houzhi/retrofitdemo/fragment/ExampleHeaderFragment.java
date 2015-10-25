package com.houzhi.retrofitdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.houzhi.retrofitdemo.R;
import com.houzhi.retrofitdemo.model.HttpbinRequest;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.Call;

/**
 * @author houzhi
 */
public class ExampleHeaderFragment extends BaseRequestExampleFragment {


    @Bind(R.id.et_agent)
    TextView etAgent;


    @OnClick(R.id.bt_header_params_request)
    void request() {

        Call<HttpbinRequest> call = httpbinService.testHeaderParams(etAgent.getText().toString());
        processCall(call);
    }


    @OnClick(R.id.bt_headers_map_request)
    void headersMapRequest() {

        Call<HttpbinRequest> call = httpbinService.testHeaderMapMethod();
        processCall(call);
    }

    @OnClick(R.id.bt_headers_request)
    void headerSingleRequest() {

        Call<HttpbinRequest> call = httpbinService.testHeaderMethod();
        processCall(call);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_example_header, container, false);

        return view;
    }


}
