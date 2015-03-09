package com.example.yyb.myapplication.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class MyToast {
	
	public static Toast makeText(Context context,CharSequence text,int duration){
		Toast msg = Toast.makeText(context, text, duration);
		msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);  
		msg.show();
		return msg;
	}
	
	public static Toast makeText(Context context,int resId,int duration){
		Toast msg = Toast.makeText(context, resId, duration);
		msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2, msg.getYOffset() / 2);  
		msg.show();
		return msg;
	}
}
