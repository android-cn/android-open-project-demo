package com.example.datadroiddemo.request;

import com.foxykeep.datadroid.requestmanager.Request;

public final class RequestFactory {

	// Request types
	public static final int REQUEST_TYPE_CITY_LIST = 0;

	// Response data
	public static final String BUNDLE_EXTRA_RESULT = "bundle.result";

	private RequestFactory() {
		// no public constructor
	}

	public static Request getCityListRequest() {
		Request request = new Request(REQUEST_TYPE_CITY_LIST);
		return request;
	}
}
