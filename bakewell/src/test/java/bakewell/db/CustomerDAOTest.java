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
		Customer a = cDAO.insertCustomer(testCustomer);
		result = cDAO.selectCustomer(testCustomer);
		assertTrue(result.get(0).getFirstName().equals(testCustomer.getFirstName()));
		assertTrue(result.get(0).getLastName().equals(testCustomer.getLastName()));
		assertTrue(result.get(0).getAddress().equals(testCustomer.getAddress()));
		assertTrue(result.get(0).getTelNo().equals(testCustomer.getTelNo()));
		assertTrue(result.get(0).getMailAddress().equals(testCustomer.getMailAddress()));
		assertTrue(result.get(0).getCompany().equals(testCustomer.getCompany()));
		assertTrue(result.get(0).getPassword().equals(testCustomer.getPassword()));	
		
		assertTrue(result.get(0).getFirstName().equals(a.getFirstName()));
		assertTrue(result.get(0).getLastName().equals(a.getLastName()));
		assertTrue(result.get(0).getAddress().equals(a.getAddress()));
		assertTrue(result.get(0).getTelNo().equals(a.getTelNo()));
		assertTrue(result.get(0).getMailAddress().equals(a.getMailAddress()));
		assertTrue(result.get(0).getCompany().equals(a.getCompany()));
		assertTrue(result.get(0).getPassword().equals(a.getPassword()));
		assert(a.getId() != null);
		
		cDAO.deleteCustomer(result.get(0));
	}
	
	@Test
	public void testCustomerDAOInsert_ShouldFail() {
		Customer a = null;
		a = cDAO.insertCustomer(failCustomer);
		result = cDAO.selectCustomer(failCustomer);
		assert(result.size() == 0);
		assert(a == null);
		cDAO.deleteCustomer(a);
	}
	
	@Test
	public void testCustomerDAODelete_ShouldWork() {
		ArrayList<Customer> a = new ArrayList<Customer>();
		cDAO.insertCustomer(testCustomer);
		result = cDAO.selectCustomer(testCustomer);
		assert(result.size() == 1);
		a = cDAO.deleteCustomer(testCustomer);
		result = cDAO.selectCustomer(testCustomer);
		assert(result.size() == 0);
		
		assertTrue(a.get(0).getFirstName().equals(testCustomer.getFirstName()));
		assertTrue(a.get(0).getLastName().equals(testCustomer.getLastName()));
		assertTrue(a.get(0).getAddress().equals(testCustomer.getAddress()));
		assertTrue(a.get(0).getTelNo().equals(testCustomer.getTelNo()));
		assertTrue(a.get(0).getMailAddress().equals(testCustomer.getMailAddress()));
		assertTrue(a.get(0).getCompany().equals(testCustomer.getCompany()));
		assertTrue(a.get(0).getPassword().equals(testCustomer.getPassword()));	
		assert(a.get(0).getId() != null);
	}
	
	@Test
	public void testCustomerDAODelete_ShouldFail() {
		ArrayList<Customer> a = new ArrayList<Customer>();
		cDAO.insertCustomer(testCustomer);
		a = cDAO.deleteCustomer(failCustomer);
		result = cDAO.selectCustomer(testCustomer);
		assert(result.size() == 1);
		assert(a.isEmpty());
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
		ArrayList<Customer> a = new ArrayList<Customer>();
		cDAO.insertCustomer(testCustomer);
		Customer updateCustomer = new Customer("firstNameTest2", "lastNameTest2", "addressTest2", "12345678900", "test1@mail2.com", "testCompany2", "testPassword2");
		a = cDAO.UpdateCustomer(updateCustomer, testCustomer);
		
		result = cDAO.selectCustomer(testCustomer);
		assert(result.size() == 0);
		
		result = cDAO.selectCustomer(updateCustomer);
		assert(result.size() == 1);
		
		assertTrue(a.get(0).getFirstName().equals(updateCustomer.getFirstName()));
		assertTrue(a.get(0).getLastName().equals(updateCustomer.getLastName()));
		assertTrue(a.get(0).getAddress().equals(updateCustomer.getAddress()));
		assertTrue(a.get(0).getTelNo().equals(updateCustomer.getTelNo()));
		assertTrue(a.get(0).getMailAddress().equals(updateCustomer.getMailAddress()));
		assertTrue(a.get(0).getCompany().equals(updateCustomer.getCompany()));
		assertTrue(a.get(0).getPassword().equals(updateCustomer.getPassword()));
		assert(a.get(0).getId() != null);
		
		cDAO.deleteCustomer(updateCustomer);
	}
	
	@Test
	public void testCustomerDAOUpdate_ShouldFail() {
		ArrayList<Customer> a = new ArrayList<Customer>();
		cDAO.insertCustomer(testCustomer);
		Customer updateCustomer = new Customer("firstNameTest2", "lastNameTest2", "addressTest2", "12345678900", "test1@mail2.com", "testCompany2", "testPassword2");
		cDAO.insertCustomer(updateCustomer);
		result = cDAO.selectCustomer(testCustomer);
		Customer updateCustomer2 = new Customer("firstNameTest2", "lastNameTest2", "addressTest2", "12345678900", "test1@mail2.com", "testCompany2", "testPassword2");
		updateCustomer2.setId(result.get(0).getId());
		a = cDAO.UpdateCustomer(updateCustomer2, updateCustomer);
		assert(a.isEmpty());
		
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
