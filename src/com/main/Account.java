package com.main;

import java.io.Serializable;
import java.util.ArrayList;

import com.exceptions.InsufficientFundsException;

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
	public void withdraw(int amount) throws InsufficientFundsException {
		if(balance-amount < 0) {
			throw new InsufficientFundsException();
		}
		balance -= amount;
	}
	public String toString() {
		String toReturn = "Owner(s) : ";
		for(Customer c : owners) {
			toReturn += c.getUserName() + " ";
		}
		toReturn += "| balance: " + balance;
		return toReturn;
	}
}
