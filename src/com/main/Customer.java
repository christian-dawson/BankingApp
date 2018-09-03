package com.main;

import java.util.ArrayList;

import com.exceptions.AccountNotFoundException;

public class Customer extends User {
	private ArrayList<Account> accounts;
	public Customer(String userName, String password) {
		super(userName, password);
		accounts = new ArrayList<>();
	}
	public void addAccount(Account account) {
		accounts.add(account);
	}
	public void removeAccount(Account account) throws AccountNotFoundException {
		if(accounts.contains(account)) {
			accounts.remove(account);
		}
		else {
			throw new AccountNotFoundException();
		}
	}
	public ArrayList<Account> getAccounts(){
		return accounts;
	}
}
