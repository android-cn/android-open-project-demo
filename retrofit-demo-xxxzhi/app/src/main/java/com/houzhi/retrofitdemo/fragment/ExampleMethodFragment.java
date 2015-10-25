package com.houzhi.retrofitdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.houzhi.retrofitdemo.R;
import com.houzhi.retrofitdemo.model.HttpbinRequest;

import butterknife.OnClick;
import retrofit.Call;

/**
 * @author houzhi
 */
public class ExampleMethodFragment extends BaseRequestExampleFragment {
    Call tCall = null;


    @OnClick(R.id.bt_get)
    void onGet() {

        //如果不用Call会出现Unable to create call adapter for class　异常
//        Call<User> t = service.exampleMethodGet();
//        processCall(t);

        Call<HttpbinRequest> call = httpbinService.testGet();
        processCall(call);
//        new SimpleStringReturnTask(){
//
//            @Override
//            protected String doInBackground(Void... params) {
//                return service.exampleMethodGet().toString();
//            }
//        }.execute();

    }

    @OnClick(R.id.bt_post)
    void onPost() {

        Call<HttpbinRequest> call = httpbinService.testPost();
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
