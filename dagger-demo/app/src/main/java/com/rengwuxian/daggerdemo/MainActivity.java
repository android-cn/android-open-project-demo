package com.rengwuxian.daggerdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rengwuxian.daggerdemo.model.Coder;

import javax.inject.Inject;

public class MainActivity extends Activity {
    @Inject Coder coder;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        App app = (App) getApplication();
        app.inject(this);
    }

    public void code(View view) {
        coder.code(textView);
    }

    public void nextCoder(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}



