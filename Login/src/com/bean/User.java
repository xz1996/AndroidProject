package com.bean;

/**
 * Created by jxnkx on 2015/12/11.
 */
public class User {
	
	private long   uid;
    private String userName;
    private String password;
    private String sex;
    
    

    public User(String userName, String password, String sex) {
    	this.userName = userName;
		this.password = password;
		this.sex = sex;
	}

	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

}
