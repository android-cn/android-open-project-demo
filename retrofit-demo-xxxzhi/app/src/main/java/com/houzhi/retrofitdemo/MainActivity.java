package com.houzhi.retrofitdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements Constants {

    @OnClick({R.id.bt_introduce, R.id.bt_request_method, R.id.bt_url_manipulation, R.id.bt_request_body, R.id.bt_form_encode,
            R.id.bt_multipart,
            R.id.bt_header_manipulation, R.id.bt_sync})
    public void click(View v) {
        int id = EXAMPLE_REQUEST_METHOD;
        switch (v.getId()) {
            case R.id.bt_introduce:
                Intent intent = new Intent(this, IntroduceActivity.class);
                startActivity(intent);
                return;
            case R.id.bt_request_method:
                id = EXAMPLE_REQUEST_METHOD;
                break;
            case R.id.bt_url_manipulation:
                id = EXAMPLE_URL_MANIPULATION;
                break;
            case R.id.bt_request_body:
                id = EXAMPLE_REQUEST_BODY;
                break;
            case R.id.bt_form_encode:
                id = EXAMPLE_FORM_ENCODE;
                break;
            case R.id.bt_multipart:
                id = EXAMPLE_MULTIPART;
                break;
            case R.id.bt_header_manipulation:
                id = EXAMPLE_HEADER_MANIPULATION;
                break;
            case R.id.bt_sync:
                id = EXAMPLE_SYNCHRONOUS;
                break;
        }
        Intent intent = new Intent(this, DetailExampleActivity.class);
        intent.putExtra(KEY, id);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


}
