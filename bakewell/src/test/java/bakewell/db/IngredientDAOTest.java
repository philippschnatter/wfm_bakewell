package bakewell.db;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bakewell.beans.Ingredient;

import junit.framework.TestCase;

public class IngredientDAOTest extends TestCase {

	IngredientDAO cDAO = null;
	Ingredient testIngredient = null;
	Ingredient failIngredient = null;
	ArrayList<Ingredient> result = null;
	
	@Before
	protected void setUp() {
		cDAO = new IngredientDAO("jdbc:h2:file:src/test/resources/db/wfDBTest", "sa", "");
		testIngredient = new Ingredient("butter", 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 10.0);
		failIngredient = new Ingredient(); 
		result = new ArrayList<Ingredient>();
		
	}
	
	@After
	protected void tearDown() {
		cDAO = null;
		testIngredient = null;
		failIngredient = null;
		result = null;
	}
	
	@Test
	public void testIngredientDAOInsert_ShouldWork() {
		Ingredient a = cDAO.insertIngredient(testIngredient);
		result = cDAO.selectIngredient(testIngredient);
		assertTrue(result.get(0).getName().equals(testIngredient.getName()));
		assertTrue(result.get(0).getGda_energy().equals(testIngredient.getGda_energy()));
		assertTrue(result.get(0).getGda_protein().equals(testIngredient.getGda_protein()));
		assertTrue(result.get(0).getGda_carbo().equals(testIngredient.getGda_carbo()));
		assertTrue(result.get(0).getGda_fat().equals(testIngredient.getGda_fat()));
		assertTrue(result.get(0).getGda_fiber().equals(testIngredient.getGda_fiber()));
		assertTrue(result.get(0).getGda_sodium().equals(testIngredient.getGda_sodium()));
		assertTrue(result.get(0).getPrice().equals(testIngredient.getPrice()));
		
		assertTrue(result.get(0).getName().equals(a.getName()));
		assertTrue(result.get(0).getGda_energy().equals(a.getGda_energy()));
		assertTrue(result.get(0).getGda_protein().equals(a.getGda_protein()));
		assertTrue(result.get(0).getGda_carbo().equals(a.getGda_carbo()));
		assertTrue(result.get(0).getGda_fat().equals(a.getGda_fat()));
		assertTrue(result.get(0).getGda_fiber().equals(a.getGda_fiber()));
		assertTrue(result.get(0).getGda_sodium().equals(a.getGda_sodium()));
		assertTrue(result.get(0).getPrice().equals(a.getPrice()));
		assert(a.getId() != null);
		
		cDAO.deleteIngredient(result.get(0));
	}
	
	@Test
	public void testIngredientDAOInsert_ShouldFail() {
		Ingredient a = null;
		a = cDAO.insertIngredient(failIngredient);
		result = cDAO.selectIngredient(failIngredient);
		assert(result.size() == 0);
		assert(a == null);
	}
	
	@Test
	public void testIngredientDAODelete_ShouldWork() {
		ArrayList<Ingredient> a = new ArrayList<Ingredient>();
		cDAO.insertIngredient(testIngredient);
		result = cDAO.selectIngredient(testIngredient);
		assert(result.size() == 1);
		a = cDAO.deleteIngredient(testIngredient);
		result = cDAO.selectIngredient(testIngredient);
		assert(result.size() == 0);
		
		assertTrue(a.get(0).getFirstName().equals(testIngredient.getFirstName()));
		assertTrue(a.get(0).getLastName().equals(testIngredient.getLastName()));
		assertTrue(a.get(0).getAddress().equals(testIngredient.getAddress()));
		assertTrue(a.get(0).getTelNo().equals(testIngredient.getTelNo()));
		assertTrue(a.get(0).getMailAddress().equals(testIngredient.getMailAddress()));
		assertTrue(a.get(0).getCompany().equals(testIngredient.getCompany()));
		assertTrue(a.get(0).getPassword().equals(testIngredient.getPassword()));	
		assert(a.get(0).getId() != null);
	}
	
	@Test
	public void testIngredientDAODelete_ShouldFail() {
		ArrayList<Ingredient> a = new ArrayList<Ingredient>();
		cDAO.insertIngredient(testIngredient);
		a = cDAO.deleteIngredient(failIngredient);
		result = cDAO.selectIngredient(testIngredient);
		assert(result.size() == 1);
		assert(a.isEmpty());
		cDAO.deleteIngredient(testIngredient);
	}
	
	@Test
	public void testIngredientDAOSelect_ShouldWork() {
		cDAO.insertIngredient(testIngredient);
		cDAO.insertIngredient(testIngredient);
		Ingredient searchIngredientFirstName = new Ingredient("firstNameTest1", null, null, null, null, null, null);
		Ingredient searchIngredientLastName = new Ingredient(null, "lastNameTest1", null, null, null, null, null);
		Ingredient searchIngredientAddress = new Ingredient(null, null, "addressTest1", null, null, null, null);
		Ingredient searchIngredientTelNo = new Ingredient(null, null, null, "1234567890", null, null, null);
		Ingredient searchIngredientMailAddress = new Ingredient(null, null, null, null, "test1@mail1.com", null, null);
		Ingredient searchIngredientCompany = new Ingredient(null, null, null, null, null, "testCompany1", null);
		Ingredient searchIngredientPassword = new Ingredient(null, null, null, null, null, null, "testPassword1");
		
		result = cDAO.selectIngredient(searchIngredientFirstName);
		assert(result.size() == 2);
		assert(result.get(0).getFirstName().equals("firstNametest1"));
		
		result = cDAO.selectIngredient(searchIngredientLastName);
		assert(result.size() == 2);
		assert(result.get(0).getLastName().equals("lastNameTest1"));
		
		result = cDAO.selectIngredient(searchIngredientAddress);
		assert(result.size() == 2);
		assert(result.get(0).getAddress().equals("addressTest1"));
		
		result = cDAO.selectIngredient(searchIngredientTelNo);
		assert(result.size() == 2);
		assert(result.get(0).getTelNo().equals("1234567890"));
		
		result = cDAO.selectIngredient(searchIngredientMailAddress);
		assert(result.size() == 2);
		assert(result.get(0).getMailAddress().equals("test1@mail1.com"));
		
		result = cDAO.selectIngredient(searchIngredientCompany);
		assert(result.size() == 2);
		assert(result.get(0).getCompany().equals("testCompany1"));
		
		result = cDAO.selectIngredient(searchIngredientPassword);
		assert(result.size() == 2);
		assert(result.get(0).getPassword().equals("testPassword1"));
		
		cDAO.deleteIngredient(testIngredient);
	}
	
	@Test
	public void testIngredientDAOSelect_ShouldFail() {
		cDAO.insertIngredient(testIngredient);
		Ingredient searchIngredientFirstName = new Ingredient("firstNameTest2", null, null, null, null, null, null);
		Ingredient searchIngredientLastName = new Ingredient(null, "lastNameTest2", null, null, null, null, null);
		Ingredient searchIngredientAddress = new Ingredient(null, null, "addressTest2", null, null, null, null);
		Ingredient searchIngredientTelNo = new Ingredient(null, null, null, "12345678902", null, null, null);
		Ingredient searchIngredientMailAddress = new Ingredient(null, null, null, null, "test1@mail2.com", null, null);
		Ingredient searchIngredientCompany = new Ingredient(null, null, null, null, null, "testCompany2", null);
		Ingredient searchIngredientPassword = new Ingredient(null, null, null, null, null, null, "testPassword2");
		
		result = cDAO.selectIngredient(searchIngredientFirstName);
		assert(result.size() == 0);
		
		result = cDAO.selectIngredient(searchIngredientLastName);
		assert(result.size() == 0);
		
		result = cDAO.selectIngredient(searchIngredientAddress);
		assert(result.size() == 0);
		
		result = cDAO.selectIngredient(searchIngredientTelNo);
		assert(result.size() == 0);
		
		result = cDAO.selectIngredient(searchIngredientMailAddress);
		assert(result.size() == 0);
		
		result = cDAO.selectIngredient(searchIngredientCompany);
		assert(result.size() == 0);
		
		result = cDAO.selectIngredient(searchIngredientPassword);
		assert(result.size() == 0);
		
		cDAO.deleteIngredient(testIngredient);	
	}
	
	@Test
	public void testIngredientDAOUpdate_ShouldWork() {
		ArrayList<Ingredient> a = new ArrayList<Ingredient>();
		cDAO.insertIngredient(testIngredient);
		Ingredient updateIngredient = new Ingredient("firstNameTest2", "lastNameTest2", "addressTest2", "12345678900", "test1@mail2.com", "testCompany2", "testPassword2");
		a = cDAO.UpdateIngredient(updateIngredient, testIngredient);
		
		result = cDAO.selectIngredient(testIngredient);
		assert(result.size() == 0);
		
		result = cDAO.selectIngredient(updateIngredient);
		assert(result.size() == 1);
		
		assertTrue(a.get(0).getFirstName().equals(updateIngredient.getFirstName()));
		assertTrue(a.get(0).getLastName().equals(updateIngredient.getLastName()));
		assertTrue(a.get(0).getAddress().equals(updateIngredient.getAddress()));
		assertTrue(a.get(0).getTelNo().equals(updateIngredient.getTelNo()));
		assertTrue(a.get(0).getMailAddress().equals(updateIngredient.getMailAddress()));
		assertTrue(a.get(0).getCompany().equals(updateIngredient.getCompany()));
		assertTrue(a.get(0).getPassword().equals(updateIngredient.getPassword()));
		assert(a.get(0).getId() != null);
		
		cDAO.deleteIngredient(updateIngredient);
	}
	
	@Test
	public void testIngredientDAOUpdate_ShouldFail() {
		ArrayList<Ingredient> a = new ArrayList<Ingredient>();
		cDAO.insertIngredient(testIngredient);
		Ingredient updateIngredient = new Ingredient("firstNameTest2", "lastNameTest2", "addressTest2", "12345678900", "test1@mail2.com", "testCompany2", "testPassword2");
		cDAO.insertIngredient(updateIngredient);
		result = cDAO.selectIngredient(testIngredient);
		Ingredient updateIngredient2 = new Ingredient("firstNameTest2", "lastNameTest2", "addressTest2", "12345678900", "test1@mail2.com", "testCompany2", "testPassword2");
		updateIngredient2.setId(result.get(0).getId());
		a = cDAO.UpdateIngredient(updateIngredient2, updateIngredient);
		assert(a.isEmpty());
		
		cDAO.selectIngredient(testIngredient);
		assert(result.size() == 1);
		cDAO.selectIngredient(updateIngredient);
		assert(result.size() == 1);
		cDAO.selectIngredient(updateIngredient2);
		assert(result.size() == 0);
		cDAO.deleteIngredient(testIngredient);
		cDAO.deleteIngredient(updateIngredient);
	}
}
