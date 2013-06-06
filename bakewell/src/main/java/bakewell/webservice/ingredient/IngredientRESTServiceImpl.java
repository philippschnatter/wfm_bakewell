package bakewell.webservice.ingredient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

import bakewell.beans.Ingredient;

public class IngredientRESTServiceImpl implements IngredientRESTService {

	@Override
	@GET
	@Path("calculateIngredient")
	public Ingredient calculateIngredient(@QueryParam("id") String id,
			@QueryParam("name") String name, @QueryParam("fat") String fat,
			@QueryParam("sugar") String sugar,
			@QueryParam("calories") String calories,
			@QueryParam("protein") String protein)
			throws WebApplicationException {
		
		// some magic still missing here
		// not clear what to return
		name = name+"_changed";

		return new Ingredient(name, new Double(fat), new Double(sugar), new Double(calories), new Double(protein));
	}
	
	

}
