package com.example.datadroiddemo.request;

import android.content.Context;

import com.foxykeep.datadroid.requestmanager.RequestManager;

public final class RequestManagerImpl extends RequestManager {

	// Singleton management
	private static RequestManagerImpl sInstance;

	public synchronized static RequestManagerImpl from(Context context) {
		if (sInstance == null) {
			sInstance = new RequestManagerImpl(context);
		}

		return sInstance;
	}

	private RequestManagerImpl(Context context) {
		super(context, RequestServiceImpl.class);
	}
}
