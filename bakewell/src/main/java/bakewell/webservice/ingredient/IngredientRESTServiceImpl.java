package bakewell.webservice.ingredient;

import java.util.HashMap;
import java.util.Map;

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
		
		int productid = Integer.valueOf(id);
    
		// DAO: ich habe eine Product ID und brauche alle Ingredients + Mengen (in Gramm) jeweils dazu
		// TODO DAO needed!!!!!
		System.out.println("product id: " + productid); // test
		Map<Ingredient, Double> ingredientmap = new HashMap<Ingredient, Double>();
		
		// statt der DAO temporaer eigene objekte
		Ingredient ing_mehl = new Ingredient("Mehl", 897.0, 43.0, 10.0, 20.0, 7.0, 2.0);
		Ingredient ing_ei = new Ingredient("Ei", 223.0, 10.0, 30.0, 9.0, 21.0, 3.0);
		Ingredient ing_schokolade = new Ingredient("Schokolade", 893.0, 15.0, 50.0, 5.0, 5.0, 2.0);
		
		ingredientmap.put(ing_mehl, 250.0);
		ingredientmap.put(ing_ei, 120.0);
		ingredientmap.put(ing_schokolade, 80.0);
		// end temp stuff
		
		// Values for all ingredients
		double allgda_energy = 0;
		double allgda_protein = 0;
		double allgda_carbo = 0;
		double allgda_fat = 0;
		double allgda_fiber = 0;
		double allgda_sodium = 0;
		
		double totalamountingram = 0;
		
		
		// iterate through hashmap
		for (Map.Entry<Ingredient, Double> entry : ingredientmap.entrySet()) {
			    
		    Ingredient ing_temp = entry.getKey();
		    double amount = entry.getValue();
		    
		    allgda_energy = allgda_energy + ing_temp.getAllgda_energy().doubleValue() * amount / 100;
		    allgda_protein = allgda_protein + ing_temp.getGda_protein().doubleValue() * amount / 100;
			allgda_carbo = allgda_carbo + ing_temp.getGda_carbo().doubleValue() * amount / 100;
			allgda_fat = allgda_fat + ing_temp.getGda_fat().doubleValue() * amount / 100;
			allgda_fiber = allgda_fiber + ing_temp.getGda_fiber() * amount / 100;
			allgda_sodium = allgda_sodium + ing_temp.getGda_sodium() * amount / 100;
			
			totalamountingram = totalamountingram + amount;
			
		}
		System.out.println(allgda_energy);
		
		// normalize to per 100 gram
		allgda_energy = allgda_energy / totalamountingram * 100;
		allgda_protein = allgda_protein / totalamountingram * 100;
		allgda_carbo = allgda_carbo / totalamountingram * 100;
		allgda_fat = allgda_fat / totalamountingram * 100;
		allgda_fiber = allgda_fiber / totalamountingram * 100;
		allgda_sodium = allgda_sodium / totalamountingram * 100;
		
		System.out.println(allgda_energy);
		
		// compute percentages of recommended GDA
		allgda_energy = allgda_energy / GdaTable.RECGDA_ENERGY.getGramPerDay() * 100;
		allgda_protein = allgda_protein / GdaTable.RECGDA_PROTEIN.getGramPerDay() * 100;
		allgda_carbo = allgda_carbo / GdaTable.RECGDA_CARBON.getGramPerDay() * 100;
		allgda_fat = allgda_fat / GdaTable.RECGDA_FAT.getGramPerDay() * 100;
		allgda_fiber = allgda_fiber / GdaTable.RECGDA_FIBER.getGramPerDay() * 100;
		allgda_sodium = allgda_sodium / GdaTable.RECGDA_SODIUM.getGramPerDay() * 100;
		
		System.out.println(allgda_energy);
		
		Recipe rec = new Recipe("some name", "some description", allgda_energy, allgda_protein, allgda_carbo, allgda_fat, allgda_fiber, allgda_sodium);
		return rec;
	}
	
	

}
