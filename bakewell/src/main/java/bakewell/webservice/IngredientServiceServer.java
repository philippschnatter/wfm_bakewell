package bakewell.webservice;
import bakewell.webservice.ingredient.IngredientRESTService;
import bakewell.webservice.ingredient.IngredientRESTServiceImpl;

import java.util.HashMap;
import java.util.Map;
import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.cxf.jaxrs.provider.json.JSONProvider;



public class IngredientServiceServer {
	
	private Server restserver = null;
	
	
	public void start() {
		
		
		// define a JSON provider and a mapping between REST and JSON namespace's
		JSONProvider<IngredientRESTService> jsonProvider = new JSONProvider<IngredientRESTService>();
		Map<String, String> map = new HashMap<String, String> ();
		map.put ( "http://rest.ws.wfm", "{}" );
		jsonProvider.setNamespaceMap ( map );
		
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();        
        sf.setResourceClasses(IngredientRESTService.class);
        sf.setResourceProvider(IngredientRESTService.class, new SingletonResourceProvider(new IngredientRESTServiceImpl()));
        sf.setAddress("http://localhost:63083");
                
        sf.getInFaultInterceptors().add(new LoggingInInterceptor());
        sf.getOutFaultInterceptors().add(new LoggingOutInterceptor());
        sf.getInInterceptors().add(new LoggingInInterceptor());
        sf.getOutInterceptors().add(new LoggingOutInterceptor());
        sf.setProvider(jsonProvider);
        
        BindingFactoryManager manager = sf.getBus().getExtension(BindingFactoryManager.class);
        JAXRSBindingFactory factory = new JAXRSBindingFactory();
        factory.setBus(sf.getBus());
        manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID, factory);     
                        
        // REST-Service
        this.restserver = sf.create();
        this.restserver.start();
		
	}
	
	public void stop()
	{
		if (restserver != null)
			this.restserver.stop();
	}
	
	public static void main(String[] args) 
	{
		new IngredientServiceServer().start();
	}
	
	

}
