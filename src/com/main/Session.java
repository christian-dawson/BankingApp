package com.main;

import java.util.Scanner;

import com.exceptions.*;

public class Session {
	
	
	static private BankHandler bank;
	static private Account account;
	static private Application application;
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
			String appData = bank.viewApplications();
			if(appData == "") {
				System.out.println("There are no outstanding applications");
				break;
			}
			System.out.println(appData);
			break;
		}
		case "view accounts" : {
			String accData = bank.viewAccounts();
			if(accData == "") {
				System.out.println("There are no active accounts");
				break;
			}
			System.out.println(accData);
			break;
			
		}
		case "select application" : {
			System.out.println("Please enter the ID of the application you wish to select");
			String appID = in.nextLine();
			try {
				application = bank.selectApplication(appID);
			}
			catch(ApplicationNotFoundException ex) {
				System.out.println("The ID you entered is not a valid application ID");
			}
			break;
		}
		case "approve" : {
			if(application == null) {
				System.out.println("Please select an application you wish to approve");
			}
			else {
				bank.approveApplication(application);
				System.out.println("Application has been successfully approved!");
				application = null;
			}
			break;
		}
		case "deny" : {
			if(application == null) {
				System.out.println("Please select an application you wish to approve");
			}
			else {
				bank.denyApplication(application);
				System.out.println("Application has been successfully denied!");
				application = null;
			}
			break;
		}
		case "select account" : {
			
			break;
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
				String accs = bank.viewAccounts((Customer)user);
				if(accs == "") {
					System.out.println("You have no active accounts, please apply for one");
					break;
				}
				System.out.println(accs);
			} catch (CustomerDoesNotExistException e) {
				System.out.println("ERROR: Your user account no longer exists!");
			}
			break;
		}
		
		case "select":{
			System.out.println("Please enter the account # of the account you are trying to access");
			String accountNum = in.nextLine();
			try {
				account = bank.selectAccount((Customer)(user), accountNum);
			}
			catch(AccountNotFoundException ex) {
				System.out.println("ERROR: Invalid account #");
			}
			break;
		}
		
		case "deposit" : {
			if(account == null) {
				System.out.println("Please select an account into which you want to deposit using the \"select\" keyword");
			}
			else {
				System.out.println("Please enter how much money you wish to deposit");
				String number = in.nextLine();
				try {
					int amount = Integer.parseInt(number);
					bank.deposit(account, amount);
				}
				catch(Exception ex) {
					ex.printStackTrace();
					System.out.println("The amount should only contian numbers and should be a whole number");
				}
			}
			break;
		}
		case "transfer" : {
			if(account == null) {
				System.out.println("Please select an account into which you want to transfer from using the \"select\" keyword");
			}
			else {
				System.out.println("Please enter how much money you wish to transfer from this account");
				String number = in.nextLine();
				try {
					int amount = Integer.parseInt(number);
				}
				catch(Exception ex) {
					System.out.println("The amount should only contian numbers and should be a whole number");
				}
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
						bank.apply((Customer)user, (Customer)bank.authenticateUser(pName, pPass));
						break;
					}
					catch(UserAuthenticationException ex) {
						System.out.println("User Authentication ERROR: Partner login information is incorrect");
					}
					catch(CustomerSimilarityException ex) {
						System.out.println("The application must have seperate owners");
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
				System.out.print("TYPE: ");
				String type = in.nextLine();
				try{
					bank.register(username, password, type);
				}
				catch(UserAlreadyExistsException ex) {
					System.out.println("User Registration ERROR: username is taken!");
				}
				catch(InvalidUserTypeException ex){
					System.out.println("ERROR: valid user types include \"customer, employee, admin\"");
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
