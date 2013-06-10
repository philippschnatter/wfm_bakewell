package bakewell.webservice.ingredient;
import bakewell.beans.Recipe;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;




@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML, MediaType.TEXT_HTML, MediaType.APPLICATION_XHTML_XML, MediaType.TEXT_PLAIN, MediaType.WILDCARD})
@Path("/ingredientCalculation")
public interface IngredientRESTService {
	
	@GET
	@Path("calculateIngredient")
	public Recipe calculateIngredient(@QueryParam("id") String id) throws WebApplicationException;
	


}
