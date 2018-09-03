package com.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.main.BankHandler;
import com.main.Customer;
import com.main.Session;

class CustomerTest {
	BankHandler bank = BankHandler.getInstance();
	Customer customer = new Customer("name", "pass");
	@Test
	void getUserNameTest() {
		Assert.assertEquals(customer.getUserName(), "name");
	}
	//void get

}
