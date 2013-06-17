package bakewell.webservice.logistics;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import bakewell.beans.Recipe;

public class RecipePriceRESTServiceImpl implements RecipePriceRESTService {

	@Override
	@GET
	@Path("calculateRecipePrice")
	public Recipe calculatePrice(@QueryParam("id") String id) throws WebApplicationException {
		
		int productid = Integer.valueOf(id);
    
		// TODO logic
		
		return new Recipe();
	}
	
	

}
