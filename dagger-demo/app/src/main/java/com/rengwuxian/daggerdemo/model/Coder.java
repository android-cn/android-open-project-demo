package com.rengwuxian.daggerdemo.model;

import android.widget.TextView;

import com.rengwuxian.daggerdemo.R;

/**
 * 程序员：为老板写代码，每写一行会向老板汇报
 */
public class Coder {
    int codeCount = 0;
    Boss boss;

    public Coder(Boss boss) {
        this.boss = boss;
    }

    public void code(TextView textView) {
        String words = textView.getResources().getString(R.string.words_when_coding, ++codeCount);
        textView.setText(words + "\n" + boss.check(codeCount));
    }
}
