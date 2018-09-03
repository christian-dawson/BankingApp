package com.main;

import java.util.ArrayList;

public class Customer extends User {
	private ArrayList<Account> accounts;
	public Customer(String userName, String password) {
		super(userName, password);
		accounts = new ArrayList<>();
	}
	public void addAccount(Account account) {
		accounts.add(account);
	}
	public void removeAccount(Account account) {
		for(Account acc : accounts) {
			if(acc.equals(account)) {
				accounts.remove(account);
			}
		}
	}
	public ArrayList<Account> getAccounts(){
		return accounts;
	}
}
