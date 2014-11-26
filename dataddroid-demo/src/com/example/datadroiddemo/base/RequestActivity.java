package com.example.datadroiddemo.base;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.example.datadroiddemo.request.RequestManagerImpl;
import com.example.datadroiddemo.utils.Utility;
import com.foxykeep.datadroid.requestmanager.Request;

/**
 * 
 * The goal of this base class: 1. bind the Datadroid to use 2. bind the
 * lifecycle of activity to Datadroid callback 3. process the loading and error
 * state
 * 
 */

public abstract class RequestActivity extends FragmentActivity implements
		RequestBaseUi {
	protected static final String SAVED_STATE_REQUEST_LIST = "savedStateRequestList";
	protected RequestManagerImpl mRequestManager = null;
	protected ArrayList<Request> mRequestList = null;
	protected boolean mContextDestroyed = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(getContentViewId());
		initAllMembers(savedInstanceState);
		if (Utility.isNetworkAvailable(this)) {
			launchRequest(getInitialRequest());
			if (needShowLoadingIndicator()) {
				onLoadingIndicatorShow();
			}
		} else {
			handleException(EXCEPTION_TYPE_NETWORK);
		}
	}

	@Override
	public void onDestroy() {
		mContextDestroyed = true;
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList(SAVED_STATE_REQUEST_LIST, mRequestList);
	}

	@Override
	public void initAllMembers(Bundle savedInstanceState) {
		mContextDestroyed = false;
		mRequestManager = RequestManagerImpl.from(this);

		if (savedInstanceState != null) {
			mRequestList = savedInstanceState
					.getParcelableArrayList(SAVED_STATE_REQUEST_LIST);
		}
		if (mRequestList == null) {
			mRequestList = new ArrayList<Request>();
		}
	}

	@Override
	public void initLoader() {

	}

	public Request getInitialRequest() {
		return null;
	}

	@Override
	public final void launchRequest(Request request) {
		if (request != null) {
			if (Utility.isNetworkAvailable(this)) {
				mRequestManager.execute(request, this);
				mRequestList.add(request);

				View errorIndicatorLayout = getErrorIndicatorLayout();
				if (errorIndicatorLayout != null
						&& errorIndicatorLayout.getVisibility() == View.VISIBLE) {
					errorIndicatorLayout.setVisibility(View.INVISIBLE);
				}
			} else {
				handleException(EXCEPTION_TYPE_NETWORK);
			}
		}
	}

	@Override
	public final void onRequestConnectionError(Request arg0, int arg1) {
		if (mRequestList.contains(arg0)) {
			mRequestList.remove(arg0);
		}

		if (arg1 == -1) {
			handleException(EXCEPTION_TYPE_NETWORK);
		} else {
			handleException(EXCEPTION_TYPE_SERVER_RESPONSE_ERROR);
		}
	}

	@Override
	public final void onRequestCustomError(Request arg0, Bundle arg1) {
		if (mRequestList.contains(arg0)) {
			mRequestList.remove(arg0);
		}

		handleException(EXCEPTION_TYPE_CUSTOM_BASE);
	}

	@Override
	public final void onRequestDataError(Request arg0) {
		if (mRequestList.contains(arg0)) {
			mRequestList.remove(arg0);
		}

		handleException(EXCEPTION_TYPE_DATA);
	}

	@Override
	public final void onRequestFinished(Request arg0, Bundle arg1) {
		if (mContextDestroyed) {
			return;
		}

		if (mRequestList.contains(arg0)) {
			onLoadingIndicatorHide();
			initLoader();
			onRequestSucess(arg0, arg1);
			mRequestList.remove(arg0);
		}
	}

	@Override
	public final void handleException(int exceptionType) {
		if (mContextDestroyed) {
			return;
		}
		onLoadingIndicatorHide();
		onRequestError(exceptionType);
	}

	@Override
	public void onRequestSucess(Request request, Bundle bundle) {

	}

	@Override
	public void onRequestError(int exceptionType) {
		View errorIndicatorLayout = getErrorIndicatorLayout();
		TextView errorMsgTextView = getErrorMsgTextView();
		if (errorIndicatorLayout == null || errorMsgTextView == null) {
			return;
		}

		switch (exceptionType) {
		case EXCEPTION_TYPE_NETWORK:
			errorMsgTextView.setText("网络错误！");
			errorIndicatorLayout.setVisibility(View.VISIBLE);
			break;
		case EXCEPTION_TYPE_DATA:
			errorMsgTextView.setText("数据错误！");
			errorIndicatorLayout.setVisibility(View.VISIBLE);
			break;
		case EXCEPTION_TYPE_SERVER_RESPONSE_ERROR:
			errorMsgTextView.setText("服务器出错！");
			errorIndicatorLayout.setVisibility(View.VISIBLE);
			break;
		case EXCEPTION_TYPE_EMPTY_RESULT:
			errorMsgTextView.setText("暂无相关数据");
			errorIndicatorLayout.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

	@Override
	public View getErrorIndicatorLayout() {
		return null;
	}

	@Override
	public TextView getErrorMsgTextView() {
		return null;
	}

	@Override
	public View getLoadingIndicatorView() {
		return null;
	}

	@Override
	public boolean needShowLoadingIndicator() {
		return true;
	}

	@Override
	public final void onLoadingIndicatorShow() {
		View view = getLoadingIndicatorView();
		if (view != null) {
			view.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public final void onLoadingIndicatorHide() {
		View view = getLoadingIndicatorView();
		if (view != null) {
			view.setVisibility(View.GONE);
		}
	}
}
