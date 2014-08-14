package com.rengwuxian.daggerdemo.model;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 程序员：为老板写代码，每写一行会向老板汇报
 */
public class Coder {
    private static AtomicInteger total = new AtomicInteger(0); // 程序员的总数

    public int number; // 每个程序员有自己的编号
    public int lineCount = 0; // 写的代码行数

    public Coder() {
        number = total.addAndGet(1);
    }

    /**
     * 程序员写一行代码
     */
    public void code() {
        lineCount++;
    }

    public static void resetTotal() {
        total.set(0);
    }
}
