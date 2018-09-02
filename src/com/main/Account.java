package com.main;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable {
	private int balance;
	private String ID;
	private ArrayList<Customer> owners;
	public Account(String ID, ArrayList<Customer> customers) {
		this.ID = ID;
		for(Customer customer : customers) {
			owners.add(customer);
		}
	}
	public String getID() {
		return ID;
	}
	public int getBalance() {
		return balance;
	}
	public void deposit(int amount) {
		balance += amount;
	}
	public void withdraw(int amount) throws IllegalArgumentException {
		if(balance-amount < 0) {
			throw new IllegalArgumentException();
		}
		balance -= amount;
	}
	public void transfer(int amount, Account account) throws IllegalArgumentException {
		withdraw(amount);
		account.deposit(amount);
	}
}
