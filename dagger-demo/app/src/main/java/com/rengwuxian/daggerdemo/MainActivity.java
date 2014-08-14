package com.rengwuxian.daggerdemo;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rengwuxian.daggerdemo.model.Boss;
import com.rengwuxian.daggerdemo.model.Coder;

import javax.inject.Inject;

public class MainActivity extends Activity implements View.OnClickListener {
    @Inject Coder coder;
    @Inject Boss boss;

    TextView textView;
    Button codeBt;
    Button nextCoderBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        codeBt = (Button) findViewById(R.id.codeBt);
        nextCoderBt = (Button) findViewById(R.id.nextCoderBt);

        // 注入依赖
        App app = (App) getApplication();
        app.inject(this);

        // 将程序员的编号写到ActionBar和按钮
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("程序员" + coder.number + "的工位");
        codeBt.setText(getString(R.string.coder_code, coder.number));
        nextCoderBt.setText(getString(R.string.next_coder, boss.name));

        // 设置监听器
        codeBt.setOnClickListener(this);
        nextCoderBt.setOnClickListener(this);
    }

    /**
     * 程序员编码
     */
    public void code() {
        coder.code();
        String conversation = textView.getResources().getString(R.string.words_when_coding, coder.number, coder.lineCount);
        textView.setText(conversation + "\n" + boss.check(coder.lineCount));
    }

    /**
     * 老板去另一个工位
     */
    public void nextCoder() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.codeBt:
                code();
                break;
            case R.id.nextCoderBt:
                nextCoder();
                break;
        }
    }
}



