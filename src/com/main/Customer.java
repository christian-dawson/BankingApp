package com.main;

import java.util.ArrayList;

public class Customer extends User {
	private ArrayList<Account> accounts;
	public Customer(String userName, String password) {
		super(userName, password);
		accounts = new ArrayList<>();
	}
	public ArrayList<Account> getAccounts(){
		return accounts;
	}
}
