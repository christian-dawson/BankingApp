package com.main;

import java.util.List;

public class Employee extends User {
	public Employee(String userName, String password) {
		super(userName, password);
	}
	public List<User> viewUsers(){
		return BankHandler.getInstance().getUsers();
	}
}
