package com.grumoon.volleydemo.custom;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

public class XmlRequest extends Request<XmlPullParser> {

	private final Listener<XmlPullParser> mListener;

	public XmlRequest(int method, String url, Listener<XmlPullParser> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		mListener = listener;
	}

	public XmlRequest(String url, Listener<XmlPullParser> listener, ErrorListener errorListener) {
		this(Method.GET, url, listener, errorListener);
	}

	@Override
	protected Response<XmlPullParser> parseNetworkResponse(NetworkResponse response) {
		try {
			String xmlString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser xmlPullParser = factory.newPullParser();
			xmlPullParser.setInput(new StringReader(xmlString));
			return Response.success(xmlPullParser, HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (XmlPullParserException e) {
			return Response.error(new ParseError(e));
		}
	}

	@Override
	protected void deliverResponse(XmlPullParser response) {
		mListener.onResponse(response);
	}

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        // self-defined user agent
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("User-Agent", "android-open-project-analysis/1.0");
        return headerMap;
    }
}
