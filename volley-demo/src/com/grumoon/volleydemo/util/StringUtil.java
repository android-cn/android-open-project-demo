package com.grumoon.volleydemo.util;

public class StringUtil {
	
	public static boolean isEmpty(String str)
	{
		if(str==null||str.trim().length()==0)
		{
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public static String preUrl(String url)
	{
		if(url==null)
		{
			return null;
		}
		
		if(url.startsWith("http://")||url.startsWith("https://"))
		{
			return url;
		}
		else
		{
			return "http://"+url;
		}
	}

}
