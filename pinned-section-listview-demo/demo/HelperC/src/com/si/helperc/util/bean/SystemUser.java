package com.si.helperc.util.bean;

import java.io.Serializable;

import android.graphics.Bitmap;

public class SystemUser implements Serializable{
	
	private String contactName;
	
	private String  contactNum;
	
	private String  contactEmail;
	
	private Bitmap headIcon;
	

	public Bitmap getHeadIcon() {
		return headIcon;
	}

	public void setHeadIcon(Bitmap headIcon) {
		this.headIcon = headIcon;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactNum() {
		return contactNum;
	}

	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	
	

}
