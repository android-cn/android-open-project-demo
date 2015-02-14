package com.ffish.dlapkdemo;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by FFish on 2015/2/14.
 */
public class HostClassForModel3 {
    public static void methodForModel3(Context context){
        Toast.makeText(context, "模式3耦合度过高，请尽量不要用该模式开发...", Toast.LENGTH_SHORT).show();
    }
}
