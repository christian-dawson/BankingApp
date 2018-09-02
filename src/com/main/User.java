package com.main;

import java.io.Serializable;

public abstract class User implements Serializable {
	
	private String userName;
	private String password;
	
	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	
	public boolean checkUserName(String userName) {
		if(this.userName.equals(userName)) {
			return true;
		}
		return false;
	}
	
	public boolean checkPassword(String password){
		if(this.password.equals(password)) {
			return true;
		}
		return false;
	}
	public String getUserName() {
		return userName;
	}
}
