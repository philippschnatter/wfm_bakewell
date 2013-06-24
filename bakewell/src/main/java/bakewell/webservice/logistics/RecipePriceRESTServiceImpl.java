package bakewell.webservice.logistics;


import java.util.Map;

import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

import bakewell.beans.Ingredient;
import bakewell.beans.Recipe;
import bakewell.db.ProductDAO;
import bakewell.db.RecipeDAO;

public class RecipePriceRESTServiceImpl implements RecipePriceRESTService {

	private ProductDAO prodao;
	private RecipeDAO recdao;
	private final String USER = "sa";
	private final String PW = "";
	
	public RecipePriceRESTServiceImpl() {
		
		this.prodao = new ProductDAO (USER, PW);
		this.recdao = new RecipeDAO(USER, PW);
	}
	
	@Override
	public Recipe calculatePrice(@QueryParam("id") String id) throws WebApplicationException {
		
		int productid = Integer.valueOf(id);
	    
		// DAO: ich habe eine Product ID und brauche alle Ingredients + Mengen (in Gramm) jeweils dazu
		// TODO DAO needed!!!!!

//		Map<Ingredient, Double> ingredientmap = new HashMap<Ingredient, Double>();
		Map<Ingredient, Double> ingredientmap = prodao.selectIngredientsOfProduct(productid);
		
		// statt der DAO temporaer eigene objekte
//		Ingredient ing_mehl = new Ingredient("Mehl", 897.0, 43.0, 10.0, 20.0, 7.0, 2.0, 23.0);
//		Ingredient ing_ei = new Ingredient("Ei", 223.0, 10.0, 30.0, 9.0, 21.0, 3.0, 20.0);
//		Ingredient ing_schokolade = new Ingredient("Schokolade", 893.0, 15.0, 50.0, 5.0, 5.0, 2.0, 27.0);
//		
//		ingredientmap.put(ing_mehl, 250.0);
//		ingredientmap.put(ing_ei, 120.0);
//		ingredientmap.put(ing_schokolade, 80.0);
		// end temp stuff
		
		double totalpriceincents = 0.0;
		
		// iterate through hashmap
		for (Map.Entry<Ingredient, Double> entry : ingredientmap.entrySet()) {
			    
		    Ingredient ing_temp = entry.getKey();
		    double amount = entry.getValue();
		    
		    totalpriceincents = totalpriceincents + ing_temp.getPrice() * amount / 100;

		}
		
		Recipe oldrec = prodao.selectRecipeByProductId(productid);
		Recipe newrec = new Recipe(oldrec.getName(), oldrec.getDescription(), oldrec.getAllgda_energy(), 
				oldrec.getAllgda_protein(), oldrec.getAllgda_carbo(), oldrec.getAllgda_fat(), 
				oldrec.getAllgda_fiber(), oldrec.getAllgda_sodium(), totalpriceincents);
		
		recdao.UpdateRecipe(newrec, oldrec);
		
		return newrec;
	}
	
	

}
