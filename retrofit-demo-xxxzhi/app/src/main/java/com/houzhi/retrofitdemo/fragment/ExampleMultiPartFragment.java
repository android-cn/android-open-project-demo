package com.houzhi.retrofitdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.houzhi.retrofitdemo.R;
import com.houzhi.retrofitdemo.model.HttpbinRequest;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit.Call;

/**
 * @author houzhi
 */
public class ExampleMultiPartFragment extends BaseRequestExampleFragment {

    @Bind(R.id.et_value1)
    TextView etValue1;
    @Bind(R.id.body)
    TextView body;

    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");

    @OnClick(R.id.bt_request)
    void request() {


//        String postBody = ""
//                + "MultiPart\n"
//                + "--------\n"
//                + "\n"
//                + " * _1.0_ May 6, 2015\n"
//                + " * _1.1_ June 15, 2015\n"
//                + " * _1.2_ August 11, 2015\n";
        String postBody = body.getText().toString();

        RequestBody part1 = RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody);
        RequestBody formBody = new FormEncodingBuilder()
                .add("log", etValue1.getText().toString())
                .build();

        Call<HttpbinRequest> call = httpbinService.postMultiPart(part1, formBody);
        processCall(call);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_example_multipart, container, false);

        return view;
    }


}
