package bakewell.webservice.ingredient;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import bakewell.beans.Ingredient;


import bakewell.beans.Recipe;

public class IngredientRESTServiceImpl implements IngredientRESTService {

	@Override
	@GET
	@Path("calculateIngredient")
	public Recipe calculateIngredient(@QueryParam("id") String id) throws WebApplicationException {
		
    
		// DAO: ich habe eine Product ID und brauche alle Ingredients + Mengen jeweils dazu
		HashMap<Ingredient, Double> ingredientmap = ProductDAO.getIngredientDataByProductID();
		

		return new Recipe("some name", "some description",234.0, 234.0, 243.0, 54.0, 34.0, 34.0);
	}
	
	

}
