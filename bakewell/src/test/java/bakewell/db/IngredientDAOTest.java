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
		testIngredient = new Ingredient("nameTest1", 0.1, 0.1, 0.1, 0.1);
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
		cDAO.insertIngredient(testIngredient);
		result = cDAO.selectIngredient(testIngredient);
		assertTrue(result.get(0).getName().equals(testIngredient.getName()));
		assertTrue(result.get(0).getAmount().equals(testIngredient.getAmount()));
		assertTrue(result.get(0).getFat().equals(testIngredient.getFat()));
		assertTrue(result.get(0).getSugar().equals(testIngredient.getSugar()));
		assertTrue(result.get(0).getCalories().equals(testIngredient.getCalories()));	
		
		cDAO.deleteIngredient(result.get(0));
	}
	
	@Test
	public void testIngredientDAOInsert_ShouldFail() {
		cDAO.insertIngredient(failIngredient);
		result = cDAO.selectIngredient(failIngredient);
		assert(result.size() == 0);
	}
	
	@Test
	public void testIngredientDAODelete_ShouldWork() {
		cDAO.insertIngredient(testIngredient);
		result = cDAO.selectIngredient(testIngredient);
		assert(result.size() == 1);
		cDAO.deleteIngredient(testIngredient);
		result = cDAO.selectIngredient(testIngredient);
		assert(result.size() == 0);
	}
	
	@Test
	public void testIngredientDAODelete_ShouldFail() {
		cDAO.insertIngredient(testIngredient);
		cDAO.deleteIngredient(failIngredient);
		result = cDAO.selectIngredient(testIngredient);
		assert(result.size() == 1);
		cDAO.deleteIngredient(testIngredient);
	}
	
	@Test
	public void testIngredientDAOSelect_ShouldWork() {
		cDAO.insertIngredient(testIngredient);
		cDAO.insertIngredient(testIngredient);
		Ingredient searchIngredientName = new Ingredient("nameTest1", null, null, null, null);
		Ingredient searchIngredientAmount = new Ingredient(null, 0.1, null, null, null);
		Ingredient searchIngredientFat = new Ingredient(null, null, 0.1, null, null);
		Ingredient searchIngredientSugar = new Ingredient(null, null, null, 0.1, null);
		Ingredient searchIngredientCalories = new Ingredient(null, null, null, null, 0.1);
		
		result = cDAO.selectIngredient(searchIngredientName);
		assert(result.size() == 2);
		assert(result.get(0).getName().equals("nameametest1"));
		
		result = cDAO.selectIngredient(searchIngredientAmount);
		assert(result.size() == 2);
		assert(result.get(0).getAmount().equals(0.1));
		
		result = cDAO.selectIngredient(searchIngredientFat);
		assert(result.size() == 2);
		assert(result.get(0).getFat().equals(0.1));
		
		result = cDAO.selectIngredient(searchIngredientSugar);
		assert(result.size() == 2);
		assert(result.get(0).getSugar().equals(0.1));
		
		result = cDAO.selectIngredient(searchIngredientCalories);
		assert(result.size() == 2);
		assert(result.get(0).getCalories().equals(0.1));
		
		cDAO.deleteIngredient(testIngredient);
	}
	
	@Test
	public void testIngredientDAOSelect_ShouldFail() {
		cDAO.insertIngredient(testIngredient);
		Ingredient searchIngredientName = new Ingredient("nameTest1", null, null, null, null);
		Ingredient searchIngredientAmount = new Ingredient(null, 0.1, null, null, null);
		Ingredient searchIngredientFat = new Ingredient(null, null, 0.1, null, null);
		Ingredient searchIngredientSugar = new Ingredient(null, null, null, 0.1, null);
		Ingredient searchIngredientCalories = new Ingredient(null, null, null, null, 0.1);
		
		result = cDAO.selectIngredient(searchIngredientName);
		assert(result.size() == 0);
		
		result = cDAO.selectIngredient(searchIngredientAmount);
		assert(result.size() == 0);
		
		result = cDAO.selectIngredient(searchIngredientFat);
		assert(result.size() == 0);
		
		result = cDAO.selectIngredient(searchIngredientSugar);
		assert(result.size() == 0);
		
		result = cDAO.selectIngredient(searchIngredientCalories);
		assert(result.size() == 0);
		
		cDAO.deleteIngredient(testIngredient);	
	}
	
	@Test
	public void testIngredientDAOUpdate_ShouldWork() {
		cDAO.insertIngredient(testIngredient);
		Ingredient updateIngredient = new Ingredient("nameTest2", 0.2, 0.2, 0.2, 0.2);
		cDAO.UpdateIngredient(updateIngredient, testIngredient);
		
		result = cDAO.selectIngredient(testIngredient);
		assert(result.size() == 0);
		
		result = cDAO.selectIngredient(updateIngredient);
		assert(result.size() == 1);
		
		cDAO.deleteIngredient(updateIngredient);
	}
	
	@Test
	public void testIngredientDAOUpdate_ShouldFail() {
		cDAO.insertIngredient(testIngredient);
		Ingredient updateIngredient = new Ingredient("nameTest2", 0.2, 0.2, 0.2, 0.2);
		cDAO.insertIngredient(updateIngredient);
		result = cDAO.selectIngredient(testIngredient);
		Ingredient updateIngredient2 = new Ingredient("nameTest2", 0.2, 0.2, 0.2, 0.2);
		updateIngredient2.setId(result.get(0).getId());
		cDAO.UpdateIngredient(updateIngredient2, updateIngredient);
		
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
