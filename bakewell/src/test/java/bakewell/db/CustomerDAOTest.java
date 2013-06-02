package bakewell.db;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bakewell.beans.Customer;

import junit.framework.TestCase;

public class CustomerDAOTest extends TestCase {

	CustomerDAO cDAO = null;
	Customer testCustomer = null;
	Customer failCustomer = null;
	ArrayList<Customer> result = null;
	
	@Before
	protected void setUp() {
		cDAO = new CustomerDAO("jdbc:h2:file:src/test/resources/db/wfDBTest", "sa", "");
		testCustomer = new Customer("firstNameTest1", "lastNameTest1", "addressTest1", "1234567890", "test1@mail1.com", "testCompany1", "testPassword1");
		failCustomer = new Customer(); 
		result = new ArrayList<Customer>();
		
	}
	
	@After
	protected void tearDown() {
		cDAO = null;
		testCustomer = null;
		failCustomer = null;
		result = null;
	}
	
	@Test
	public void testCustomerDAOInsert_ShouldWork() {
		cDAO.insertCustomer(testCustomer);
		result = cDAO.selectCustomer(testCustomer);
		assertTrue(result.get(0).getFirstName().equals(testCustomer.getFirstName()));
		assertTrue(result.get(0).getLastName().equals(testCustomer.getLastName()));
		assertTrue(result.get(0).getAddress().equals(testCustomer.getAddress()));
		assertTrue(result.get(0).getTelNo().equals(testCustomer.getTelNo()));
		assertTrue(result.get(0).getMailAddress().equals(testCustomer.getMailAddress()));
		assertTrue(result.get(0).getCompany().equals(testCustomer.getCompany()));
		assertTrue(result.get(0).getPassword().equals(testCustomer.getPassword()));	
		
		cDAO.deleteCustomer(result.get(0));
	}
	
	@Test
	public void testCustomerDAOInsert_ShouldFail() {
		cDAO.insertCustomer(failCustomer);
		result = cDAO.selectCustomer(failCustomer);
		assert(result.size() == 0);
	}
	
	@Test
	public void testCustomerDAODelete_ShouldWork() {
		cDAO.insertCustomer(testCustomer);
		result = cDAO.selectCustomer(testCustomer);
		assert(result.size() == 1);
		cDAO.deleteCustomer(testCustomer);
		result = cDAO.selectCustomer(testCustomer);
		assert(result.size() == 0);
	}
	
	@Test
	public void testCustomerDAODelete_ShouldFail() {
		cDAO.insertCustomer(testCustomer);
		cDAO.deleteCustomer(failCustomer);
		result = cDAO.selectCustomer(testCustomer);
		assert(result.size() == 1);
		cDAO.deleteCustomer(testCustomer);
	}
	
	@Test
	public void testCustomerDAOSelect_ShouldWork() {
		cDAO.insertCustomer(testCustomer);
		cDAO.insertCustomer(testCustomer);
		Customer searchCustomerFirstName = new Customer("firstNameTest1", null, null, null, null, null, null);
		Customer searchCustomerLastName = new Customer(null, "lastNameTest1", null, null, null, null, null);
		Customer searchCustomerAddress = new Customer(null, null, "addressTest1", null, null, null, null);
		Customer searchCustomerTelNo = new Customer(null, null, null, "1234567890", null, null, null);
		Customer searchCustomerMailAddress = new Customer(null, null, null, null, "test1@mail1.com", null, null);
		Customer searchCustomerCompany = new Customer(null, null, null, null, null, "testCompany1", null);
		Customer searchCustomerPassword = new Customer(null, null, null, null, null, null, "testPassword1");
		
		result = cDAO.selectCustomer(searchCustomerFirstName);
		assert(result.size() == 2);
		assert(result.get(0).getFirstName().equals("firstNametest1"));
		
		result = cDAO.selectCustomer(searchCustomerLastName);
		assert(result.size() == 2);
		assert(result.get(0).getLastName().equals("lastNameTest1"));
		
		result = cDAO.selectCustomer(searchCustomerAddress);
		assert(result.size() == 2);
		assert(result.get(0).getAddress().equals("addressTest1"));
		
		result = cDAO.selectCustomer(searchCustomerTelNo);
		assert(result.size() == 2);
		assert(result.get(0).getTelNo().equals("1234567890"));
		
		result = cDAO.selectCustomer(searchCustomerMailAddress);
		assert(result.size() == 2);
		assert(result.get(0).getMailAddress().equals("test1@mail1.com"));
		
		result = cDAO.selectCustomer(searchCustomerCompany);
		assert(result.size() == 2);
		assert(result.get(0).getCompany().equals("testCompany1"));
		
		result = cDAO.selectCustomer(searchCustomerPassword);
		assert(result.size() == 2);
		assert(result.get(0).getPassword().equals("testPassword1"));
		
		cDAO.deleteCustomer(testCustomer);
	}
	
	@Test
	public void testCustomerDAOSelect_ShouldFail() {
		cDAO.insertCustomer(testCustomer);
		Customer searchCustomerFirstName = new Customer("firstNameTest2", null, null, null, null, null, null);
		Customer searchCustomerLastName = new Customer(null, "lastNameTest2", null, null, null, null, null);
		Customer searchCustomerAddress = new Customer(null, null, "addressTest2", null, null, null, null);
		Customer searchCustomerTelNo = new Customer(null, null, null, "12345678902", null, null, null);
		Customer searchCustomerMailAddress = new Customer(null, null, null, null, "test1@mail2.com", null, null);
		Customer searchCustomerCompany = new Customer(null, null, null, null, null, "testCompany2", null);
		Customer searchCustomerPassword = new Customer(null, null, null, null, null, null, "testPassword2");
		
		result = cDAO.selectCustomer(searchCustomerFirstName);
		assert(result.size() == 0);
		
		result = cDAO.selectCustomer(searchCustomerLastName);
		assert(result.size() == 0);
		
		result = cDAO.selectCustomer(searchCustomerAddress);
		assert(result.size() == 0);
		
		result = cDAO.selectCustomer(searchCustomerTelNo);
		assert(result.size() == 0);
		
		result = cDAO.selectCustomer(searchCustomerMailAddress);
		assert(result.size() == 0);
		
		result = cDAO.selectCustomer(searchCustomerCompany);
		assert(result.size() == 0);
		
		result = cDAO.selectCustomer(searchCustomerPassword);
		assert(result.size() == 0);
		
		cDAO.deleteCustomer(testCustomer);	
	}
	
	@Test
	public void testCustomerDAOUpdate_ShouldWork() {
		cDAO.insertCustomer(testCustomer);
		Customer updateCustomer = new Customer("firstNameTest2", "lastNameTest2", "addressTest2", "12345678900", "test1@mail2.com", "testCompany2", "testPassword2");
		cDAO.UpdateCustomer(updateCustomer, testCustomer);
		
		result = cDAO.selectCustomer(testCustomer);
		assert(result.size() == 0);
		
		result = cDAO.selectCustomer(updateCustomer);
		assert(result.size() == 1);
		
		cDAO.deleteCustomer(updateCustomer);
	}
	
	@Test
	public void testCustomerDAOUpdate_ShouldFail() {
		cDAO.insertCustomer(testCustomer);
		Customer updateCustomer = new Customer("firstNameTest2", "lastNameTest2", "addressTest2", "12345678900", "test1@mail2.com", "testCompany2", "testPassword2");
		cDAO.insertCustomer(updateCustomer);
		result = cDAO.selectCustomer(testCustomer);
		Customer updateCustomer2 = new Customer("firstNameTest2", "lastNameTest2", "addressTest2", "12345678900", "test1@mail2.com", "testCompany2", "testPassword2");
		updateCustomer2.setId(result.get(0).getId());
		cDAO.UpdateCustomer(updateCustomer2, updateCustomer);
		
		cDAO.selectCustomer(testCustomer);
		assert(result.size() == 1);
		cDAO.selectCustomer(updateCustomer);
		assert(result.size() == 1);
		cDAO.selectCustomer(updateCustomer2);
		assert(result.size() == 0);
		cDAO.deleteCustomer(testCustomer);
		cDAO.deleteCustomer(updateCustomer);
	}
}
