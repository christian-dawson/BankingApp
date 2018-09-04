package com.main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import com.exceptions.*;

public class BankHandler implements Serializable{
	
	private static final String FILE_NAME = "data.txt";
	
	private ArrayList<Application> applications;
	private ArrayList<User> users;
	
	private String uniqueAppID;
	private String uniqueAccID;
	
	private static BankHandler handler;
	
	//BankHandler Setup
	private BankHandler() {
		uniqueAppID = "0";
		uniqueAccID = "0";
		applications = new ArrayList<>();
		users = new ArrayList<>();
	}
	public static BankHandler getInstance() {
		if(handler == null) {
			serialize();
		}
		return handler;
	}
	public static void serialize() {
		try {
			FileInputStream in = new FileInputStream(FILE_NAME);
			ObjectInputStream ob = new ObjectInputStream(in);
			handler = ((BankHandler)ob.readObject());
			ob.close();
		}
		catch(Exception ex) {
			handler = new BankHandler();
		}
	}
	public static void deserialize() {
		try{
			FileOutputStream out = new FileOutputStream(FILE_NAME);
			ObjectOutputStream ob = new ObjectOutputStream(out);
			ob.writeObject(handler);
			ob.close();
		}
		catch(Exception ex) {
			System.out.println("writing error");
		}
	}
	
	
	//Interactive functions
	
	public void apply(Customer a, Customer b) throws CustomerSimilarityException {
		if(a == b) {
			throw new CustomerSimilarityException();
		}
		applications.add(new Application(uniqueAppID(),a,b));
	}
	public void apply(Customer a) {
		applications.add(new Application(uniqueAppID(),a));
	}
	public String viewAccounts(Customer customer) throws CustomerDoesNotExistException{
		String toReturn = "";
		if(!users.contains(customer)) {
			throw new CustomerDoesNotExistException();
		}
		for(User user : users) {
			if(user.checkUserName(customer.getUserName())){
				for(Account acc : ((Customer)user).getAccounts()) {
					toReturn += "\n" + acc.toString();
				}
			}
		}
		return toReturn;
	}
	public Application selectApplication(String ID) throws ApplicationNotFoundException {
		for(Application app : applications) {
			if(app.getID().equals(ID)) {
				return app;
			}
		}
		throw new ApplicationNotFoundException();
	}
	
	public void register(String name, String pass, String type) throws UserAlreadyExistsException, InvalidUserTypeException {
		User toAdd;
		switch(type.toLowerCase()) {
			case "customer" : {
				toAdd = new Customer(name, pass);
				break;
			}
			case "employee" : {
				toAdd = new Employee(name, pass);
				break;
			}
			case "admin" : {
				toAdd = new Admin(name, pass);
				break;
			}
			default : {
				throw new InvalidUserTypeException();
			}
		}
		for(User user : users) {
			if(user.checkUserName(toAdd.getUserName())) {
				throw new UserAlreadyExistsException();
			}
		}
		users.add(toAdd);
	}
	public User authenticateUser(String userName, String password) throws UserAuthenticationException {
		for(User user : users) {
			if(user.checkUserName(userName)) {
				if(user.checkPassword(password)) {
					return user;
				}
			}
		}
		throw new UserAuthenticationException();
	}
	public void unregister(User user) {
		users.remove(user);
	}
	public void addAccount(Account account) {
		for(Customer customer : account.getOwners()) {
			customer.addAccount(account);
		}
	}
	public void removeAccount(Account account) throws AccountNotFoundException {
		for(Customer customer : account.getOwners()) {
			customer.removeAccount(account);
		}
	}
	public void removeUser(String userName) throws UserNotFoundException {
		boolean fail = true;
		for(User user : users) {
			if(userName.equals(user.getUserName())) {
				users.remove(user);
				fail = false;
			}
		}
		if(fail) {
			throw new UserNotFoundException();
		}
	}
	public void approveApplication(Application application) {
		addAccount(new Account(uniqueAccID(), application.applicants()));
		applications.remove(application);
	}
	public void denyApplication(Application application) {
		applications.remove(application);
	}
	public void deposit(Account account, int amount) throws IllegalArgumentException {
		account.deposit(amount);
	}
	public void withdraw(Account account, int amount) throws InsufficientFundsException {
		account.withdraw(amount);
	}
	public void transfer(Account from, Account to, int amount) throws InsufficientFundsException {
		from.withdraw(amount);
		to.deposit(amount);
		
	}
	public Account selectAccount(String ID) throws AccountNotFoundException{
		for(User user : users) {
			if(user instanceof Customer) {
				for(Account acc : ((Customer)user).getAccounts()) {
					if(acc.getID().equals(ID)) {
						return acc;
					}
				}
			}
		}
		throw new AccountNotFoundException();
	}
	public Account selectAccount(Customer customer, String ID) throws AccountNotFoundException {
		for(Account acc : customer.getAccounts()) {
			if(acc.getID().equals(ID)) {
				return acc;
			}
		}
		throw new AccountNotFoundException();
	}
	public String viewApplications() {
		String toReturn = "";
		for(Application app : applications) {
			toReturn = toReturn.concat(app.toString() + "\n");
		}
		return toReturn;
	}
	public String viewAccounts() {
		String toReturn = "";
		for(User user : users) {
			if(user instanceof Customer) {
				for(Account acc : ((Customer)user).getAccounts()) {
					toReturn = toReturn.concat(acc.toString() + "\n");
				}
			}
		}
		return toReturn;
	}
	private String uniqueAppID() {
		String toReturn = ((Integer.parseInt(uniqueAppID)+1) + "");
		for(int i = 1; i <= 10; i++) {
			if(toReturn.length() < i) {
				toReturn = "0"+toReturn;
			}
		}
		uniqueAppID = toReturn;
		return uniqueAppID;
	}
	private String uniqueAccID() {
		String toReturn = ((Integer.parseInt(uniqueAccID)+1) + "");
		for(int i = 1; i <= 10; i++) {
			if(toReturn.length() < i) {
				toReturn = "0"+toReturn;
			}
		}
		uniqueAccID = toReturn;
		return uniqueAccID;
	}
	
	public ArrayList<User> getUsers(){
		return users;
	}
	public ArrayList<Application> getApplications(){
		return applications;
	}
}
