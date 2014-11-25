package com.example.datadroiddemo.base;

import android.os.Bundle;

import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.requestmanager.RequestManager.RequestListener;

/**
 * Define the common interface for activity, fragment and service
 */
public interface RequestBase extends RequestListener, ExceptionHandler {

	/**
	 * Do the initialization of the data members
	 * 
	 * @param savedInstanceState
	 */
	public void initAllMembers(Bundle savedInstanceState);

	/**
	 * Launch any kind of request
	 * 
	 * @param request
	 */
	public void launchRequest(Request request);

	/**
	 * Subclass should override this method to handle the request result
	 * 
	 * @param request
	 * @param bundle
	 */
	public void onRequestSucess(Request request, Bundle bundle);

	/**
	 * Subclass could override this method to handle error
	 * 
	 * @param exceptionType
	 */
	public void onRequestError(int exceptionType);

}
