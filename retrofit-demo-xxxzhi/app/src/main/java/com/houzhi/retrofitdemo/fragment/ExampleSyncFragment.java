package com.houzhi.retrofitdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.houzhi.retrofitdemo.R;
import com.houzhi.retrofitdemo.model.HttpbinRequest;

import java.io.IOException;

import butterknife.OnClick;
import retrofit.Call;

/**
 * @author houzhi
 */
public class ExampleSyncFragment extends BaseRequestExampleFragment {

    @OnClick(R.id.bt_sync)
    void onSync() {
        //execute

        final Call<HttpbinRequest> call = httpbinService.testGet();
        new SimpleStringReturnTask() {

            @Override
            protected String doInBackground(Void... params) {
                try {
                    return call.execute().body().toString();
                } catch (IOException e) {
                    e.printStackTrace();
                    return "" + e.getMessage();
                }
            }
        }.execute();

    }

    @OnClick(R.id.bt_async)
    void onAsync() {

        Call<HttpbinRequest> call = httpbinService.testGet();

        // call enquene
        processCall(call);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request_method, container, false);

        return view;
    }


}
