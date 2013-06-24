package bakewell.web;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import junit.framework.Assert;

import bakewell.beans.Recipe;
import bakewell.db.ProductDAO;
import bakewell.db.RecipeDAO;
import bakewell.webservice.StartServers;
import bakewell.webservice.ingredient.IngredientRESTService;
import bakewell.webservice.logistics.RecipePriceRESTService;

import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.provider.json.JSONProvider;
import org.junit.BeforeClass;
import org.junit.Test;



public class WebServiceTest {
	
	
	private ProductDAO prodao;
	private RecipeDAO recdao;
	private final String USER = "sa";
	private final String PW = "";
	private static StartServers server = null;
	
	@BeforeClass
	public static void init()
	{
		
		server = new StartServers();
		server.start();
	}
	
	@Test
	public void testIngredientWS() {
		
		this.prodao = new ProductDAO (USER, PW);
		this.recdao = new RecipeDAO(USER, PW);
		
		// REST-Service
		// define a JSON provider and a mapping between REST and JSON namespace's
		java.util.List<JSONProvider<IngredientRESTService>> providers = new java.util.ArrayList<JSONProvider<IngredientRESTService>> ();
		JSONProvider<IngredientRESTService> jsonProvider = new JSONProvider<IngredientRESTService>();
		Map<String, String> map = new HashMap<String, String> ();
		map.put ( "http://rest.ws.wfm", "{}" );
		jsonProvider.setNamespaceMap ( map );
		providers.add ( jsonProvider );
		
		// Client setup programmatically
		JAXRSClientFactoryBean sf = new JAXRSClientFactoryBean();
		sf.setResourceClass(IngredientRESTService.class);
		sf.setAddress("http://localhost:63082");
		sf.setProvider(jsonProvider);
		
		BindingFactoryManager manager = sf.getBus().getExtension(BindingFactoryManager.class);
		JAXRSBindingFactory factory = new JAXRSBindingFactory();
		factory.setBus(sf.getBus());
		manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID, factory);
		
		IngredientRESTService service = sf.create(IngredientRESTService.class);
		WebClient wc = sf.createWebClient();
		wc.accept(MediaType.APPLICATION_JSON);
		
		Recipe recipe;
		
		try {
			
			recipe = service.calculateIngredient("0");

			Assert.assertNotNull(recipe);
			Assert.assertEquals(recipe.getAllgda_carbo().toString(), "name");
			Assert.assertEquals(recipe.getName(), "description");
			Assert.assertEquals(recipe.getDescription(), "54.20");
			Assert.assertEquals(recipe.getAllgda_energy().toString(), "54.20");
			Assert.assertEquals(recipe.getAllgda_fat().toString(), "54.20");
			Assert.assertEquals(recipe.getAllgda_fiber().toString(), "54.20");
			Assert.assertEquals(recipe.getAllgda_protein().toString(), "54.20");
			Assert.assertEquals(recipe.getAllgda_sodium().toString(), "54.20");
			Assert.assertNotNull(recipe.getIngredients());
	
		} catch (WebApplicationException waEx) {
			
			Assert.fail(waEx.getLocalizedMessage());
			waEx.printStackTrace();
			
		} catch (Exception e) {
			
			Assert.fail(e.getLocalizedMessage());
			e.printStackTrace();
		}
		recipe = null;
		
	}
	
	@Test
	public void testRecipePriceWS() {
		
		this.prodao = new ProductDAO (USER, PW);
		this.recdao = new RecipeDAO(USER, PW);
		
		// REST-Service
		// define a JSON provider and a mapping between REST and JSON namespace's
		java.util.List<JSONProvider<RecipePriceRESTService>> providers = new java.util.ArrayList<JSONProvider<RecipePriceRESTService>> ();
		JSONProvider<RecipePriceRESTService> jsonProvider = new JSONProvider<RecipePriceRESTService>();
		Map<String, String> map = new HashMap<String, String> ();
		map.put ( "http://rest.ws.wfm", "{}" );
		jsonProvider.setNamespaceMap ( map );
		providers.add ( jsonProvider );
		
		// Client setup programmatically
		JAXRSClientFactoryBean sf = new JAXRSClientFactoryBean();
		sf.setResourceClass(RecipePriceRESTService.class);
		sf.setAddress("http://localhost:63082");
		sf.setProvider(jsonProvider);
		
		BindingFactoryManager manager = sf.getBus().getExtension(BindingFactoryManager.class);
		JAXRSBindingFactory factory = new JAXRSBindingFactory();
		factory.setBus(sf.getBus());
		manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID, factory);
		
		RecipePriceRESTService service = sf.create(RecipePriceRESTService.class);
		WebClient wc = sf.createWebClient();
		wc.accept(MediaType.APPLICATION_JSON);
		
		Recipe recipe;
		
		try {
			
			recipe = service.calculatePrice("0");

			Assert.assertNotNull(recipe);
			Assert.assertEquals(recipe.getTotalprice().toString(), "567.60");

	
		} catch (WebApplicationException waEx) {
			
			Assert.fail(waEx.getLocalizedMessage());
			waEx.printStackTrace();
			
		} catch (Exception e) {
			
			Assert.fail(e.getLocalizedMessage());
			e.printStackTrace();
		}
		recipe = null;
		
	}
	

}
