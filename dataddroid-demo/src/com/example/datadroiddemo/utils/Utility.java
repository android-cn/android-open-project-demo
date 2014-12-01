package com.example.datadroiddemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class Utility {
	private Utility() {
	}

	public static final String TAG = "Utility";

	public static boolean isNetworkAvailable(Context context) {
		boolean netSataus = false;
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netinfo = cm.getActiveNetworkInfo();
		if (netinfo != null) {
			netSataus = netinfo.isAvailable();
		}
		return netSataus;
	}

}
