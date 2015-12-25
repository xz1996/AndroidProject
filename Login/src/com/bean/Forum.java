package com.bean;

import java.io.Serializable;

public class Forum implements Serializable{
	public  static final long serialVersionUID=1L;
	private int imageid;
	private String Title;
	private String msg;
	private boolean checkStatus;
	public Forum(int imageid,String Title,String msg) {
		// TODO Auto-generated constructor stub
		this.imageid=imageid;
		this.Title=Title;
		this.msg=msg;
		this.checkStatus=false;
	}
	public int getImageid() {
		return imageid;
	}
	public void setImageid(int imageid) {
		this.imageid = imageid;
	}
	
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(boolean checkStatus) {
		this.checkStatus = checkStatus;
	}

}
