package com.main;

import java.io.Serializable;
import java.util.ArrayList;

public class Application implements Serializable {
	private ArrayList<Customer> applicants;
	private String ID;
	public Application(String ID, Customer c, Customer ... customers) {
		this.ID = ID;
		applicants = new ArrayList<>();
		applicants.add(c);
		for(Customer customer : customers) {
			applicants.add(customer);
		}
	}
	public String getID() {
		return ID;
	}
	public ArrayList<Customer> applicants(){
		return applicants;
	}
}
