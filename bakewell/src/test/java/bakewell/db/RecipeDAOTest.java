package bakewell.db;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bakewell.beans.Ingredient2Recipe;
import bakewell.beans.Recipe;

import junit.framework.TestCase;

public class RecipeDAOTest extends TestCase {

	RecipeDAO rDAO = null;
	Ingredient2RecipeDAO i2rDAO = null;
	Recipe testRecipe = null;
	Recipe failRecipe = null;
	ArrayList<Recipe> result = null;
	
	@Before
	protected void setUp() {
		rDAO = new RecipeDAO("jdbc:h2:file:src/test/resources/db/wfDBTest", "sa", "");
		testRecipe = new Recipe("name1", "description1", 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 10.0);
		failRecipe = new Recipe(); 
		result = new ArrayList<Recipe>();
		
	}
	
	@After
	protected void tearDown() {
		rDAO = null;
		testRecipe = null;
		failRecipe = null;
		result = null;
	}
	
	@Test
	public void testRecipeDAOInsertNewRecipe_ShouldWork() {
		i2rDAO = new Ingredient2RecipeDAO("jdbc:h2:file:src/test/resources/db/wfDBTest", "sa", "");
		ArrayList<Ingredient2Recipe> list = new ArrayList<Ingredient2Recipe>();
		list.add(new Ingredient2Recipe(null, null, 25000.0));
		list.add(new Ingredient2Recipe(null, null, 25000.0));
		result = rDAO.insertNewRecipe(testRecipe, list);
		assert(result.size() == 1);
		
		Ingredient2Recipe searchI2r = new Ingredient2Recipe(result.get(0).getId(), null, null);
		ArrayList<Ingredient2Recipe> list2 = new ArrayList<Ingredient2Recipe>();
		list2 = i2rDAO.selectIngredient2Recipe(searchI2r);
		assert(list2.size() == 2);
		assert(list2.get(0).getId().equals(result.get(0).getId()));
		assert(list2.get(1).getId().equals(result.get(0).getId()));
		assert(list2.get(0).getAmount().equals(25000.0));
		assert(list2.get(0).getAmount().equals(list.get(1).getAmount()));
	}
	
	public void testRecipeDAOInsertNewRecipe_ShouldWork2() {
		i2rDAO = new Ingredient2RecipeDAO("jdbc:h2:file:src/test/resources/db/wfDBTest", "sa", "");
		ArrayList<Ingredient2Recipe> list = new ArrayList<Ingredient2Recipe>();
		list.add(new Ingredient2Recipe(null, null, 25000.0));
		list.add(new Ingredient2Recipe(null, null, 25000.0));
		testRecipe = rDAO.insertRecipe(testRecipe);
		result = rDAO.insertNewRecipe(testRecipe, list);
		assert(result.size() == 1);
		
		Ingredient2Recipe searchI2r = new Ingredient2Recipe(result.get(0).getId(), null, null);
		ArrayList<Ingredient2Recipe> list2 = new ArrayList<Ingredient2Recipe>();
		list2 = i2rDAO.selectIngredient2Recipe(searchI2r);
		assert(list2.size() == 2);
		assert(list2.get(0).getId().equals(result.get(0).getId()));
		assert(list2.get(1).getId().equals(result.get(0).getId()));
		assert(list2.get(0).getAmount().equals(25000.0));
		assert(list2.get(0).getAmount().equals(list.get(1).getAmount()));
	}
	
	@Test
	public void testRecipeDAOInsert_ShouldWork() {
		Recipe a = rDAO.insertRecipe(testRecipe);
		result = rDAO.selectRecipe(testRecipe);
		assertTrue(result.get(0).getName().equals(testRecipe.getName()));
		assertTrue(result.get(0).getDescription().equals(testRecipe.getDescription()));
		assertTrue(result.get(0).getAllgda_energy().equals(testRecipe.getAllgda_energy()));
		assertTrue(result.get(0).getAllgda_protein().equals(testRecipe.getAllgda_protein()));
		assertTrue(result.get(0).getAllgda_carbo().equals(testRecipe.getAllgda_carbo()));
		assertTrue(result.get(0).getAllgda_fat().equals(testRecipe.getAllgda_fat()));
		assertTrue(result.get(0).getAllgda_fiber().equals(testRecipe.getAllgda_fiber()));	
		assertTrue(result.get(0).getAllgda_sodium().equals(testRecipe.getAllgda_sodium()));
		assertTrue(result.get(0).getTotalprice().equals(testRecipe.getTotalprice()));
		
		assertTrue(result.get(0).getName().equals(a.getName()));
		assertTrue(result.get(0).getDescription().equals(a.getDescription()));
		assertTrue(result.get(0).getAllgda_energy().equals(a.getAllgda_energy()));
		assertTrue(result.get(0).getAllgda_protein().equals(a.getAllgda_protein()));
		assertTrue(result.get(0).getAllgda_carbo().equals(a.getAllgda_carbo()));
		assertTrue(result.get(0).getAllgda_fat().equals(a.getAllgda_fat()));
		assertTrue(result.get(0).getAllgda_fiber().equals(a.getAllgda_fiber()));	
		assertTrue(result.get(0).getAllgda_sodium().equals(a.getAllgda_sodium()));
		assertTrue(result.get(0).getTotalprice().equals(a.getTotalprice()));
		assert(a.getId() != null);
		
		rDAO.deleteRecipe(result.get(0));
	}
	
	@Test
	public void testRecipeDAOInsert_ShouldFail() {
		Recipe a = null;
		a = rDAO.insertRecipe(failRecipe);
		result = rDAO.selectRecipe(failRecipe);
		assert(result.size() == 0);
		assert(a == null);
	}
	
	@Test
	public void testRecipeDAODelete_ShouldWork() {
		i2rDAO = new Ingredient2RecipeDAO("jdbc:h2:file:src/test/resources/db/wfDBTest", "sa", "");
		ArrayList<Recipe> a = new ArrayList<Recipe>();
		rDAO.insertRecipe(testRecipe);
		result = rDAO.selectRecipe(testRecipe);
		assert(result.size() == 1);
		a = rDAO.deleteRecipe(testRecipe);
		result = rDAO.selectRecipe(testRecipe);
		assert(result.size() == 0);
		
		assertTrue(a.get(0).getName().equals(testRecipe.getName()));
		assertTrue(a.get(0).getDescription().equals(testRecipe.getDescription()));
		assertTrue(a.get(0).getAllgda_energy().equals(testRecipe.getAllgda_energy()));
		assertTrue(a.get(0).getAllgda_protein().equals(testRecipe.getAllgda_protein()));
		assertTrue(a.get(0).getAllgda_carbo().equals(testRecipe.getAllgda_carbo()));
		assertTrue(a.get(0).getAllgda_fat().equals(testRecipe.getAllgda_fat()));
		assertTrue(a.get(0).getAllgda_fiber().equals(testRecipe.getAllgda_fiber()));	
		assertTrue(a.get(0).getAllgda_sodium().equals(testRecipe.getAllgda_sodium()));
		assertTrue(a.get(0).getTotalprice().equals(testRecipe.getTotalprice()));
		
		Ingredient2Recipe testConnection = new Ingredient2Recipe(a.get(0).getId(), null, null);
		ArrayList<Ingredient2Recipe> result2 = i2rDAO.selectIngredient2Recipe(testConnection);
		assert(result2.size() == 0);
		assert(a.get(0).getId() != null);
	}
	
	@Test
	public void testRecipeDAODelete_ShouldFail() {
		ArrayList<Recipe> a = new ArrayList<Recipe>();
		rDAO.insertRecipe(testRecipe);
		result = rDAO.selectRecipe(testRecipe);
		assert(result.size() == 1);
		assert(a.isEmpty());
		rDAO.deleteRecipe(testRecipe);
	}
	
	@Test
	public void testRecipeDAOSelect_ShouldWork() {
		Recipe testRecipe1 = rDAO.insertRecipe(testRecipe);
		Recipe testRecipe2 = rDAO.insertRecipe(testRecipe);
		Recipe searchRecipe1 = new Recipe("testname1", null, null, null, null, null, null, null, null);
		Recipe searchRecipe2 = new Recipe(null, "testdes1", null, null, null, null, null, null, null);
		Recipe searchRecipe3 = new Recipe(null, null, 3.0, null, null, null, null, null, null);
		Recipe searchRecipe4 = new Recipe(null, null, null, 3.0, null, null, null, null, null);
		Recipe searchRecipe5 = new Recipe(null, null, null, null, 3.0, null, null, null, null);
		Recipe searchRecipe6 = new Recipe(null, null, null, null, null, 3.0, null, null, null);
		Recipe searchRecipe7 = new Recipe(null, null, null, null, null, null, 3.0, null, null);
		Recipe searchRecipe8 = new Recipe(null, null, null, null, null, null, null, 3.0, null);
		Recipe searchRecipe9 = new Recipe(null, null, null, null, null, null, null, null, 3.0);
		
		Recipe searchRecipe10 = new Recipe();
		searchRecipe10.setId(testRecipe1.getId());
		result = rDAO.selectRecipe(searchRecipe10);
		assert(result.size() == 1);
		assert(testRecipe1.getId().equals(result.get(0).getId()));
		
		Recipe searchRecipe11 = new Recipe();
		searchRecipe11.setId(testRecipe2.getId());
		result = rDAO.selectRecipe(searchRecipe11);
		assert(result.size() == 1);
		assert(testRecipe2.getId().equals(result.get(0).getId()));
		
		result = rDAO.selectRecipe(searchRecipe1);
		assert(result.size() == 2);
		assert(result.get(0).getName().equals("firstNametest1"));
		
		result = rDAO.selectRecipe(searchRecipe2);
		assert(result.size() == 2);
		assert(result.get(0).getDescription().equals("testdes1"));

		result = rDAO.selectRecipe(searchRecipe3);
		assert(result.size() == 2);
		assert(result.get(0).getAllgda_energy().equals(3.0));
		
		result = rDAO.selectRecipe(searchRecipe4);
		assert(result.size() == 2);
		assert(result.get(0).getAllgda_protein().equals(3.0));
		
		result = rDAO.selectRecipe(searchRecipe5);
		assert(result.size() == 2);
		assert(result.get(0).getAllgda_carbo().equals(3.0));
		
		result = rDAO.selectRecipe(searchRecipe6);
		assert(result.size() == 2);
		assert(result.get(0).getAllgda_fat().equals(3.0));
		
		result = rDAO.selectRecipe(searchRecipe7);
		assert(result.size() == 2);
		assert(result.get(0).getAllgda_fiber().equals(3.0));
		
		result = rDAO.selectRecipe(searchRecipe8);
		assert(result.size() == 2);
		assert(result.get(0).getAllgda_sodium().equals(3.0));
		
		result = rDAO.selectRecipe(searchRecipe9);
		assert(result.size() == 2);
		assert(result.get(0).getTotalprice().equals(3.0));
		
		rDAO.deleteRecipe(testRecipe);
	}
	
	@Test
	public void testRecipeDAOSelect_ShouldFail() {
		rDAO.insertRecipe(testRecipe);
		Recipe searchRecipe1 = new Recipe("testname1", null, null, null, null, null, null, null, null);
		Recipe searchRecipe2 = new Recipe(null, "testdes1", null, null, null, null, null, null, null);
		Recipe searchRecipe3 = new Recipe(null, null, 3.0, null, null, null, null, null, null);
		Recipe searchRecipe4 = new Recipe(null, null, null, 3.0, null, null, null, null, null);
		Recipe searchRecipe5 = new Recipe(null, null, null, null, 3.0, null, null, null, null);
		Recipe searchRecipe6 = new Recipe(null, null, null, null, null, 3.0, null, null, null);
		Recipe searchRecipe7 = new Recipe(null, null, null, null, null, null, 3.0, null, null);
		Recipe searchRecipe8 = new Recipe(null, null, null, null, null, null, null, 3.0, null);
		Recipe searchRecipe9 = new Recipe(null, null, null, null, null, null, null, null, 3.0);
		
		result = rDAO.selectRecipe(searchRecipe1);
		assert(result.size() == 0);
		
		result = rDAO.selectRecipe(searchRecipe2);
		assert(result.size() == 0);
		
		result = rDAO.selectRecipe(searchRecipe3);
		assert(result.size() == 0);
		
		result = rDAO.selectRecipe(searchRecipe4);
		assert(result.size() == 0);
		
		result = rDAO.selectRecipe(searchRecipe5);
		assert(result.size() == 0);
		
		result = rDAO.selectRecipe(searchRecipe6);
		assert(result.size() == 0);
		
		result = rDAO.selectRecipe(searchRecipe7);
		assert(result.size() == 0);
		
		result = rDAO.selectRecipe(searchRecipe8);
		assert(result.size() == 0);
		
		result = rDAO.selectRecipe(searchRecipe9);
		assert(result.size() == 0);
		
		rDAO.deleteRecipe(testRecipe);	
	}
	
	@Test
	public void testRecipeDAOUpdate_ShouldWork() {
		ArrayList<Recipe> a = new ArrayList<Recipe>();
		rDAO.insertRecipe(testRecipe);
		Recipe updateRecipe = new Recipe("name2", "description2", 11.0, 11.0, 11.0, 11.0, 11.0, 11.0, 110.0);
		a = rDAO.UpdateRecipe(updateRecipe, testRecipe);
		
		result = rDAO.selectRecipe(testRecipe);
		assert(result.size() == 0);
		
		result = rDAO.selectRecipe(updateRecipe);
		assert(result.size() == 1);
		
		assertTrue(a.get(0).getName().equals(updateRecipe.getName()));
		assertTrue(a.get(0).getDescription().equals(updateRecipe.getDescription()));
		assertTrue(a.get(0).getAllgda_energy().equals(updateRecipe.getAllgda_energy()));
		assertTrue(a.get(0).getAllgda_protein().equals(updateRecipe.getAllgda_protein()));
		assertTrue(a.get(0).getAllgda_carbo().equals(updateRecipe.getAllgda_carbo()));
		assertTrue(a.get(0).getAllgda_fat().equals(updateRecipe.getAllgda_fat()));
		assertTrue(a.get(0).getAllgda_fiber().equals(updateRecipe.getAllgda_fiber()));	
		assertTrue(a.get(0).getAllgda_sodium().equals(updateRecipe.getAllgda_sodium()));
		assertTrue(a.get(0).getTotalprice().equals(updateRecipe.getTotalprice()));
		assert(a.get(0).getId() != null);
		
		rDAO.deleteRecipe(updateRecipe);
	}
	
	@Test
	public void testRecipeDAOUpdate_ShouldFail() {
		ArrayList<Recipe> a = new ArrayList<Recipe>();
		rDAO.insertRecipe(testRecipe);
		Recipe updateRecipe = new Recipe("name2", "description2", 11.0, 11.0, 11.0, 11.0, 11.0, 11.0, 110.0);
		rDAO.insertRecipe(updateRecipe);
		result = rDAO.selectRecipe(testRecipe);
		Recipe updateRecipe2 = new Recipe("name2", "description2", 11.0, 11.0, 11.0, 11.0, 11.0, 11.0, 110.0);
		updateRecipe2.setId(result.get(0).getId());
		a = rDAO.UpdateRecipe(updateRecipe2, updateRecipe);
		assert(a.isEmpty());
		
		rDAO.selectRecipe(testRecipe);
		assert(result.size() == 1);
		rDAO.selectRecipe(updateRecipe);
		assert(result.size() == 1);
		rDAO.selectRecipe(updateRecipe2);
		assert(result.size() == 0);
		rDAO.deleteRecipe(testRecipe);
		rDAO.deleteRecipe(updateRecipe);
	}
}
