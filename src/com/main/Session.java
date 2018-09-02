package com.main;

import java.util.ArrayList;
import java.util.Scanner;

import com.exceptions.*;

public class Session {
	static private BankHandler bank;
	static private Account account;
	static private User user;
	static private Scanner in;
	static private String input;
	static private boolean running;
	static private boolean userAuthenticated;
	
	public static void start(){
		bank = BankHandler.getInstance();
		running = true;
		System.out.println("Weclome to Dawson Banking Application");
		userAuthenticated = false;
		in = new Scanner(System.in);
		while(running) {
			if(userAuthenticated) {
				if(user instanceof Customer) {
					customerMenu();
				}
				else if(user instanceof Employee) {
					employeeMenu();
				}
				else {
					adminMenu();
				}
			}
			else {
				mainMenu();
			}
		}
	}
	private static void adminMenu() {
		input = in.nextLine();
		switch(input) {
		case "view applications" : {
			
		}
		}
	}
	private static void employeeMenu() {
		
	}
	private static void customerMenu() {
		input = in.nextLine();
		switch(input) {
		
		case "view accounts":{
			try {
				ArrayList<Account> accs = bank.viewAccounts((Customer)user);
				if(accs == null) {
					System.out.println("You have no active accounts, please apply for one");
				}
				System.out.println("Account #   |   Balance");
				for(Account acc : accs) {
					System.out.println(acc.getID() + "  |  " + acc.getBalance());
				}
			} catch (CustomerDoesNotExistException e) {
				System.out.println("ERROR: Your user account no longer exists!");
			}
			break;
		}
		
		case "select":{
			System.out.println("Please enter the account # of the account you are trying to access");
			String accountNum = in.nextLine();
			try {
				for(Account acc : bank.viewAccounts((Customer)(user))) {
					account = null;
					if(accountNum == acc.getID()) {
						account = acc;
						break;
					}
				}
				if(account == null) {
					System.out.println("ERROR: You do not have an account with that number!");
				}
			}
			catch(CustomerDoesNotExistException ex) {
				System.out.println("ERROR: Your user account no longer exists!");
			}
			break;
		}
		
		case "deposit" : {
			if(account == null) {
				System.out.println("Please select an account into which you want to deposit using the \"select\" keyword");
				String number = in.nextLine();
			}
			break;
		}
		case "transfer" : {
			if(account == null) {
				System.out.println("Please select an account into which you want to transfer from using the \"select\" keyword");
			}
			break;
		}
		case "withdraw" : {
			if(account == null) {
				System.out.println("Please select an account from which you want to make a withdrawl using the \"select\" keyword");
			}
		}
		case "apply" : {
			while(true) {
				System.out.println("Would you like to create a joint application?");
				input = in.nextLine();
				if(input.equalsIgnoreCase("yes")) {
					System.out.print("Partner USERNAME: ");
					String pName = in.nextLine();
					System.out.print("Partner PASSWORD: ");
					String pPass = in.nextLine();
					try {
						bank.apply((Customer)BankHandler.getInstance().authenticateUser(pName, pPass));
						break;
					}
					catch(UserAuthenticationException ex) {
						System.out.println("User Authentication ERROR: Partner login information is incorrect");
					}
					
				}
				else if(input.equalsIgnoreCase("no")) {
					bank.apply((Customer)user);
					System.out.println("An application has been submitted for review!");
					break;
				}
				else if(input.equalsIgnoreCase("return")) {
					break;
				}
				else {
					System.out.println("options include \"yes, no, return\"");
				}
			}
			break;
		}
		case "logout" : {
			user = null;
			userAuthenticated = false;
			break;
		}
		default : {
			System.out.println("Possible commands include \"deposit, withdraw, transfer, apply, logout\"");
			break;
		}
		
		}
	}
	private static void mainMenu() {
		input = in.nextLine();
		switch(input) {
			case "register" : {
				System.out.print("USERNAME: ");
				String username = in.nextLine();
				System.out.print("PASSWORD: ");
				String password = in.nextLine();
				try{
					bank.register(new Customer(username, password));
				}
				catch(UserAlreadyExistsException ex) {
					System.out.println("User Registration ERROR: username is taken!");
				}
				break;
			}
			case "login" : {
				System.out.print("USERNAME: ");
				String username = in.nextLine();
				System.out.print("PASSWORD: ");
				String password = in.nextLine();
				try{
					user = bank.authenticateUser(username, password);
					userAuthenticated = true;
					System.out.println("Welcome to Dawson Bank, " + user.getUserName() + "!");
				}
				catch(UserAuthenticationException ex) {
					System.out.println("User Authentication ERROR: username or password is incorrect");
				}
				break;
			}
			case "exit" : {
				BankHandler.deserialize();
				System.exit(1);
				break;
			}
			default : {
				System.out.println("Possible commands include \"register, login, exit\"");
				break;
			}
		}
	}
}
