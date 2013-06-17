package bakewell.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bakewell.beans.Ingredient;
import bakewell.beans.Recipe;

/**
 * This Class contains generic CRUD Methods to access the wfDB.
 * @author Alex
 *
 */
public class IngredientDAO {
	
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
	 * Constructor, which takes the username and the password in order
	 * to create a connection to the DB
	 * @param user
	 * @param password
	 */
	public IngredientDAO(String user, String password) {
		  this.user = user;
		  this.password = password;
		  this.url = getUrl();
	}
	
	/**
	 * Constructor, which takes the url, username and the password in order
	 * to create a connection to the DB
	 * @param user
	 * @param password
	 */
	public IngredientDAO(String url, String user, String password) {
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
	    * 
	    * ich habe eine Product ID und brauche alle Ingredients + Mengen (in Gramm) jeweils dazu
	    * 
	    * @param productid
	    */
	public Map<Ingredient, Double> getIngredientsOfProduct(int productid) {
	
		// productid fuer die query verwenden
		// HashMap mit Ingredients und Mengen
		Map<Ingredient, Double> ingredientmap = new HashMap<Ingredient, Double>();
		
		// TODO some DAO magic
		
	
		return ingredientmap;
	}
	
	
	   /**
	    * 
	    * der Webservice gibt ein Recipe-Object (newrecipe) mit den kalkulierten GDA Werten fuer das Label zurueck
	    * dieses recipe object wird aber im WS neu erstellt, da der WS nur die ID vom Product kennt
	    * 
	    * das DAO muss folglich das "richtige" recipe (oldrecipe) mittels Product ID aus der DB holen und dann
	    * die GDA Werte (und nur diese) vom newrecipe ins oldrecipe schreiben.
	    * 
	    * @params newrecipe, productid
	    */
	public void updateRecipeWithProductId(Recipe newrecipe, int productid) {
		
		// TODO logic
		
	}
	
	
	
	
	
	

	
	   /**
	    * Used to insert a new Ingredient into the Database
	    * @param i 
	    */
	public void insertIngredient(Ingredient i) {
		
		//Establish a connection to the DB
		openConnection();
		
		//Build the query
		query = "INSERT INTO INGREDIENT (ID, NAME, AMOUNT , FAT , SUGAR , CALORIES ) VALUES (ID_INGREDIENT_SEQ.nextval, ?, ?, ?, ?, ?)";
		
		try {
			//interpret the query...
			pstmt = connection.prepareStatement(query);
			
			//...and assign the Values of the given Object to the query
			pstmt.setString(1, i.getName());
			pstmt.setDouble(2, i.getAmount());
			pstmt.setDouble(3, i.getFat());
			pstmt.setDouble(4, i.getSugar());
			pstmt.setDouble(5, i.getCalories());
			
			//executes the Insert query on the DB
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//closes the connection to the DB
		closeConnection();
	}
	
	   /**
	    * Generic Method, used to delete a Ingredient from the Database
	    * @param i
	    */
	
	public void deleteIngredient(Ingredient i) {
		
		//Establish a connection to the DB
		openConnection();
		 
		//index variable which counts the number of arguments which are not NULL
		int index = 0;
		
		//determines whether the Query shall be sent to the DB
		//in order to prevent it from executing "DELETE FROM \"Ingredient\" WHERE 1=1" on the DB
		boolean status = true;
		
		//Builds the query
		query = "DELETE FROM INGREDIENT WHERE 1=1";
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented
		if(i.getId() != null) {query = query + " AND ID = ?"; index++;}
		if(i.getName() != null) {query = query + " AND NAME = ?"; index++;}
		if(i.getAmount() != null) {query = query + " AND AMOUNT = ?"; index++;}
		if(i.getFat() != null) {query = query + " AND FAT = ?"; index++;}
		if(i.getSugar() != null) {query = query + " AND SUGAR = ?"; index++;}
		if(i.getCalories() != null) {query = query + " AND CALORIES = ?"; index++;}

		
		//in case every given attribute is null, the query shall not be executed
		if(index == 0) {status = false;} 	
		query = query+";";
			
		try {
			//prepares the query
			pstmt = connection.prepareStatement(query);
			
			//sets as much attributes to the specified index of the prepared
			//statement as were given in as delete criteria
			if(i.getCalories() != null) {pstmt.setDouble(index--, i.getCalories());}
			if(i.getSugar() != null) {pstmt.setDouble(index--, i.getSugar());}
			if(i.getFat() != null) {pstmt.setDouble(index--, i.getFat());}
			if(i.getAmount() != null) {pstmt.setDouble(index--, i.getAmount());}
			if(i.getName() != null) {pstmt.setString(index--, i.getName());}
			if(i.getId() != null) {pstmt.setInt(index--, i.getId());}

			
			//if at least one attribute is not null, the query shall be executed
			if(status != false) {
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//closes the connection to the DB
		closeConnection();
	}
	
	
	public ArrayList<Ingredient> selectIngredient(Ingredient i) {
		
		//Result ArrayList, which is returned to the invoking routine
		ArrayList<Ingredient> result = new ArrayList<Ingredient>();
		
		//Establish a connection to the DB
		openConnection();
		
		//Builds the query
		query = "SELECT * FROM INGREDIENT WHERE 1=1";
		
		//index variable which counts the number of arguments which are not NULL
		int index = 0;
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented
		if(i.getId() != null) {query = query + " AND ID = ?"; index++;}
		if(i.getName() != null) {query = query + " AND NAME = ?"; index++;}
		if(i.getAmount() != null) {query = query + " AND AMOUNT = ?"; index++;}
		if(i.getFat() != null) {query = query + " AND FAT = ?"; index++;}
		if(i.getSugar() != null) {query = query + " AND SUGAR = ?"; index++;}
		if(i.getCalories() != null) {query = query + " AND CALORIES = ?"; index++;}
		query = query+";";
		
		try {
			
			//prepares the query
			pstmt = connection.prepareStatement(query);
			
			//sets as much attributes to the specified index of the prepared
			//statement as were given in as delete criteria
			
			if(i.getCalories() != null) {pstmt.setDouble(index--, i.getCalories());}
			if(i.getSugar() != null) {pstmt.setDouble(index--, i.getSugar());}
			if(i.getFat() != null) {pstmt.setDouble(index--, i.getFat());}
			if(i.getAmount() != null) {pstmt.setDouble(index--, i.getAmount());}
			if(i.getName() != null) {pstmt.setString(index--, i.getName());}
			if(i.getId() != null) {pstmt.setInt(index--, i.getId());}
			
			//executes the query on the DB and receives the result set
			rs = pstmt.executeQuery();

			//While the result set contains results, they shall be
			//converted to Ingredient Objects and asserted to a result ArrayList
			while(rs.next()) {
				Ingredient res = new Ingredient();
				res.setId(rs.getInt(1));
				res.setName(rs.getString(2));
				res.setAmount(rs.getDouble(3));
				res.setFat(rs.getDouble(4));
				res.setSugar(rs.getDouble(5));
				res.setCalories(rs.getDouble(6));
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
	
	
	public void UpdateIngredient(Ingredient newI, Ingredient oldI) {
		
		//Establish a connection to the DB
		openConnection();
		
		//index variable which counts the number of arguments which are not NULL
		int index = 0;
		
		//Builds the query
		query = "UPDATE INGREDIENT SET ";
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented --> NEW ATTRIBUTES
		if(newI.getId() != null) {query = query + " AND ID = ?"; index++;}
		if(newI.getName() != null) {query = query + " AND NAME = ?"; index++;}
		if(newI.getAmount() != null) {query = query + " AND AMOUNT = ?"; index++;}
		if(newI.getFat() != null) {query = query + " AND FAT = ?"; index++;}
		if(newI.getSugar() != null) {query = query + " AND SUGAR = ?"; index++;}
		if(newI.getCalories() != null) {query = query + " AND CALORIES = ?"; index++;}
		
		//The last "," from the subsequent query shall be removed in order to prevent a syntax error
		//furthermore the WHERE Clause with a dummy argument shall be asserted
		query = query.substring(0, query.length()-1) + " WHERE 1=1 ";
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented --> SEARCH CRITERIA
		if(oldI.getId() != null) {query = query + " AND ID = ?,"; index++;}
		if(oldI.getName() != null) {query = query + " AND NAME = ?"; index++;}
		if(oldI.getAmount() != null) {query = query + " AND AMOUNT = ?"; index++;}
		if(oldI.getFat() != null) {query = query + " AND FAT = ?"; index++;}
		if(oldI.getSugar() != null) {query = query + " AND SUGAR = ?"; index++;}
		if(oldI.getCalories() != null) {query = query + " AND CALORIES = ?"; index++;}
		
		query = query+";";
		
		try {	
			//prepares the query
			pstmt = connection.prepareStatement(query);
			
			//sets as much attributes to the specified index of the prepared
			//statement as were given as argument
			if(oldI.getCalories() != null) {pstmt.setDouble(index--, oldI.getCalories());}
			if(oldI.getSugar() != null) {pstmt.setDouble(index--, oldI.getSugar());}
			if(oldI.getFat() != null) {pstmt.setDouble(index--, oldI.getFat());}
			if(oldI.getAmount() != null) {pstmt.setDouble(index--, oldI.getAmount());}
			if(oldI.getName() != null) {pstmt.setString(index--, oldI.getName());}
			if(oldI.getId() != null) {pstmt.setInt(index--, oldI.getId());}
			
			if(newI.getCalories() != null) {pstmt.setDouble(index--, newI.getCalories());}
			if(newI.getSugar() != null) {pstmt.setDouble(index--, newI.getSugar());}
			if(newI.getFat() != null) {pstmt.setDouble(index--, newI.getFat());}
			if(newI.getAmount() != null) {pstmt.setDouble(index--, newI.getAmount());}
			if(newI.getName() != null) {pstmt.setString(index--, newI.getName());}
			if(newI.getId() != null) {pstmt.setInt(index--, newI.getId());}
			
			//Execute the Update on the DB
			pstmt.executeUpdate();
		} catch (SQLException e) {
		}
		//Close the connection to the DB
		closeConnection();
	}
}