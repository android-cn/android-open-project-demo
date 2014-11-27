package com.example.datadroiddemo.base;

import android.view.View;
import android.widget.TextView;

import com.foxykeep.datadroid.requestmanager.Request;

/**
 * Define the interface for UI related, eg. activity, fragment
 */
public interface RequestBaseUi extends RequestBase {

	/**
	 * Get the content view id, work with setContentView(int layoutId) and
	 * inflate(int layoutId)
	 * 
	 * @return the layout id
	 */
	public int getContentViewId();

	/**
	 * Do the initialization of the cursor loader to load cached data from DB
	 */
	public void initLoader();

	/**
	 * Get the first request to launch
	 * 
	 * @return the first request
	 */
	public Request getInitialRequest();

	/**
	 * Get the error indicate view
	 * 
	 * @return indicator view for errors
	 */
	public View getErrorIndicatorLayout();

	/**
	 * Get the error msg indicate view
	 * 
	 * @return indicator view for errors
	 */
	public TextView getErrorMsgTextView();

	/**
	 * Indicate the need to show the data loading progress
	 */
	public boolean needShowLoadingIndicator();

	/**
	 * Get the loading progress view
	 * 
	 * @return indicator view for loading progress
	 */
	public View getLoadingIndicatorView();

	/**
	 * Indicate the data loading begins. User could show some progress dialog
	 */
	public void onLoadingIndicatorShow();

	/**
	 * Indicate the data loading ends. User could hide some progress dialog
	 */
	public void onLoadingIndicatorHide();
}
