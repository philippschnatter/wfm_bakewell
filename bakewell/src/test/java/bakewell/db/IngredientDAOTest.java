package bakewell.db;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bakewell.beans.Ingredient;
import bakewell.beans.Ingredient2Recipe;
import bakewell.beans.Ingredient2RecipeNonPersistent;

import junit.framework.TestCase;

public class IngredientDAOTest extends TestCase {

	IngredientDAO cDAO = null;
	Ingredient2RecipeDAO i2rDAO = null;
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
	public void testIngredientDAOinsertIngredient2RecipeNonPersistent_ShouldWork() {
		i2rDAO = new Ingredient2RecipeDAO("jdbc:h2:file:src/test/resources/db/wfDBTest", "sa", "");
		testIngredient = cDAO.insertIngredient(testIngredient);
		ArrayList<Ingredient2RecipeNonPersistent> list = new ArrayList<Ingredient2RecipeNonPersistent>();
		list.add(new Ingredient2RecipeNonPersistent(testIngredient.getName(), null, testIngredient.getId(), 26000.0));
		list.add(new Ingredient2RecipeNonPersistent(testIngredient.getName(), null, testIngredient.getId(), 26000.0));
		
		ArrayList<Ingredient2RecipeNonPersistent> res = cDAO.insertIngredient2RecipeNonPersistent(list);

		Ingredient2Recipe searchIngredient2Recipe = new Ingredient2Recipe(null, testIngredient.getId(), null);
		ArrayList<Ingredient2Recipe> resi2r = i2rDAO.selectIngredient2Recipe(searchIngredient2Recipe);
		
		assert(res.size() == 2);
		assert(resi2r.size() == 2);
	}
	
	@Test
	public void testIngredientDAOselectAllIngredient2RecipeNonPersistent_ShouldWork() {
		i2rDAO = new Ingredient2RecipeDAO("jdbc:h2:file:src/test/resources/db/wfDBTest", "sa", "");
		ArrayList<Ingredient2RecipeNonPersistent> res = cDAO.selectAllIngredient2RecipeNonPersistent();
		
		assertTrue(!res.isEmpty());
	}
	
	@Test
	public void testIngredientDAOselectAllIngredients_ShouldWork() {
		result = cDAO.selectAllIngredients();
		int i = result.size();
		cDAO.insertIngredient(testIngredient);
		cDAO.insertIngredient(testIngredient);
		cDAO.insertIngredient(testIngredient);
		cDAO.insertIngredient(testIngredient);
		testIngredient = cDAO.insertIngredient(testIngredient);
		result = cDAO.selectAllIngredients();
		assert(i == (result.size()-5));
		assert(result.get(0).getId().equals(1));
		assert(result.get(result.size()-1).getId().equals(testIngredient.getId()));
	}
	
	@Test
	public void testIngredientDAOselectAllIngredients_ShouldWork2() {
		result = cDAO.selectAllIngredients();
		int i = result.size();
		Ingredient testIngredient1 = cDAO.insertIngredient(testIngredient);
		Ingredient testIngredient2 = cDAO.insertIngredient(testIngredient);
		cDAO.deleteIngredient(testIngredient1);
		result = cDAO.selectAllIngredients();
		assert(i == (result.size()+1));
		assert(result.get(0).getId().equals(1));
		assert(result.get(result.size()-1).getId().equals(testIngredient2.getId()));
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
		
		assertTrue(a.get(0).getName().equals(testIngredient.getName()));
		assertTrue(a.get(0).getGda_energy().equals(testIngredient.getGda_energy()));
		assertTrue(a.get(0).getGda_protein().equals(testIngredient.getGda_protein()));
		assertTrue(a.get(0).getGda_carbo().equals(testIngredient.getGda_carbo()));
		assertTrue(a.get(0).getGda_fat().equals(testIngredient.getGda_fat()));
		assertTrue(a.get(0).getGda_fiber().equals(testIngredient.getGda_fiber()));
		assertTrue(a.get(0).getGda_sodium().equals(testIngredient.getGda_sodium()));
		assertTrue(a.get(0).getPrice().equals(testIngredient.getPrice()));	
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
		
		Ingredient searchIngredientName = new Ingredient("firstNameTest1", null, null, null, null, null, null, null);
		Ingredient searchIngredientGDAEnergy = new Ingredient(null, 10.0, null, null, null, null, null, null);
		Ingredient searchIngredientGDAProtein = new Ingredient(null, null, 10.0, null, null, null, null, null);
		Ingredient searchIngredientGDACarbo = new Ingredient(null, null, null, 10.0, null, null, null, null);
		Ingredient searchIngredientGDAFat = new Ingredient(null, null, null, null, 10.0, null, null, null);
		Ingredient searchIngredientGDAFiber = new Ingredient(null, null, null, null, null, 10.0, null, null);
		Ingredient searchIngredientGDASodium = new Ingredient(null, null, null, null, null, null, 10.0, null);
		Ingredient searchIngredientPrice = new Ingredient(null, null, null, null, null, null, null, 10.0);
		
		result = cDAO.selectIngredient(searchIngredientName);
		assert(result.size() == 2);
		assert(result.get(0).getName().equals("firstNameTest1"));
		
		result = cDAO.selectIngredient(searchIngredientGDAEnergy);
		assert(result.size() == 2);
		assert(result.get(0).getGda_energy().equals(10.0));
		
		result = cDAO.selectIngredient(searchIngredientGDAProtein);
		assert(result.size() == 2);
		assert(result.get(0).getGda_protein().equals(10.0));
		
		result = cDAO.selectIngredient(searchIngredientGDACarbo);
		assert(result.size() == 2);
		assert(result.get(0).getGda_carbo().equals(10.0));
		
		result = cDAO.selectIngredient(searchIngredientGDAFat);
		assert(result.size() == 2);
		assert(result.get(0).getGda_fat().equals(10.0));
		
		result = cDAO.selectIngredient(searchIngredientGDAFiber);
		assert(result.size() == 2);
		assert(result.get(0).getGda_fiber().equals(10.0));
		
		result = cDAO.selectIngredient(searchIngredientGDASodium);
		assert(result.size() == 2);
		assert(result.get(0).getGda_sodium().equals(10.0));
		
		result = cDAO.selectIngredient(searchIngredientPrice);
		assert(result.size() == 2);
		assert(result.get(0).getPrice().equals(10.0));
		cDAO.deleteIngredient(testIngredient);
	}
	
	@Test
	public void testIngredientDAOSelect_ShouldFail() {
		cDAO.insertIngredient(testIngredient);
		Ingredient searchIngredientName = new Ingredient("firstNameTest1", null, null, null, null, null, null, null);
		Ingredient searchIngredientGDAEnergy = new Ingredient(null, 10.0, null, null, null, null, null, null);
		Ingredient searchIngredientGDAProtein = new Ingredient(null, null, 10.0, null, null, null, null, null);
		Ingredient searchIngredientGDACarbo = new Ingredient(null, null, null, 10.0, null, null, null, null);
		Ingredient searchIngredientGDAFat = new Ingredient(null, null, null, null, 10.0, null, null, null);
		Ingredient searchIngredientGDAFiber = new Ingredient(null, null, null, null, null, 10.0, null, null);
		Ingredient searchIngredientGDASodium = new Ingredient(null, null, null, null, null, null, 10.0, null);
		Ingredient searchIngredientPrice = new Ingredient(null, null, null, null, null, null, null, 10.0);
		
		result = cDAO.selectIngredient(searchIngredientName);
		assert(result.size() == 0);
		
		result = cDAO.selectIngredient(searchIngredientGDAEnergy);
		assert(result.size() == 0);
		
		result = cDAO.selectIngredient(searchIngredientGDAProtein);
		assert(result.size() == 0);
		
		result = cDAO.selectIngredient(searchIngredientGDACarbo);
		assert(result.size() == 0);
		
		result = cDAO.selectIngredient(searchIngredientGDAFat);
		assert(result.size() == 0);
		
		result = cDAO.selectIngredient(searchIngredientGDAFiber);
		assert(result.size() == 0);
		
		result = cDAO.selectIngredient(searchIngredientGDASodium);
		assert(result.size() == 0);
		
		result = cDAO.selectIngredient(searchIngredientPrice);
		assert(result.size() == 0);
		
		cDAO.deleteIngredient(testIngredient);	
	}
	
	@Test
	public void testIngredientDAOUpdate_ShouldWork() {
		ArrayList<Ingredient> a = new ArrayList<Ingredient>();
		cDAO.insertIngredient(testIngredient);
		Ingredient updateIngredient = new Ingredient("butter2", 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 20.0);
		a = cDAO.UpdateIngredient(updateIngredient, testIngredient);
		
		result = cDAO.selectIngredient(testIngredient);
		assert(result.size() == 0);
		
		result = cDAO.selectIngredient(updateIngredient);
		assert(result.size() == 1);
		
		assertTrue(a.get(0).getName().equals(updateIngredient.getName()));
		assertTrue(a.get(0).getGda_energy().equals(updateIngredient.getGda_energy()));
		assertTrue(a.get(0).getGda_protein().equals(updateIngredient.getGda_protein()));
		assertTrue(a.get(0).getGda_carbo().equals(updateIngredient.getGda_carbo()));
		assertTrue(a.get(0).getGda_fat().equals(updateIngredient.getGda_fat()));
		assertTrue(a.get(0).getGda_fiber().equals(updateIngredient.getGda_fiber()));
		assertTrue(a.get(0).getGda_sodium().equals(updateIngredient.getGda_sodium()));
		assertTrue(a.get(0).getPrice().equals(updateIngredient.getPrice()));
		assert(a.get(0).getId() != null);
		
		cDAO.deleteIngredient(updateIngredient);
	}
	
	@Test
	public void testIngredientDAOUpdate_ShouldFail() {
		ArrayList<Ingredient> a = new ArrayList<Ingredient>();
		cDAO.insertIngredient(testIngredient);
		Ingredient updateIngredient = new Ingredient("butter2", 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 20.0);
		cDAO.insertIngredient(updateIngredient);
		result = cDAO.selectIngredient(testIngredient);
		Ingredient updateIngredient2 = new Ingredient("butter2", 2.0, 2.0, 2.0, 2.0, 2.0, 2.0, 20.0);
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
