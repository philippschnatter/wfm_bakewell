package bakewell.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bakewell.beans.Ingredient2Recipe;

/**
 * This Class contains generic CRUD Methods to access the UserDB.
 * @author Alex
 *
 */
public class Ingredient2RecipeDAO {
	
	//Keeps the connection to the database
	private Connection connection;
	//Contains the result set for the select method
	private ResultSet rs;
	//Prepared Statement in order to prevent sql injection
	private PreparedStatement pstmt;
	//query which is built in the methods and sent to the database
	private String query;
	
	private String driver = "org.h2.Driver";
	
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
	public Ingredient2RecipeDAO(String user, String password) {
		  this.user = user;
		  this.password = password;
		  this.url = getUrl();
	}
	
	/**
	 * Constructor, which takes the username and the password in order
	 * to create a connection to the DB
	 * @param user
	 * @param password
	 */
	public Ingredient2RecipeDAO(String url, String user, String password) {
		  this.user = user;
		  this.password = password;
		  this.url = url;
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
	    * Used to insert a new Ingredient2Recipe into the Database
	    * @param c 
	    */
	public Ingredient2Recipe insertIngredient2Recipe(Ingredient2Recipe c) {
		
		//Establish a connection to the DB
		openConnection();
		
		//Build the query
		query = "INSERT INTO INGREDIENT2RECIPE (ID, RECIPE_ID, INGREDIENT_ID , AMOUNT ) VALUES (ID_INGREDIENT2RECIPE_SEQ.nextval, ?, ?, ?)";
		
		try {
			//interpret the query...
			pstmt = connection.prepareStatement(query);
			
			//...and assign the Values of the given Object to the query
			pstmt.setInt(1, c.getRecipe_id());
			pstmt.setInt(2, c.getIngredient_id());
			pstmt.setDouble(3, c.getAmount());
			
			//executes the Insert query on the DB
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//closes the connection to the DB
		closeConnection();
		
		ArrayList<Ingredient2Recipe> result = new ArrayList<Ingredient2Recipe>();
		result = selectIngredient2Recipe(c);
		Ingredient2Recipe b = new Ingredient2Recipe();
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
	    * Generic Method, used to delete a Ingredient2Recipe from the Database
	    * @param c 
	    */
	
	public ArrayList<Ingredient2Recipe> deleteIngredient2Recipe(Ingredient2Recipe c) {
		
		ArrayList<Ingredient2Recipe> result = new ArrayList<Ingredient2Recipe>();
		result = selectIngredient2Recipe(c);
		
		//Establish a connection to the DB
		openConnection();
		 
		//index variable which counts the number of arguments which are not NULL
		int index = 0;
		
		//determines whether the Query shall be sent to the DB
		//in order to prevent it from executing "DELETE FROM \"Ingredient2Recipe\" WHERE 1=1" on the DB
		boolean status = true;
		
		//Builds the query
		query = "DELETE FROM INGREDIENT2RECIPE WHERE 1=1";
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented
		if(c.getId() != null) {query = query + " AND ID = ?"; index++;}
		if(c.getRecipe_id() != null) {query = query + " AND RECIPE_ID = ?"; index++;}
		if(c.getIngredient_id() != null) {query = query + " AND INGREDIENT_ID = ?"; index++;}
		if(c.getAmount() != null) {query = query + " AND AMOUNT = ?"; index++;}
		
		//in case every given attribute is null, the query shall not be executed
		if(index == 0) {status = false;} 	
		query = query+";";
			
		try {
			//prepares the query
			pstmt = connection.prepareStatement(query);
			
			//sets as much attributes to the specified index of the prepared
			//statement as were given in as delete criteria
			if(c.getAmount() != null) {pstmt.setDouble(index--, c.getAmount());}
			if(c.getIngredient_id() != null) {pstmt.setInt(index--, c.getIngredient_id());}
			if(c.getRecipe_id() != null) {pstmt.setInt(index--, c.getRecipe_id());}
			if(c.getId() != null) {pstmt.setInt(index--, c.getId());}
			
			//if at least one attribute is not null, the query shall be executed
			if(status != false) {
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		//closes the connection to the DB
		closeConnection();
		return result;
	}
	
	
	public ArrayList<Ingredient2Recipe> selectIngredient2Recipe(Ingredient2Recipe c) {
		
		//Result ArrayList, which is returned to the invoking routine
		ArrayList<Ingredient2Recipe> result = new ArrayList<Ingredient2Recipe>();
		
		//Establish a connection to the DB
		openConnection();
		
		//Builds the query
		query = "SELECT * FROM INGREDIENT2RECIPE WHERE 1=1";
		
		//index variable which counts the number of arguments which are not NULL
		int index = 0;
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented
		if(c.getId() != null) {query = query + " AND ID = ?"; index++;}
		if(c.getRecipe_id() != null) {query = query + " AND RECIPE_ID = ?"; index++;}
		if(c.getIngredient_id() != null) {query = query + " AND INGREDIENT_ID = ?"; index++;}
		if(c.getAmount() != null) {query = query + " AND AMOUNT = ?"; index++;}
		query = query+";";
		
		try {
			
			//prepares the query
			pstmt = connection.prepareStatement(query);
			
			//sets as much attributes to the specified index of the prepared
			//statement as were given in as delete criteria
			
			if(c.getAmount() != null) {pstmt.setDouble(index--, c.getAmount());}
			if(c.getIngredient_id() != null) {pstmt.setInt(index--, c.getIngredient_id());}
			if(c.getRecipe_id() != null) {pstmt.setInt(index--, c.getRecipe_id());}
			if(c.getId() != null) {pstmt.setInt(index--, c.getId());}
			
			//executes the query on the DB and receives the result set
			rs = pstmt.executeQuery();

			//While the result set contains results, they shall be
			//converted to Ingredient2Recipe Objects and asserted to a result ArrayList
			while(rs.next()) {
				Ingredient2Recipe res = new Ingredient2Recipe();
				res.setId(rs.getInt(1));
				res.setRecipe_id(rs.getInt(2));
				res.setIngredient_id(rs.getInt(3));
				res.setAmount(rs.getDouble(4));
				result.add(res);	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//closes the connection to the DB...
		closeConnection();
		//...and returns the ArrayList
		return result;
	}
	
	
	public ArrayList<Ingredient2Recipe> UpdateIngredient2Recipe(Ingredient2Recipe newC, Ingredient2Recipe oldC) {
		
		//Establish a connection to the DB
		openConnection();
		
		//index variable which counts the number of arguments which are not NULL
		int index = 0;
		
		//Builds the query
		query = "UPDATE INGREDIENT2RECIPE SET ";
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented --> NEW ATTRIBUTES
		if(newC.getId() != null) {query = query + " ID = ?, "; index++;}
		if(newC.getRecipe_id() != null) {query = query + " RECIPE_ID = ?, "; index++;}
		if(newC.getIngredient_id() != null) {query = query + " INGREDIENT_ID = ?, "; index++;}
		if(newC.getAmount() != null) {query = query + " AMOUNT = ? "; index++;}
		
		//The last "," from the subsequent query shall be removed in order to prevent a syntax error
		//furthermore the WHERE Clause with a dummy argument shall be asserted
		query = query.substring(0, query.length()-1) + " WHERE 1=1 ";
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented --> SEARCH CRITERIA
		if(oldC.getId() != null) {query = query + " AND ID = ?"; index++;}
		if(oldC.getRecipe_id() != null) {query = query + " AND RECIPE_ID = ?"; index++;}
		if(oldC.getIngredient_id() != null) {query = query + " AND INGREDIENT_ID = ?"; index++;}
		if(oldC.getAmount() != null) {query = query + " AND AMOUNT = ?"; index++;}
		
		query = query+";";
		
		try {	
			//prepares the query
			pstmt = connection.prepareStatement(query);
			
			//sets as much attributes to the specified index of the prepared
			//statement as were given as argument
			if(oldC.getAmount() != null) {pstmt.setDouble(index--, oldC.getAmount());}
			if(oldC.getIngredient_id() != null) {pstmt.setInt(index--, oldC.getIngredient_id());}
			if(oldC.getRecipe_id() != null) {pstmt.setInt(index--, oldC.getRecipe_id());}
			if(oldC.getId() != null) {pstmt.setInt(index--, oldC.getId());}
			
			if(newC.getAmount() != null) {pstmt.setDouble(index--, newC.getAmount());}
			if(newC.getIngredient_id() != null) {pstmt.setInt(index--, newC.getIngredient_id());}
			if(newC.getRecipe_id() != null) {pstmt.setInt(index--, newC.getRecipe_id());}
			if(newC.getId() != null) {pstmt.setInt(index--, newC.getId());}
			
			//Execute the Update on the DB
			pstmt.executeUpdate();
		} catch (SQLException e) {
			return null;
		}
		//Close the connection to the DB
		closeConnection();
		return selectIngredient2Recipe(newC);
	}
}