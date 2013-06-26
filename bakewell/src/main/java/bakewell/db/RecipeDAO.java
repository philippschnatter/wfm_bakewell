package bakewell.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bakewell.beans.Ingredient2Recipe;
import bakewell.beans.Recipe;

/**
 * This Class contains generic CRUD Methods to access the UserDB.
 * @author Alex
 *
 */
public class RecipeDAO {
	
	//Keeps the connection to the database
	private Connection connection;
	//Contains the result set for the select method
	private ResultSet rs;
	//Prepared Statement in order to prevent sql injection
	private PreparedStatement pstmt;
	//query which is built in the methods and sent to the database
	private String query;
	
	private String driver = "org.h2.Driver";
	private Ingredient2RecipeDAO iDAO;
	
	//Name of the DB
	private String url = "";
	private String user = "";
	private String password = "";  

	/**
	 * Constructor, which takes the url, username and the password in order
	 * to create a connection to the DB
	 * @param user
	 * @param password
	 */
	public RecipeDAO(String user, String password) {
		  this.user = user;
		  this.password = password;
		  this.url = getUrl();
		  iDAO = new Ingredient2RecipeDAO(user, password);
	}
	
	/**
	 * Constructor, which takes the username and the password in order
	 * to create a connection to the DB
	 * @param user
	 * @param password
	 */
	public RecipeDAO(String url, String user, String password) {
		  this.user = user;
		  this.password = password;
		  this.url = url;
		  iDAO = new Ingredient2RecipeDAO(user, password);
	}
	
	/**
	 * Opens the connection to the DB and is used in the four CRUD methods before executing the query to the DB
	 */
	private void openConnection () {
		try {
			    Class.forName (driver);
		}
		catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		try {
		      connection = DriverManager.getConnection (url, user, password);
		}
		catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Closes the connection to the DB, is used by the four CRUD methods after executing a query to the DB
	 */
	  private void closeConnection () {
		    try {
		      connection.close ();
		    }
		    catch (SQLException e) {
		    }
	  }
		 
	  /**
	   * Is used to build the URL for connecting to the DB
	   * Invoked by the openConnection Method
	   * @return
	   */
	   private String getUrl () {
		    return ("jdbc:h2:file:src/main/resources/db/wfDB");
	   }

	/**
	 * inserts a new Recipe without ID and connects the ingredients via the ingredient2recipe table
	 * @param r ... might be a Recipe, WITH OR WITHOUT an ID
	 * @param ingredients ... a list with ingredients and the respective Amount, the recipe_id does 
	 * not have to be set
	 * @return ... a Recipe with the complete Ingredient2Recipe list attached
	 */
	public ArrayList<Recipe> insertNewRecipe(Recipe r, ArrayList<Ingredient2Recipe> ingredients) {
		
		Recipe recipe = r;
		
		//if the Recipe does not exist, and does not have an ID
		if(r.getId() == null) {
			recipe = insertRecipe(r);
		//if the Recipe has an ID
		} else {
			recipe = selectRecipe(r).get(0);
		}
		
		//run through the given Ingredient2Recipe...
		for(int i = 0; i < ingredients.size(); i++) {
			//and connect the Recipe ID to each ingredient...
			ingredients.get(i).setRecipe_id(recipe.getId());
			//furthermore add each Ingredient2Recipe to the DB
			iDAO.insertIngredient2Recipe(ingredients.get(i));
		}
		
		//in the end the finished Recipe with all Ingredien2Recipe Connections shall be returned
		return selectRecipe(recipe);
	}
	   /**
	    * Used to insert a new Recipe into the Database
	    * @param c 
	    */
	public Recipe insertRecipe(Recipe c) {
		
		//Establish a connection to the DB
		openConnection();
		
		//Build the query
		query = "INSERT INTO RECIPE (ID, NAME, DESCRIPTION , ALLGDA_ENERGY , ALLGDA_PROTEIN , ALLGDA_CARBO , ALLGDA_FAT , ALLGDA_FIBER, ALLGDA_SODIUM , TOTALPRICE ) VALUES (ID_RECIPE_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?)";		
		
		try {
			//interpret the query...
			pstmt = connection.prepareStatement(query);
			
			//...and assign the Values of the given Object to the query
			pstmt.setString(1, c.getName());
			pstmt.setString(2, c.getDescription());
			pstmt.setDouble(3, c.getAllgda_energy());
			pstmt.setDouble(4, c.getAllgda_protein());
			pstmt.setDouble(5, c.getAllgda_carbo());
			pstmt.setDouble(6, c.getAllgda_fat());
			pstmt.setDouble(7, c.getAllgda_fiber());
			pstmt.setDouble(8, c.getAllgda_sodium());
			pstmt.setDouble(9, c.getTotalprice());
			
			//executes the Insert query on the DB
			pstmt.executeUpdate();
			
			for(int i = 0; i < c.getIngredients().size(); i++) {
				iDAO.insertIngredient2Recipe(c.getIngredients().get(i));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		//closes the connection to the DB
		closeConnection();
		
		ArrayList<Recipe> result = new ArrayList<Recipe>();
		result = selectRecipe(c);
		Recipe b = new Recipe();
		int index = 0;
		for(int i = 0; i < result.size(); i++) {
			if(result.get(i).getId() > index) {
				b = result.get(i);
				index = result.get(i).getId();
			}
		}
		
		return b;
	}
	
	   /**
	    * Generic Method, used to delete a Recipe from the Database
	    * @param c 
	    */
	
	public ArrayList<Recipe> deleteRecipe(Recipe c) {
		
		ArrayList<Recipe> result = new ArrayList<Recipe>();
		result = selectRecipe(c);

		for(int i = 0; i < result.size(); i++) {
			Recipe r = result.get(i);
			for(int j = 0; j < r.getIngredients().size(); i++) {
				iDAO.deleteIngredient2Recipe(r.getIngredients().get(i));
			}
		}
		
		//Establish a connection to the DB
		openConnection();
		 
		//index variable which counts the number of arguments which are not NULL
		int index = 0;
		
		//determines whether the Query shall be sent to the DB
		//in order to prevent it from executing "DELETE FROM \"Recipe\" WHERE 1=1" on the DB
		boolean status = true;
		
		//Builds the query
		query = "DELETE FROM RECIPE WHERE 1=1";
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented
		if(c.getId() != null) {query = query + " AND ID = ?"; index++;}
		if(c.getName() != null) {query = query + " AND NAME = ?"; index++;}
		if(c.getDescription() != null) {query = query + " AND DESCRIPTION = ?"; index++;}
		if(c.getAllgda_energy() != null) {query = query + " AND ALLGDA_ENERGY = ?"; index++;}
		if(c.getAllgda_protein() != null) {query = query + " AND ALLGDA_PROTEIN = ?"; index++;}
		if(c.getAllgda_carbo() != null) {query = query + " AND ALLGDA_CARBO = ?"; index++;}
		if(c.getAllgda_fat() != null) {query = query + " AND ALLGDA_FAT = ?"; index++;}
		if(c.getAllgda_fiber() != null) {query = query + " AND ALLGDA_FIBER = ?"; index++;}
		if(c.getAllgda_sodium() != null) {query = query + " AND ALLGDA_SODIUM = ?"; index++;}
		if(c.getTotalprice() != null) {query = query + " AND TOTALPRICE = ?"; index++;}
		
		//in case every given attribute is null, the query shall not be executed
		if(index == 0) {status = false;} 	
		query = query+";";
			
		try {
			//prepares the query
			pstmt = connection.prepareStatement(query);
			
			//sets as much attributes to the specified index of the prepared
			//statement as were given in as delete criteria
			if(c.getTotalprice() != null) {pstmt.setDouble(index--, c.getTotalprice());}
			if(c.getAllgda_sodium() != null) {pstmt.setDouble(index--, c.getAllgda_sodium());}
			if(c.getAllgda_fiber() != null) {pstmt.setDouble(index--, c.getAllgda_fiber());}
			if(c.getAllgda_fat() != null) {pstmt.setDouble(index--, c.getAllgda_fat());}
			if(c.getAllgda_carbo() != null) {pstmt.setDouble(index--, c.getAllgda_carbo());}
			if(c.getAllgda_protein() != null) {pstmt.setDouble(index--, c.getAllgda_protein());}
			if(c.getAllgda_energy() != null) {pstmt.setDouble(index--, c.getAllgda_energy());}
			if(c.getDescription() != null) {pstmt.setString(index--, c.getDescription());}
			if(c.getName() != null) {pstmt.setString(index--, c.getName());}
			if(c.getId() != null) {pstmt.setInt(index--, c.getId());}
			
			//if at least one attribute is not null, the query shall be executed
			if(status != false) {
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//closes the connection to the DB
		closeConnection();
		return result;
	}
	
	
	public ArrayList<Recipe> selectRecipe(Recipe c) {
		
		//Result ArrayList, which is returned to the invoking routine
		ArrayList<Recipe> result = new ArrayList<Recipe>();
		
		//Establish a connection to the DB
		openConnection();
		
		//Builds the query
		query = "SELECT * FROM RECIPE WHERE 1=1";
		
		//index variable which counts the number of arguments which are not NULL
		int index = 0;
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented
		if(c.getId() != null) {query = query + " AND ID = ?"; index++;}
		if(c.getName() != null) {query = query + " AND NAME = ?"; index++;}
		if(c.getDescription() != null) {query = query + " AND DESCRIPTION = ?"; index++;}
		if(c.getAllgda_energy() != null) {query = query + " AND ALLGDA_ENERGY = ?"; index++;}
		if(c.getAllgda_protein() != null) {query = query + " AND ALLGDA_PROTEIN = ?"; index++;}
		if(c.getAllgda_carbo() != null) {query = query + " AND ALLGDA_CARBO = ?"; index++;}
		if(c.getAllgda_fat() != null) {query = query + " AND ALLGDA_FAT = ?"; index++;}
		if(c.getAllgda_fiber() != null) {query = query + " AND ALLGDA_FIBER = ?"; index++;}
		if(c.getAllgda_sodium() != null) {query = query + " AND ALLGDA_SODIUM = ?"; index++;}
		if(c.getTotalprice() != null) {query = query + " AND TOTALPRICE = ?"; index++;}
		query = query+";";
		
		try {
			
			//prepares the query
			pstmt = connection.prepareStatement(query);
			
			//sets as much attributes to the specified index of the prepared
			//statement as were given in as delete criteria
			
			if(c.getTotalprice() != null) {pstmt.setDouble(index--, c.getTotalprice());}
			if(c.getAllgda_sodium() != null) {pstmt.setDouble(index--, c.getAllgda_sodium());}
			if(c.getAllgda_fiber() != null) {pstmt.setDouble(index--, c.getAllgda_fiber());}
			if(c.getAllgda_fat() != null) {pstmt.setDouble(index--, c.getAllgda_fat());}
			if(c.getAllgda_carbo() != null) {pstmt.setDouble(index--, c.getAllgda_carbo());}
			if(c.getAllgda_protein() != null) {pstmt.setDouble(index--, c.getAllgda_protein());}
			if(c.getAllgda_energy() != null) {pstmt.setDouble(index--, c.getAllgda_energy());}
			if(c.getDescription() != null) {pstmt.setString(index--, c.getDescription());}
			if(c.getName() != null) {pstmt.setString(index--, c.getName());}
			if(c.getId() != null) {pstmt.setInt(index--, c.getId());}
			
			//executes the query on the DB and receives the result set
			rs = pstmt.executeQuery();

			//While the result set contains results, they shall be
			//converted to Recipe Objects and asserted to a result ArrayList
			while(rs.next()) {
				Recipe res = new Recipe();
				res.setId(rs.getInt(1));
				res.setName(rs.getString(2));
				res.setDescription(rs.getString(3));
				res.setAllgda_energy(rs.getDouble(4));
				res.setAllgda_protein(rs.getDouble(5));
				res.setAllgda_carbo(rs.getDouble(6));
				res.setAllgda_fat(rs.getDouble(7));
				res.setAllgda_fiber(rs.getDouble(8));
				res.setAllgda_sodium(rs.getDouble(9));
				res.setTotalprice(rs.getDouble(10));
				result.add(res);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//closes the connection to the DB...
		closeConnection();
		
		for(int i = 0; i < result.size(); i++) {
			Ingredient2Recipe searchEntity = new Ingredient2Recipe(result.get(i).getId(), null, null);
			result.get(i).setIngredients(iDAO.selectIngredient2Recipe(searchEntity));
		}
		
		//...and returns the ArrayList
		return result;
	}
	
	public ArrayList<Recipe> UpdateRecipe(Recipe newR, Recipe oldR) {
		
		//Establish a connection to the DB
		openConnection();
		
		//index variable which counts the number of arguments which are not NULL
		int index = 0;
		
		//Builds the query
		query = "UPDATE RECIPE SET ";
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented --> NEW ATTRIBUTES
		if(newR.getId() != null) {query = query + " ID = ?,"; index++;}
		if(newR.getName() != null) {query = query + " NAME = ?,"; index++;}
		if(newR.getDescription() != null) {query = query + " DESCRIPTION = ?,"; index++;}
		if(newR.getAllgda_energy() != null) {query = query + " ALLGDA_ENERGY = ?,"; index++;}
		if(newR.getAllgda_protein() != null) {query = query + " ALLGDA_PROTEIN = ?,"; index++;}
		if(newR.getAllgda_carbo() != null) {query = query + " ALLGDA_CARBO = ?,"; index++;}
		if(newR.getAllgda_fat() != null) {query = query + " ALLGDA_FAT = ?,"; index++;}
		if(newR.getAllgda_fiber() != null) {query = query + " ALLGDA_FIBER = ?,"; index++;}
		if(newR.getAllgda_sodium() != null) {query = query + " ALLGDA_SODIUM = ?,"; index++;}
		if(newR.getTotalprice() != null) {query = query + " TOTALPRICE = ? ,"; index++;}
		
		//The last "," from the subsequent query shall be removed in order to prevent a syntax error
		//furthermore the WHERE Clause with a dummy argument shall be asserted
		query = query.substring(0, query.length()-1) + " WHERE 1=1 ";
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented --> SEARCH CRITERIA
		if(oldR.getId() != null) {query = query + " AND ID = ?"; index++;}
		if(oldR.getName() != null) {query = query + " AND NAME = ?"; index++;}
		if(oldR.getDescription() != null) {query = query + " AND DESCRIPTION = ?"; index++;}
		if(oldR.getAllgda_energy() != null) {query = query + " AND ALLGDA_ENERGY = ?"; index++;}
		if(oldR.getAllgda_protein() != null) {query = query + " AND ALLGDA_PROTEIN = ?"; index++;}
		if(oldR.getAllgda_carbo() != null) {query = query + " AND ALLGDA_CARBO = ?"; index++;}
		if(oldR.getAllgda_fat() != null) {query = query + " AND ALLGDA_FAT = ?"; index++;}
		if(oldR.getAllgda_fiber() != null) {query = query + " AND ALLGDA_FIBER = ?"; index++;}
		if(oldR.getAllgda_sodium() != null) {query = query + " AND ALLGDA_SODIUM = ?"; index++;}
		if(oldR.getTotalprice() != null) {query = query + " AND TOTALPRICE = ?"; index++;}
		
		query = query+";";
		
		try {	
			//prepares the query
			pstmt = connection.prepareStatement(query);
			
			//sets as much attributes to the specified index of the prepared
			//statement as were given as argument
			if(oldR.getTotalprice() != null) {pstmt.setDouble(index--, oldR.getTotalprice());}
			if(oldR.getAllgda_sodium() != null) {pstmt.setDouble(index--, oldR.getAllgda_sodium());}
			if(oldR.getAllgda_fiber() != null) {pstmt.setDouble(index--, oldR.getAllgda_fiber());}
			if(oldR.getAllgda_fat() != null) {pstmt.setDouble(index--, oldR.getAllgda_fat());}
			if(oldR.getAllgda_carbo() != null) {pstmt.setDouble(index--, oldR.getAllgda_carbo());}
			if(oldR.getAllgda_protein() != null) {pstmt.setDouble(index--, oldR.getAllgda_protein());}
			if(oldR.getAllgda_energy() != null) {pstmt.setDouble(index--, oldR.getAllgda_energy());}
			if(oldR.getDescription() != null) {pstmt.setString(index--, oldR.getDescription());}
			if(oldR.getName() != null) {pstmt.setString(index--, oldR.getName());}
			if(oldR.getId() != null) {pstmt.setInt(index--, oldR.getId());}
			
			if(newR.getTotalprice() != null) {pstmt.setDouble(index--, newR.getTotalprice());}
			if(newR.getAllgda_sodium() != null) {pstmt.setDouble(index--, newR.getAllgda_sodium());}
			if(newR.getAllgda_fiber() != null) {pstmt.setDouble(index--, newR.getAllgda_fiber());}
			if(newR.getAllgda_fat() != null) {pstmt.setDouble(index--, newR.getAllgda_fat());}
			if(newR.getAllgda_carbo() != null) {pstmt.setDouble(index--, newR.getAllgda_carbo());}
			if(newR.getAllgda_protein() != null) {pstmt.setDouble(index--, newR.getAllgda_protein());}
			if(newR.getAllgda_energy() != null) {pstmt.setDouble(index--, newR.getAllgda_energy());}
			if(newR.getDescription() != null) {pstmt.setString(index--, newR.getDescription());}
			if(newR.getName() != null) {pstmt.setString(index--, newR.getName());}
			if(newR.getId() != null) {pstmt.setInt(index--, newR.getId());}
			
			//Execute the Update on the DB
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		//Close the connection to the DB
		closeConnection();
		return selectRecipe(newR);
	}
}