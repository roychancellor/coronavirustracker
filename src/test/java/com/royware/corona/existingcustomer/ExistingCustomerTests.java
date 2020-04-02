/**
 * Tests existing customer functionality
 * 1. Correct login credentials produce an existing customer
 * 2. Creating a new customer with a username that exists in the database produces an error
 */
package com.royware.corona.existingcustomer;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.royware.corona.model.Customer;
import com.royware.corona.services.DatabaseService;
import com.royware.corona.services.LoginService;

public class ExistingCustomerTests {
	private Customer testCust;
	private DatabaseService ds;
	private LoginService ls;
	
	@Before
	public void beforeTest() {
		ds = new DatabaseService();
		testCust = new Customer();
		ls = new LoginService();
	}

	@Test
	public void testLoginCredentials() {
		//TEST 1: Valid credentials --> login
		String testUser = "twomack";
		String testPass = "password";
		int custId = ls.validateCredentials(testUser, testPass);
		testCust = ds.dbRetrieveCustomerById(custId);
		assertTrue("Valid credentials --> login", custId > 0);
		assertTrue("username should be twomack", testCust.getUsername().equals(testUser));
		assertNotNull(testCust);

		//TEST 2: Invalid credentials --> do not log in
		testUser = "unknown";
		testPass = "password";
		custId = ls.validateCredentials(testUser, testPass);
		assertTrue("Invalid credentials --> do not login", custId == 0);
		testCust = ds.dbRetrieveCustomerById(custId);
		assertNull(testCust);
	}
	
	@After
	public void afterTests() {
		ds.close();
	}
	
}
