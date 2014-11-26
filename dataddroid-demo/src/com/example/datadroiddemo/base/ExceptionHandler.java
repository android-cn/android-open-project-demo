package com.example.datadroiddemo.base;

public interface ExceptionHandler {
	public static final int EXCEPTION_NONE = 0;
	public static final int EXCEPTION_TYPE_NETWORK = 10;
	public static final int EXCEPTION_TYPE_DATA = 20;
	public static final int EXCEPTION_TYPE_AUTH = 30;
	public static final int EXCEPTION_TYPE_SERVER_RESPONSE_ERROR = 40;
	public static final int EXCEPTION_TYPE_EMPTY_RESULT = 50;

	public static final int EXCEPTION_TYPE_CUSTOM_BASE = 10000;

	public void handleException(int exceptionType);
}
