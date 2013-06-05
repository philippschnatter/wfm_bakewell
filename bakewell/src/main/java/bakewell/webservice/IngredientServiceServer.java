package bakewell.webservice;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.provider.json.JSONProvider;

import bakewell.webservice.ingredient.IngredientRESTService;


public class IngredientServiceServer {
	
	private Server restserver = null;
	
	
	public void start() {
		
		
		// define a JSON provider and a mapping between REST and JSON namespace's
		JSONProvider<IngredientRESTService> jsonProvider = new JSONProvider<IngredientRESTService>();
		Map<String, String> map = new HashMap<String, String> ();
		map.put ( "http://rest.ws.wfm", "{}" );
		jsonProvider.setNamespaceMap ( map );
		
		
		
	}
	

}
