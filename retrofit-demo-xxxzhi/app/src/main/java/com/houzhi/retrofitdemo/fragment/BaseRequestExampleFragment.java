package com.houzhi.retrofitdemo.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.houzhi.retrofitdemo.R;
import com.houzhi.retrofitdemo.http.HttpbinService;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * @author houzhi
 */
public class BaseRequestExampleFragment extends Fragment {

    protected void processCall(Call call) {

        call.enqueue(new Callback() {
            @Override
            public void onResponse(retrofit.Response response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    setResult(response.body().toString());
                } else {
                    String error = null;
                    try {
                        error = response.errorBody() == null ? "" : response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                        error = "";
                    }

                    setResult("request fail: " + error + ",\n" + response.message());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                setResult(t.getMessage());
            }
        });
    }

    public abstract class SimpleStringReturnTask extends AsyncTask<Void, Void, String> {
        Dialog dialog = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(getActivity(), "wait", "wait for a minute", true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            setResult(s);
            dialog.dismiss();
        }
    }

    protected void setResult(String result) {
        if (result != null) {
            tvResult.setText(result);
        } else {
            tvResult.setText("请求结果为null");
        }
    }


    protected HttpbinService httpbinService;
    /**
     * 请求结果
     */
    @Bind(R.id.tv_res)
    protected TextView tvResult;

    public BaseRequestExampleFragment() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://httpbin.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        httpbinService = retrofit.create(HttpbinService.class);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }
}
