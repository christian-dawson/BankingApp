package com.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.exceptions.AccountNotFoundException;
import com.main.Account;
import com.main.BankHandler;
import com.main.Customer;
import com.main.Session;

class CustomerTest {
	BankHandler bank = BankHandler.getInstance();
	Customer customer = new Customer("name", "pass");
	ArrayList<Customer> toAdd;
	Account acc;
	
	@Before
	void setUp() {
		toAdd = new ArrayList<>();
		toAdd.add(customer);
		acc = new Account("1023421", toAdd);
	}
	@Test 
	void checkUserNameTest(){
		assertTrue(customer.checkUserName("name"));
		assertFalse(customer.checkUserName("not_name"));
	}
	@Test
	void checkPasswordTest() {
		assertTrue(customer.checkPassword("pass"));
		assertFalse(customer.checkPassword("not_pass"));
	}
	@Test
	void getUserNameTest() {
		assertEquals(customer.getUserName(), "name");
	}
	@Test
	void addAccount() {
		customer.addAccount(acc);
		assertEquals(acc, customer.getAccounts().get(0));
	}
	@Test
	void removeAccount() {
		customer.addAccount(acc);
		try {
			customer.removeAccount(acc);
		} catch (AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(0, customer.getAccounts().size());
	}
	
	//void get

}
