package bakewell.activiti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import junit.framework.Assert;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
//import org.activiti.engine.impl.el.Expression;
//import org.activiti.engine.impl.el.FixedValue;
import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.provider.json.JSONProvider;

import bakewell.beans.Product;
import bakewell.beans.Recipe;
import bakewell.db.ProductDAO;
import bakewell.webservice.logistics.RecipePriceRESTService;

public class QuoteIngredientPricesWsCall implements JavaDelegate
{
	// These fields correspond to the name of the <activiti:field /> elements
	// in the bpmn
//	private FixedValue service;
//	private Expression productId;
	

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
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
			
			recipe = service.calculatePrice(productId);
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
