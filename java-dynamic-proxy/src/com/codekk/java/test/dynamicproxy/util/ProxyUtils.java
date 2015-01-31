package com.codekk.java.test.dynamicproxy.util;

import java.io.FileOutputStream;
import java.io.IOException;

import sun.misc.ProxyGenerator;

/**
 * Utils of proxy
 * 
 * @author trinea@codekk.com
 * @thx to <a href="http://rejoy.iteye.com/blog/1627405">Rejoy's Blog</a>
 */
public class ProxyUtils {

    /**
     * Save proxy class to path
     * 
     * @param path path to save proxy class
     * @param proxyClassName name of proxy class
     * @param interfaces interfaces of proxy class
     * @return
     */
    public static boolean saveProxyClass(String path, String proxyClassName, Class[] interfaces) {
        if (proxyClassName == null || path == null) {
            return false;
        }

        // get byte of proxy class
        byte[] classFile = ProxyGenerator.generateProxyClass(proxyClassName, interfaces);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(path);
            out.write(classFile);
            out.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
