package com.codekk.java.test.dynamicproxy;

/**
 * Proxy class, implements {@link Operate}
 * 
 * @author trinea@codekk.com
 */
public class OperateImpl implements Operate {

    @Override
    public void operateMethod1() {
        System.out.println("Invoke operateMethod1");
        sleep(110);
    }

    @Override
    public void operateMethod2() {
        System.out.println("Invoke operateMethod2");
        sleep(120);
    }

    @Override
    public void operateMethod3() {
        System.out.println("Invoke operateMethod3");
        sleep(130);
    }

    private static void sleep(long millSeconds) {
        try {
            Thread.sleep(millSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
