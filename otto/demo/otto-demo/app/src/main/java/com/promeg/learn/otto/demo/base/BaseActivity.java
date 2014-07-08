package com.promeg.learn.otto.demo.base;

import com.squareup.otto.Bus;

import android.app.Activity;
import android.os.Bundle;

import javax.inject.Inject;

public class BaseActivity extends Activity {
    protected int theme = 0;

    @Inject
    protected Bus mBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GraphRetriever.from(this).inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBus.register(this);
    }

    @Override
    protected void onStop() {
        mBus.unregister(this);
        super.onStop();
    }

}
