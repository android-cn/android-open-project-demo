package com.example.zhy_baseadapterhelper;

import java.util.ArrayList;
import java.util.List;

public class Bean
{
	private String title;
	private String url;
	private String desc;
	private String phone;
	private String time;

	public Bean()
	{
	}

	public Bean(String url, String title, String desc, String phone, String time)
	{
		this.url = url;
		this.title = title;
		this.desc = desc;
		this.phone = phone;
		this.time = time;
	}

	public static List<Bean> beans = new ArrayList<Bean>();
	static
	{
		Bean bean = null;
		for (int i = 0; i < Images.imageThumbUrls.length; i++)
		{
			bean = new Bean(Images.imageThumbUrls[i], "标题 ：" + i, "这是一个描述 : "
					+ i, "10086" + i, "2014-12-12");
			beans.add(bean);
		}
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

}
