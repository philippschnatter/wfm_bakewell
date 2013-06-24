package bakewell.activiti;

import java.util.ArrayList;
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

import bakewell.beans.Product;
import bakewell.beans.Recipe;
import bakewell.db.ProductDAO;
import bakewell.webservice.ingredient.IngredientRESTService;

public class GetProductLabelWsCall implements JavaDelegate
{
	
	// These fields correspond to the name of the <activiti:field /> elements
	// in the bpmn
//	private FixedValue service;
//	private Expression productId;
	

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		// aus activiti
//		Integer productId = (Integer)execution.getVariable("productId");

		String USER = "sa";
		String PW = "";
		
		ProductDAO prodao = new ProductDAO (USER, PW);
				
		Product p = new Product();
		ArrayList<Product> plist = prodao.selectProduct(p);
		String productId = "0";
		
		if (!plist.isEmpty()) {
			p = plist.get(0);
			productId = p.getId().toString();
		}
		
		

		
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
		
		Recipe recipe = null;
		
		try {
			
			recipe = service.calculateIngredient(productId.toString());
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
