package com.houzhi.retrofitdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.houzhi.retrofitdemo.fragment.ExampleBodyFragment;
import com.houzhi.retrofitdemo.fragment.ExampleFiledFragment;
import com.houzhi.retrofitdemo.fragment.ExampleHeaderFragment;
import com.houzhi.retrofitdemo.fragment.ExampleManipulationFragment;
import com.houzhi.retrofitdemo.fragment.ExampleMethodFragment;
import com.houzhi.retrofitdemo.fragment.ExampleMultiPartFragment;
import com.houzhi.retrofitdemo.fragment.ExampleSyncFragment;

public class DetailExampleActivity extends AppCompatActivity implements Constants {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        Fragment fragment = null;
        switch (getIntent().getIntExtra(KEY, EXAMPLE_REQUEST_METHOD)) {
            case EXAMPLE_FORM_ENCODE:
                fragment = new ExampleFiledFragment();
                break;
            case EXAMPLE_MULTIPART:
                //with multi part
                fragment = new ExampleMultiPartFragment();
                break;
            case EXAMPLE_HEADER_MANIPULATION:
                fragment = new ExampleHeaderFragment();
                break;
            case EXAMPLE_REQUEST_BODY:
                fragment = new ExampleBodyFragment();
                break;
            case EXAMPLE_REQUEST_METHOD:
                fragment = new ExampleMethodFragment();
                break;
            case EXAMPLE_URL_MANIPULATION:
                fragment = new ExampleManipulationFragment();
                break;
            case EXAMPLE_SYNCHRONOUS:
                fragment = new ExampleSyncFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
    }

}
