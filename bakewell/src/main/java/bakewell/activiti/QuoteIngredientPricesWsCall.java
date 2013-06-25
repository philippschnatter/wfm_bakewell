package bakewell.activiti;


import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import junit.framework.Assert;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.provider.json.JSONProvider;

import bakewell.beans.Recipe;
import bakewell.webservice.logistics.RecipePriceRESTService;

public class QuoteIngredientPricesWsCall implements JavaDelegate
{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// executeWs(execution);
	}
	
	public void executeWs(DelegateExecution execution) throws Exception {
		
		
		Integer productId = (Integer)execution.getVariable("productid");
		
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
		
		Recipe recipe = null;
		
		try {
			
			recipe = service.calculatePrice(productId.toString());
			execution.setVariable("recipe", recipe);
			
		} catch (WebApplicationException waEx) {
			

			waEx.printStackTrace();
			
		} catch (Exception e) {
			
			Assert.fail(e.getLocalizedMessage());
			e.printStackTrace();
		}
		recipe = null;

	}
		

}
