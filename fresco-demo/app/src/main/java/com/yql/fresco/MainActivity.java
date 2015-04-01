package com.yql.fresco;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_in_xml).setOnClickListener(this);
        findViewById(R.id.btn_in_code).setOnClickListener(this);
        findViewById(R.id.btn_advance).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_in_xml:
                startSimpleItent(SimpleActivity.TYPE_XML);
                break;
            case R.id.btn_in_code:
                startSimpleItent(SimpleActivity.TYPE_CODE);
                break;
            case R.id.btn_advance:
                startActivity(new Intent(MainActivity.this, AdvanceActivity.class));
                break;
        }
    }

    private void startSimpleItent(int type) {
        Intent intent = new Intent(this, SimpleActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
