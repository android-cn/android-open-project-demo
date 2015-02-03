package com.codekk.java.test.dynamicproxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.codekk.java.test.dynamicproxy.util.ProxyUtils;

/**
 * Dynamic Proxy Demo
 * 
 * @author trinea@codekk.com
 */
public class Main {
    public static void main(String[] args) {

        TimingInvocationHandler timingInvocationHandler = new TimingInvocationHandler(new OperateImpl());
        Operate operate = (Operate)(Proxy.newProxyInstance(Operate.class.getClassLoader(), new Class[] {Operate.class},
                timingInvocationHandler));

        // call proxy instance method
        operate.operateMethod1();
        System.out.println();
        operate.operateMethod2();
        System.out.println();
        operate.operateMethod3();

        // print info of proxy class
        System.out.println("proxy class is: " + operate.getClass().getName());
        System.out.println("\r\nsuper class of proxy class is: " + operate.getClass().getSuperclass().getName());
        System.out.println("\r\ninterfaces of proxy class are: ");
        for (Class inter : operate.getClass().getInterfaces()) {
            System.out.println("\t" + inter.getName());
        }
        System.out.println("\r\nmethods of proxy class are: ");
        for (Method method : operate.getClass().getMethods()) {
            System.out.println("\t" + method.getName());
        }

        // save proxy class to root of this project, you can use jd-gui to see content of the saved file
        String saveFileName = "$Proxy0.class";
        ProxyUtils.saveProxyClass(saveFileName, operate.getClass().getSimpleName(), operate.getClass().getInterfaces());
        System.out.println("\r\nContent of " + operate.getClass().getSimpleName() + ".class has saved to file "
                + saveFileName + " at root of this project");
    }
}
