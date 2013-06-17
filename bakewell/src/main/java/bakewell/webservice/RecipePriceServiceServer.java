package bakewell.webservice;
import bakewell.webservice.logistics.RecipePriceRESTService;
import bakewell.webservice.logistics.RecipePriceRESTServiceImpl;

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



public class RecipePriceServiceServer {
	
	private Server restserver = null;
	
	
	public void start() {
		
		
		// define a JSON provider and a mapping between REST and JSON namespace's
		JSONProvider<RecipePriceRESTService> jsonProvider = new JSONProvider<RecipePriceRESTService>();
		Map<String, String> map = new HashMap<String, String> ();
		map.put ( "http://rest.ws.wfm", "{}" );
		jsonProvider.setNamespaceMap ( map );
		
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();        
        sf.setResourceClasses(RecipePriceRESTService.class);
        sf.setResourceProvider(RecipePriceRESTService.class, new SingletonResourceProvider(new RecipePriceRESTServiceImpl()));
        sf.setAddress("http://localhost:63082");
                
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
		new RecipePriceServiceServer().start();
	}
	
	

}
