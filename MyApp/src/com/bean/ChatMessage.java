package com.bean;

public class ChatMessage {

	private String msg;
	private int who;
	public ChatMessage(String msg,int who) {
		// TODO Auto-generated constructor stub
		this.msg = msg;
		this.who = who;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getWho() {
		return who;
	}
	public void setWho(int who) {
		this.who = who;
	}

}
