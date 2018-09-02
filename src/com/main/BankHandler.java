package com.main;

import static org.hamcrest.CoreMatchers.containsString;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.exceptions.*;

public class BankHandler implements Serializable{
	
	private static final String FILE_NAME = "data.txt";
	
	private List<Application> applications;
	private List<Account> accounts;
	private List<User> users;
	
	static private String uniqueAppID;
	static private String uniqueAccID;
	
	private static BankHandler handler;
	
	private BankHandler() {
		uniqueAppID = "000000";
		applications = new ArrayList<>();
		accounts = new ArrayList<>();
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
	public void apply(Customer customer, Customer ... partner) {
		receiveApplication(new Application(uniqueAppID(),customer, partner));
	}
	public ArrayList<Account> viewAccounts(Customer customer) throws CustomerDoesNotExistException{
		for(User user : users) {
			if(user.checkUserName(customer.getUserName())){
				return customer.getAccounts();
			}
		}
		throw new CustomerDoesNotExistException();
	}
	
	public void register(User requestUser) throws UserAlreadyExistsException {
		for(User user : users) {
			if(user.checkUserName(requestUser.getUserName())) {
				throw new UserAlreadyExistsException();
			}
		}
		users.add(requestUser);
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
		accounts.add(account);
	}
	public void removeAccount(Account account) {
		accounts.remove(account);
	}
	public void receiveApplication(Application application) {
		applications.add(application);
	}
	public void approveApplication(Application application) {
		addAccount(new Account(uniqueAccID(), application.applicants()));
	}
	public void denyApplication(Application application) {
		applications.remove(application);
	}
	public List<Account> getAccounts(){
		return accounts;
	}
	public List<Application> getApplications(){
		return applications;
	}
	public List<User> getUsers(){
		return users;
	}
	static private String uniqueAppID() {
		String toReturn = ((Integer.parseInt(uniqueAppID)+1) + "");
		for(int i = 1; i <= 10; i++) {
			if(uniqueAppID.length() < i) {
				uniqueAppID = "0"+toReturn;
			}
		}
		return uniqueAccID;
	}
	static private String uniqueAccID() {
		String toReturn = ((Integer.parseInt(uniqueAccID)+1) + "");
		for(int i = 1; i <= 10; i++) {
			if(uniqueAccID.length() < i) {
				uniqueAccID = "0"+toReturn;
			}
		}
		return uniqueAccID;
	}
}
