package bakewell.web;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import junit.framework.Assert;

import bakewell.beans.Customer;
import bakewell.beans.Recipe;
import bakewell.db.CustomerDAO;
//import bakewell.db.ProductDAO;
//import bakewell.db.RecipeDAO;
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
	
	
//	private ProductDAO prodao;
//	private RecipeDAO recdao;
//	private final String USER = "SA";
//	private final String PW = "";
	private static StartServers server = null;
	
	@BeforeClass
	public static void init()
	{
		
		server = new StartServers();
		server.start();
	}
	
	@Test
	public void testIngredientWS() {
		
		
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
		sf.setAddress("http://localhost:63083");
		sf.setProvider(jsonProvider);
		
		// Binding
		BindingFactoryManager manager = sf.getBus().getExtension(BindingFactoryManager.class);
		JAXRSBindingFactory factory = new JAXRSBindingFactory();
		factory.setBus(sf.getBus());
		manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID, factory);
		
		// create service
		IngredientRESTService service = sf.create(IngredientRESTService.class);
		WebClient wc = sf.createWebClient();
		wc.accept(MediaType.APPLICATION_JSON);
		
		Recipe recipe;
		
		try {
			
			recipe = service.calculateIngredient("3");

			NumberFormat numberFormat = new DecimalFormat("0.00");
		    numberFormat.setRoundingMode(RoundingMode.DOWN);
		    
		    
			Assert.assertNotNull(recipe);
			Assert.assertEquals(recipe.getName(), "Sachertorte");
//			Assert.assertEquals(recipe.getDescription(), "description"); // zu lange^^
			
			Assert.assertEquals(numberFormat.format(recipe.getAllgda_carbo()), "0,33");
			Assert.assertEquals(numberFormat.format(recipe.getAllgda_energy()), "0,04");
			Assert.assertEquals(numberFormat.format(recipe.getAllgda_fat()), "1,05");
			Assert.assertEquals(numberFormat.format(recipe.getAllgda_fiber()), "4,16");
			Assert.assertEquals(numberFormat.format(recipe.getAllgda_protein()), "1,81");
			Assert.assertEquals(numberFormat.format(recipe.getAllgda_sodium()), "41,66");
			Assert.assertNotNull(recipe.getIngredients());
	
		} catch (WebApplicationException waEx) {
			
			waEx.printStackTrace();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		recipe = null;
		
	}
	
	@Test
	public void testRecipePriceWS() {
		
		
		// REST-Service
		// define a JSON provider and a mapping between REST and JSON namespaces
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
		
		// Binding
		BindingFactoryManager manager = sf.getBus().getExtension(BindingFactoryManager.class);
		JAXRSBindingFactory factory = new JAXRSBindingFactory();
		factory.setBus(sf.getBus());
		manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID, factory);
		
		// create service
		RecipePriceRESTService service = sf.create(RecipePriceRESTService.class);
		WebClient wc = sf.createWebClient();
		wc.accept(MediaType.APPLICATION_JSON);
		
		Recipe recipe;
		
		try {

			CustomerDAO cDAO = new CustomerDAO("jdbc:h2:file:src/main/resources/db/wfDB", "sa", "");
			Customer testCustomer = new Customer(null, "Gruber", null, null, null, null, null);
			ArrayList<Customer> result = cDAO.selectCustomer(testCustomer);
					
			if (!result.isEmpty()) {
				Customer newcustomer = result.get(0);
				System.out.println(newcustomer.getFirstName()+" "+newcustomer.getLastName()+newcustomer.getMailAddress());	
			}

					
			recipe = service.calculatePrice("3");

			NumberFormat numberFormat = new DecimalFormat("0.00");
		    numberFormat.setRoundingMode(RoundingMode.DOWN);
		    
		    
			Assert.assertNotNull(recipe);
			Assert.assertEquals(numberFormat.format(recipe.getTotalprice()), "190,00");

	
		} catch (WebApplicationException waEx) {
			
			waEx.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		recipe = null;
		
	}
	

}
