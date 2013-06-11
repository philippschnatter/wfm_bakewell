package bakewell.web;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import junit.framework.Assert;

import bakewell.beans.Recipe;
import bakewell.webservice.StartServers;
import bakewell.webservice.ingredient.IngredientRESTService;

import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.provider.json.JSONProvider;
import org.junit.BeforeClass;
import org.junit.Test;




public class WebServiceTest {
	
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
			
			recipe = service.calculateIngredient("2");

			Assert.assertNotNull(recipe);
			
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
