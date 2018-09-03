package com.main;

import java.io.Serializable;
import java.util.ArrayList;

public class Application implements Serializable {
	private ArrayList<Customer> applicants;
	private String ID;
	public Application(String ID, Customer c, Customer b) {
		this.ID = ID;
		applicants = new ArrayList<>();
		applicants.add(c);
		applicants.add(b);
	}
	public Application(String ID, Customer c) {
		this.ID = ID;
		System.out.println(ID);
		applicants = new ArrayList<>();
		applicants.add(c);
	}
	public String getID() {
		return ID;
	}
	public ArrayList<Customer> applicants(){
		return applicants;
	}
	public String toString() {
		String toReturn = "Applicants: ";
		for(Customer cust : applicants) {
			toReturn += cust.getUserName() + " ";
		}
		toReturn += "| ID: " + ID;
		return toReturn;
	}
}
