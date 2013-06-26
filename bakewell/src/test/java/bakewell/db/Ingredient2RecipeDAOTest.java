package bakewell.db;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bakewell.beans.Ingredient;
import bakewell.beans.Ingredient2Recipe;
import bakewell.beans.Recipe;

import junit.framework.TestCase;

public class Ingredient2RecipeDAOTest extends TestCase {

	Ingredient2RecipeDAO i2rDAO = null;
	RecipeDAO rDAO = null;
	IngredientDAO iDAO = null;
	Recipe testRecipe = null;
	Ingredient testIngredient = null;
	Ingredient2Recipe testIngredient2Recipe = null;
	Ingredient2Recipe failIngredient2Recipe = null;
	ArrayList<Ingredient2Recipe> result = null;
	
	@Before
	protected void setUp() {
		i2rDAO = new Ingredient2RecipeDAO("jdbc:h2:file:src/test/resources/db/wfDBTest", "sa", "");
		rDAO = new RecipeDAO("jdbc:h2:file:src/test/resources/db/wfDBTest", "sa", "");
		iDAO = new IngredientDAO("jdbc:h2:file:src/test/resources/db/wfDBTest", "sa", "");
		testRecipe = new Recipe("name1", "description1", 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 10.0);
		testRecipe = rDAO.insertRecipe(testRecipe);
		testIngredient = new Ingredient("butter", 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 10.0);
		testIngredient = iDAO.insertIngredient(testIngredient);
		
		testIngredient2Recipe = new Ingredient2Recipe(testRecipe.getId(), testIngredient.getId(), 10.0);
		failIngredient2Recipe = new Ingredient2Recipe(); 
		result = new ArrayList<Ingredient2Recipe>();
	}
	
	@After
	protected void tearDown() {
		i2rDAO = null;
		rDAO.deleteRecipe(testRecipe);
		iDAO.deleteIngredient(testIngredient);
		rDAO = null;
		iDAO = null;
		testIngredient2Recipe = null;
		failIngredient2Recipe = null;
		result = null;
	}
	
	@Test
	public void testIngredient2RecipeDAOInsert_ShouldWork() {
		Ingredient2Recipe a = i2rDAO.insertIngredient2Recipe(testIngredient2Recipe);
		result = i2rDAO.selectIngredient2Recipe(testIngredient2Recipe);
	
		assertTrue(result.get(0).getRecipe_id().equals(a.getRecipe_id()));
		assertTrue(result.get(0).getIngredient_id().equals(a.getIngredient_id()));
		assertTrue(result.get(0).getId().equals(a.getId()));
		assert(a.getId() != null);
		
		i2rDAO.deleteIngredient2Recipe(result.get(0));
	}
	
	@Test
	public void testIngredient2RecipeDAOInsert_ShouldFail() {
		Ingredient2Recipe a = null;
		a = i2rDAO.insertIngredient2Recipe(failIngredient2Recipe);
		result = i2rDAO.selectIngredient2Recipe(failIngredient2Recipe);
		assert(result.size() == 0);
		assert(a == null);
		i2rDAO.deleteIngredient2Recipe(a);
	}
	
	@Test
	public void testIngredient2RecipeDAODelete_ShouldWork() {
		ArrayList<Ingredient2Recipe> a = new ArrayList<Ingredient2Recipe>();
		testIngredient2Recipe = i2rDAO.insertIngredient2Recipe(testIngredient2Recipe);
		result = i2rDAO.selectIngredient2Recipe(testIngredient2Recipe);
		assert(result.size() == 1);
		a = i2rDAO.deleteIngredient2Recipe(testIngredient2Recipe);
		result = i2rDAO.selectIngredient2Recipe(testIngredient2Recipe);
		assert(result.size() == 0);
		
		assertTrue(a.get(0).getRecipe_id().equals(testIngredient2Recipe.getRecipe_id()));
		assertTrue(a.get(0).getIngredient_id().equals(testIngredient2Recipe.getIngredient_id()));
		assertTrue(a.get(0).getId().equals(testIngredient2Recipe.getId()));
		assert(a.get(0).getId() != null);
	}
	
	@Test
	public void testIngredient2RecipeDAODelete_ShouldFail() {
		ArrayList<Ingredient2Recipe> a = new ArrayList<Ingredient2Recipe>();
		i2rDAO.insertIngredient2Recipe(testIngredient2Recipe);
		a = i2rDAO.deleteIngredient2Recipe(failIngredient2Recipe);
		result = i2rDAO.selectIngredient2Recipe(testIngredient2Recipe);
		assert(result.size() == 1);
		assert(a.isEmpty());
		i2rDAO.deleteIngredient2Recipe(testIngredient2Recipe);
	}
	
	@Test
	public void testIngredient2RecipeDAOSelect_ShouldWork() {
		i2rDAO.insertIngredient2Recipe(testIngredient2Recipe);
		i2rDAO.insertIngredient2Recipe(testIngredient2Recipe);
		Ingredient2Recipe searchIngredient2RecipeRecipe = new Ingredient2Recipe(testRecipe.getId(), null, null);
		Ingredient2Recipe searchIngredient2RecipeIngredient = new Ingredient2Recipe(null, testIngredient.getId(), null);
		Ingredient2Recipe searchIngredient2RecipeAmount = new Ingredient2Recipe(null, null, 10.0);
		
		result = i2rDAO.selectIngredient2Recipe(searchIngredient2RecipeRecipe);
		assert(result.size() == 2);
		assert(result.get(0).getRecipe_id().equals(testRecipe.getId()));
		
		result = i2rDAO.selectIngredient2Recipe(searchIngredient2RecipeIngredient);
		assert(result.size() == 2);
		assert(result.get(0).getIngredient_id().equals(testIngredient.getId()));
		
		result = i2rDAO.selectIngredient2Recipe(searchIngredient2RecipeAmount);
		assert(result.size() == 2);
		assert(result.get(0).getAmount().equals(10.0));
		
		i2rDAO.deleteIngredient2Recipe(testIngredient2Recipe);
	}
	
	@Test
	public void testIngredient2RecipeDAOSelect_ShouldFail() {
		i2rDAO.insertIngredient2Recipe(testIngredient2Recipe);
		Ingredient2Recipe searchIngredient2RecipeRecipe = new Ingredient2Recipe(testRecipe.getId(), null, null);
		Ingredient2Recipe searchIngredient2RecipeIngredient = new Ingredient2Recipe(null, testIngredient.getId(), null);
		Ingredient2Recipe searchIngredient2RecipeAmount = new Ingredient2Recipe(null, null, 10.0);
		
		result = i2rDAO.selectIngredient2Recipe(searchIngredient2RecipeRecipe);
		assert(result.size() == 0);
		
		result = i2rDAO.selectIngredient2Recipe(searchIngredient2RecipeIngredient);
		assert(result.size() == 0);
		
		result = i2rDAO.selectIngredient2Recipe(searchIngredient2RecipeAmount);
		assert(result.size() == 0);
		
		i2rDAO.deleteIngredient2Recipe(testIngredient2Recipe);	
	}
	
	@Test
	public void testIngredient2RecipeDAOUpdate_ShouldWork() {
		ArrayList<Ingredient2Recipe> a = new ArrayList<Ingredient2Recipe>();
		i2rDAO.insertIngredient2Recipe(testIngredient2Recipe);
		Ingredient2Recipe updateIngredient2Recipe = new Ingredient2Recipe(testRecipe.getId(), testIngredient.getId(), 20.0);
		a = i2rDAO.UpdateIngredient2Recipe(updateIngredient2Recipe, testIngredient2Recipe);
		
		result = i2rDAO.selectIngredient2Recipe(testIngredient2Recipe);
		assert(result.size() == 0);
		
		result = i2rDAO.selectIngredient2Recipe(updateIngredient2Recipe);
		assert(result.size() == 1);
		
		assertTrue(a.get(0).getRecipe_id().equals(result.get(0).getRecipe_id()));
		assertTrue(a.get(0).getIngredient_id().equals(result.get(0).getIngredient_id()));
		assertTrue(a.get(0).getId().equals(result.get(0).getId()));
		assert(a.get(0).getId() != null);
		
		i2rDAO.deleteIngredient2Recipe(updateIngredient2Recipe);
	}
	
	@Test
	public void testIngredient2RecipeDAOUpdate_ShouldFail() {
		ArrayList<Ingredient2Recipe> a = new ArrayList<Ingredient2Recipe>();
		i2rDAO.insertIngredient2Recipe(testIngredient2Recipe);
		Ingredient2Recipe updateIngredient2Recipe = new Ingredient2Recipe(testRecipe.getId(), testIngredient.getId(), 20.0);
		i2rDAO.insertIngredient2Recipe(updateIngredient2Recipe);
		result = i2rDAO.selectIngredient2Recipe(testIngredient2Recipe);
		Ingredient2Recipe updateIngredient2Recipe2 = new Ingredient2Recipe(testRecipe.getId(), testIngredient.getId(), 20.0);
		updateIngredient2Recipe2.setId(result.get(0).getId());
		a = i2rDAO.UpdateIngredient2Recipe(updateIngredient2Recipe2, updateIngredient2Recipe);
		assert(a.isEmpty());
		
		i2rDAO.selectIngredient2Recipe(testIngredient2Recipe);
		assert(result.size() == 1);
		i2rDAO.selectIngredient2Recipe(updateIngredient2Recipe);
		assert(result.size() == 1);
		i2rDAO.selectIngredient2Recipe(updateIngredient2Recipe2);
		assert(result.size() == 0);
		i2rDAO.deleteIngredient2Recipe(testIngredient2Recipe);
		i2rDAO.deleteIngredient2Recipe(updateIngredient2Recipe);
	}
}
