package com.houzhi.retrofitdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.houzhi.retrofitdemo.R;
import com.houzhi.retrofitdemo.model.Args;
import com.houzhi.retrofitdemo.model.HttpbinRequest;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.Call;

/**
 * @author houzhi
 */
public class ExampleBodyFragment extends BaseRequestExampleFragment {

    @Bind(R.id.et_value1)
    TextView etValue1;

    @Bind(R.id.et_value2)
    TextView etValue2;

    @Bind(R.id.et_value3)
    TextView etValue3;

    @OnClick(R.id.bt_request)
    void request() {
        Args args = new Args();
        args.setArg1(etValue1.getText().toString());
        args.setArg2(etValue2.getText().toString());
        args.setArg3(etValue3.getText().toString());

        Call<HttpbinRequest> call = httpbinService.postBody(args);
        processCall(call);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_example_body, container, false);

        return view;
    }


}
