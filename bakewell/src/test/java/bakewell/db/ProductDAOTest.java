package bakewell.db;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bakewell.beans.Customer;
import bakewell.beans.Ingredient;
import bakewell.beans.Ingredient2Recipe;
import bakewell.beans.Product;
import bakewell.beans.Recipe;

import junit.framework.TestCase;

public class ProductDAOTest extends TestCase {

	ProductDAO pDAO = null;
	RecipeDAO rDAO = null;
	CustomerDAO cDAO = null;
	IngredientDAO iDAO = null;
	Ingredient2RecipeDAO i2rDAO = null;
	Recipe testRecipe = null;
	Customer testCustomer = null;
	Product testProduct = null;
	Product failProduct = null;
	ArrayList<Product> result = null;
	
	@SuppressWarnings("deprecation")
	@Before
	protected void setUp() {
		pDAO = new ProductDAO("jdbc:h2:file:src/test/resources/db/wfDBTest", "sa", "");
		rDAO = new RecipeDAO("jdbc:h2:file:src/test/resources/db/wfDBTest", "sa", "");
		cDAO = new CustomerDAO("jdbc:h2:file:src/test/resources/db/wfDBTest", "sa", "");
		
		testRecipe = new Recipe("testRecipe", "testDescription", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
		testRecipe = rDAO.insertRecipe(testRecipe);
		testCustomer = new Customer("firstNameTest1", "lastNameTest1", "addressTest1", "1234567890", "test1@mail1.com", "testCompany1", "testPassword1");
		testCustomer = cDAO.insertCustomer(testCustomer);
		
		testProduct = new Product(testRecipe.getId(), testCustomer.getId(), "testProductName", new Date(2013, 06, 26), new Date(2013, 01, 01), new Date (2013, 06, 01), "testFacility", "testProdContr", "testTrContr", 0.0, "testComment");
		failProduct = new Product(); 
		result = new ArrayList<Product>();		
	}
	
	@After
	protected void tearDown() {
		pDAO = null;
		rDAO = null;
		cDAO = null;
		testRecipe = null;
		testCustomer = null;
		testProduct = null;
		failProduct = null;
		result = null;
	}
	
	@Test
	public void testProductDAOSelectRecipeByProductId_ShouldWork() {
		Product a = pDAO.insertProduct(testProduct);
		Recipe r = pDAO.selectRecipeByProductId(a.getId());
		
		assertTrue(r.getName().equals(testRecipe.getName()));
		assertTrue(r.getDescription().equals(testRecipe.getDescription()));
		assertTrue(r.getAllgda_energy().equals(testRecipe.getAllgda_energy()));
		assertTrue(r.getAllgda_protein().equals(testRecipe.getAllgda_protein()));
		assertTrue(r.getAllgda_carbo().equals(testRecipe.getAllgda_carbo()));
		assertTrue(r.getAllgda_fat().equals(testRecipe.getAllgda_fat()));
		assertTrue(r.getAllgda_fiber().equals(testRecipe.getAllgda_fiber()));	
		assertTrue(r.getAllgda_sodium().equals(testRecipe.getAllgda_sodium()));
		assertTrue(r.getTotalprice().equals(testRecipe.getTotalprice()));
		
		pDAO.deleteProduct(a);
	}
	
	@Test
	public void testProductDAOSelectRecipeByProductId_ShouldFail() {
		Recipe r = pDAO.selectRecipeByProductId(-1);
		assertTrue(r == null);
	}
	
	@Test
	public void testProductDAOSelectIngredientsOfProduct_ShouldWork() {
		iDAO = new IngredientDAO("jdbc:h2:file:src/test/resources/db/wfDBTest", "sa", "");
		i2rDAO = new Ingredient2RecipeDAO("jdbc:h2:file:src/test/resources/db/wfDBTest", "sa", "");
		
		Ingredient testIngredient1 = new Ingredient("butter", 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 10.0);
		Ingredient testIngredient2 = new Ingredient("butter", 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 10.0);
		
		testIngredient1 = iDAO.insertIngredient(testIngredient1);
		testIngredient2 = iDAO.insertIngredient(testIngredient2);
		
		Ingredient2Recipe testConnection1 = new Ingredient2Recipe(testRecipe.getId(), testIngredient1.getId(), 10.0);
		Ingredient2Recipe testConnection2 = new Ingredient2Recipe(testRecipe.getId(), testIngredient2.getId(), 10.0);
		
		testConnection1 = i2rDAO.insertIngredient2Recipe(testConnection1);
		testConnection2 = i2rDAO.insertIngredient2Recipe(testConnection2);
		
		Map <Ingredient, Double> result = new HashMap<Ingredient, Double>();
				
		result = pDAO.selectIngredientsOfProduct(testProduct.getId());
		
		assertTrue(result.values().size() == 2);
		assertTrue(result.keySet().size() == 2);
	}
	
	@Test
	public void testProductDAOSelectIngredientsOfProduct_ShouldFail() {
		Map <Ingredient, Double> result = new HashMap<Ingredient, Double>();
		result = pDAO.selectIngredientsOfProduct(testProduct.getId());
		
		assertTrue(result.values().size() == 0);
		assertTrue(result.keySet().size() == 0);
	}
	
	@Test
	public void testProductDAOInsert_ShouldWork() {
		Product a = pDAO.insertProduct(testProduct);
		result = pDAO.selectProduct(testProduct);
				
		assertTrue(result.get(0).getRecipe_id().equals(testProduct.getRecipe_id()));
		assertTrue(result.get(0).getCustomer_id().equals(testProduct.getCustomer_id()));
		assertTrue(result.get(0).getProduct_Name().equals(testProduct.getProduct_Name()));
		assertTrue(result.get(0).getDeliveryDate().equals(testProduct.getDeliveryDate()));
		assertTrue(result.get(0).getProduction_Start().equals(testProduct.getProduction_Start()));
		assertTrue(result.get(0).getProduction_End().equals(testProduct.getProduction_End()));
		assertTrue(result.get(0).getProduction_Facility().equals(testProduct.getProduction_Facility()));	
		assertTrue(result.get(0).getProduction_Contractor().equals(testProduct.getProduction_Contractor()));
		assertTrue(result.get(0).getTransport_Contractor().equals(testProduct.getTransport_Contractor()));
		assertTrue(result.get(0).getTransport_cost().equals(testProduct.getTransport_cost()));
		assertTrue(result.get(0).getComment().equals(testProduct.getComment()));	
		
		assertTrue(result.get(0).getRecipe_id().equals(a.getRecipe_id()));
		assertTrue(result.get(0).getCustomer_id().equals(a.getCustomer_id()));
		assertTrue(result.get(0).getProduct_Name().equals(a.getProduct_Name()));
		assertTrue(result.get(0).getDeliveryDate().equals(a.getDeliveryDate()));
		assertTrue(result.get(0).getProduction_Start().equals(a.getProduction_Start()));
		assertTrue(result.get(0).getProduction_End().equals(a.getProduction_End()));
		assertTrue(result.get(0).getProduction_Facility().equals(a.getProduction_Facility()));	
		assertTrue(result.get(0).getProduction_Contractor().equals(a.getProduction_Contractor()));
		assertTrue(result.get(0).getTransport_Contractor().equals(a.getTransport_Contractor()));
		assertTrue(result.get(0).getTransport_cost().equals(a.getTransport_cost()));
		assertTrue(result.get(0).getComment().equals(a.getComment()));
		assert(a.getId() != null);
		
		pDAO.deleteProduct(result.get(0));
	}
	
	@Test
	public void testProductDAOInsert_ShouldFail() {
		Product a = null;
		a = pDAO.insertProduct(failProduct);
		result = pDAO.selectProduct(failProduct);
		assert(result.size() == 0);
		assert(a == null);
		pDAO.deleteProduct(a);
	}
	
	@Test
	public void testProductDAODelete_ShouldWork() {
		ArrayList<Product> a = new ArrayList<Product>();
		pDAO.insertProduct(testProduct);
		result = pDAO.selectProduct(testProduct);
		assert(result.size() == 1);
		a = pDAO.deleteProduct(testProduct);
		result = pDAO.selectProduct(testProduct);
		assert(result.size() == 0);
		
		assertTrue(a.get(0).getRecipe_id().equals(testProduct.getRecipe_id()));
		assertTrue(a.get(0).getCustomer_id().equals(testProduct.getCustomer_id()));
		assertTrue(a.get(0).getProduct_Name().equals(testProduct.getProduct_Name()));
		assertTrue(a.get(0).getDeliveryDate().equals(testProduct.getDeliveryDate()));
		assertTrue(a.get(0).getProduction_Start().equals(testProduct.getProduction_Start()));
		assertTrue(a.get(0).getProduction_End().equals(testProduct.getProduction_End()));
		assertTrue(a.get(0).getProduction_Facility().equals(testProduct.getProduction_Facility()));	
		assertTrue(a.get(0).getProduction_Contractor().equals(testProduct.getProduction_Contractor()));
		assertTrue(a.get(0).getTransport_Contractor().equals(testProduct.getTransport_Contractor()));
		assertTrue(a.get(0).getTransport_cost().equals(testProduct.getTransport_cost()));
		assertTrue(a.get(0).getComment().equals(testProduct.getComment()));
		assert(a.get(0).getId() != null);
	}
	
	@Test
	public void testProductDAODelete_ShouldFail() {
		ArrayList<Product> a = new ArrayList<Product>();
		pDAO.insertProduct(testProduct);
		a = pDAO.deleteProduct(failProduct);
		result = pDAO.selectProduct(testProduct);
		assert(result.size() == 1);
		assert(a.isEmpty());
		pDAO.deleteProduct(testProduct);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testProductDAOSelect_ShouldWork() {
		pDAO.insertProduct(testProduct);
		pDAO.insertProduct(testProduct);
		Product searchProductRecipeId = new Product(testRecipe.getId(), null, null, null, null, null, null, null, null, null, null);
		Product searchProductCustomerId = new Product(null, testCustomer.getId(), null, null, null, null, null, null, null, null, null);
		Product searchProductProductName = new Product(null, null, "testProductName", null, null, null, null, null, null, null, null);
		Product searchProductDeliveryDate = new Product(null, null, null, new Date(2013, 06, 26), null, null, null, null, null, null, null);
		Product searchProductProductionStart = new Product(null, null, null, null, new Date(2013, 01, 01), null, null, null, null, null, null);
		Product searchProductProductionEnd = new Product(null, null, null, null, null, new Date (2013, 06, 01), null, null, null, null, null);
		Product searchProductProductionFacility = new Product(null, null, null, null, null, null, "testFacility", null, null, null, null);
		Product searchProductProductionContractor = new Product(null, null, null, null, null, null, null, "testProdContr", null, null, null);
		Product searchProductProductionTransportContractor = new Product(null, null, null, null, null, null, null, null, "testTrContr", null, null);
		Product searchProductProductTransportCost = new Product(null, null, null, null, null, null, null, null, null, 0.0, null);
		Product searchProductProductComment = new Product(null, null, null, null, null, null, null, null, null, null, "testComment");
		
		result = pDAO.selectProduct(searchProductRecipeId);
		assert(result.size() == 2);
		assert(result.get(0).getRecipe_id().equals(testRecipe.getId()));
		
		result = pDAO.selectProduct(searchProductCustomerId);
		assert(result.size() == 2);
		assert(result.get(0).getCustomer_id().equals(testCustomer.getId()));
		
		result = pDAO.selectProduct(searchProductProductName);
		assert(result.size() == 2);
		assert(result.get(0).getProduct_Name().equals("testProductName"));
		
		result = pDAO.selectProduct(searchProductDeliveryDate);
		assert(result.size() == 2);
		assert(result.get(0).getDeliveryDate().equals(new Date(2013, 06, 26)));
		
		result = pDAO.selectProduct(searchProductProductionStart);
		assert(result.size() == 2);
		assert(result.get(0).getProduction_Start().equals(new Date(2013, 01, 01)));
		
		result = pDAO.selectProduct(searchProductProductionEnd);
		assert(result.size() == 2);
		assert(result.get(0).getProduction_End().equals(new Date (2013, 06, 01)));
		
		result = pDAO.selectProduct(searchProductProductionFacility);
		assert(result.size() == 2);
		assert(result.get(0).getProduction_Facility().equals("testFacility"));
		
		result = pDAO.selectProduct(searchProductProductionContractor);
		assert(result.size() == 2);
		assert(result.get(0).getProduction_Contractor().equals("testProdContr"));
		
		result = pDAO.selectProduct(searchProductProductionTransportContractor);
		assert(result.size() == 2);
		assert(result.get(0).getTransport_Contractor().equals("testTrContr"));
		
		result = pDAO.selectProduct(searchProductProductTransportCost);
		assert(result.size() == 2);
		assert(result.get(0).getTransport_cost().equals(0.0));
		
		result = pDAO.selectProduct(searchProductProductComment);
		assert(result.size() == 2);
		assert(result.get(0).getComment().equals("testComment"));
		
		pDAO.deleteProduct(testProduct);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testProductDAOSelect_ShouldFail() {
		pDAO.insertProduct(testProduct);
		Product searchProductRecipeId = new Product(testRecipe.getId(), null, null, null, null, null, null, null, null, null, null);
		Product searchProductCustomerId = new Product(null, testCustomer.getId(), null, null, null, null, null, null, null, null, null);
		Product searchProductProductName = new Product(null, null, "testProductName", null, null, null, null, null, null, null, null);
		Product searchProductDeliveryDate = new Product(null, null, null, new Date(2013, 06, 26), null, null, null, null, null, null, null);
		Product searchProductProductionStart = new Product(null, null, null, null, new Date(2013, 01, 01), null, null, null, null, null, null);
		Product searchProductProductionEnd = new Product(null, null, null, null, null, new Date (2013, 06, 01), null, null, null, null, null);
		Product searchProductProductionFacility = new Product(null, null, null, null, null, null, "testFacility", null, null, null, null);
		Product searchProductProductionContractor = new Product(null, null, null, null, null, null, null, "testProdContr", null, null, null);
		Product searchProductProductionTransportContractor = new Product(null, null, null, null, null, null, null, null, "testTrContr", null, null);
		Product searchProductProductTransportCost = new Product(null, null, null, null, null, null, null, null, null, 0.0, null);
		Product searchProductProductComment = new Product(null, null, null, null, null, null, null, null, null, null, "testComment");
		
		result = pDAO.selectProduct(searchProductRecipeId);
		assert(result.size() == 0);
		
		result = pDAO.selectProduct(searchProductCustomerId);
		assert(result.size() == 0);
		
		result = pDAO.selectProduct(searchProductProductName);
		assert(result.size() == 0);
		
		result = pDAO.selectProduct(searchProductDeliveryDate);
		assert(result.size() == 0);
		
		result = pDAO.selectProduct(searchProductProductionStart);
		assert(result.size() == 0);
		
		result = pDAO.selectProduct(searchProductProductionEnd);
		assert(result.size() == 0);
		
		result = pDAO.selectProduct(searchProductProductionFacility);
		assert(result.size() == 0);
		
		result = pDAO.selectProduct(searchProductProductionContractor);
		assert(result.size() == 0);
		
		result = pDAO.selectProduct(searchProductProductionTransportContractor);
		assert(result.size() == 0);
		
		result = pDAO.selectProduct(searchProductProductTransportCost);
		assert(result.size() == 0);
		
		result = pDAO.selectProduct(searchProductProductComment);
		assert(result.size() == 0);
		
		pDAO.deleteProduct(testProduct);	
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testProductDAOUpdate_ShouldWork() {
		ArrayList<Product> a = new ArrayList<Product>();
		pDAO.insertProduct(testProduct);
		Product updateProduct = new Product(testRecipe.getId(), testCustomer.getId(), "testProductName1", new Date(2014, 06, 26), new Date(2014, 01, 01), new Date (2014, 06, 01), "testFacility2", "testProdContr2", "testTrContr2", 1.0, "testComment2");
		a = pDAO.UpdateProduct(updateProduct, testProduct);
		
		result = pDAO.selectProduct(testProduct);
		assert(result.size() == 0);
		
		result = pDAO.selectProduct(updateProduct);
		assert(result.size() == 1);
		
		assertTrue(a.get(0).getRecipe_id().equals(updateProduct.getRecipe_id()));
		assertTrue(a.get(0).getCustomer_id().equals(updateProduct.getCustomer_id()));
		assertTrue(a.get(0).getProduct_Name().equals(updateProduct.getProduct_Name()));
		assertTrue(a.get(0).getDeliveryDate().equals(updateProduct.getDeliveryDate()));
		assertTrue(a.get(0).getProduction_Start().equals(updateProduct.getProduction_Start()));
		assertTrue(a.get(0).getProduction_End().equals(updateProduct.getProduction_End()));
		assertTrue(a.get(0).getProduction_Facility().equals(updateProduct.getProduction_Facility()));	
		assertTrue(a.get(0).getProduction_Contractor().equals(updateProduct.getProduction_Contractor()));
		assertTrue(a.get(0).getTransport_Contractor().equals(updateProduct.getTransport_Contractor()));
		assertTrue(a.get(0).getTransport_cost().equals(updateProduct.getTransport_cost()));
		assertTrue(a.get(0).getComment().equals(updateProduct.getComment()));
		assert(a.get(0).getId() != null);
		
		pDAO.deleteProduct(updateProduct);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testProductDAOUpdate_ShouldFail() {
		ArrayList<Product> a = new ArrayList<Product>();
		pDAO.insertProduct(testProduct);
		Product updateProduct = new Product(testRecipe.getId(), testCustomer.getId(), "testProductName1", new Date(2014, 06, 26), new Date(2014, 01, 01), new Date (2014, 06, 01), "testFacility2", "testProdContr2", "testTrContr2", 1.0, "testComment2");
		pDAO.insertProduct(updateProduct);
		result = pDAO.selectProduct(testProduct);
		Product updateProduct2 = new Product(testRecipe.getId(), testCustomer.getId(), "testProductName1", new Date(2014, 06, 26), new Date(2014, 01, 01), new Date (2014, 06, 01), "testFacility2", "testProdContr2", "testTrContr2", 1.0, "testComment2");
		updateProduct2.setId(result.get(0).getId());
		a = pDAO.UpdateProduct(updateProduct2, updateProduct);
		assert(a.isEmpty());
		
		pDAO.selectProduct(testProduct);
		assert(result.size() == 1);
		pDAO.selectProduct(updateProduct);
		assert(result.size() == 1);
		pDAO.selectProduct(updateProduct2);
		assert(result.size() == 0);
		pDAO.deleteProduct(testProduct);
		pDAO.deleteProduct(updateProduct);
	}
}
