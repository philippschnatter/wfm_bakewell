package bakewell.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bakewell.beans.Ingredient;
import bakewell.beans.Ingredient2Recipe;
import bakewell.beans.Ingredient2RecipeNonPersistent;

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
	
	private Ingredient2RecipeDAO i2rDAO = null;
	
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
	public IngredientDAO(String user, String password) {
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
	    * Holt alle Ingredients und Ingredients2Recipe aus der Datenbank heraus und fügt sie in ein Objekt
	    * Ingredient2RecipeNonPersistent zusammen
	    * @return eine Liste aller Ingredient2Recipe Connections mit dem entsprechenden Ingredient Namen
	    */
	   public ArrayList<Ingredient2RecipeNonPersistent> selectAllIngredient2RecipeNonPersistent() {
		  //Holt alle Ingredients mittels IngredientDAO aus der DB
		  ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
		  ingredients = selectIngredient(new Ingredient());
		  
		  //Holt alle Ingredient2Recipe Connections mittels Ingredient2RecipeDAO aus der DB
		  ArrayList<Ingredient2Recipe> ingredient2recipe = new ArrayList<Ingredient2Recipe>();
		  i2rDAO = new Ingredient2RecipeDAO("jdbc:h2:file:C:/Users/stefan/Documents/Workflow/git/bakewell/src/main/resources/db/wfDB", user, password);
		  ingredient2recipe = i2rDAO.selectIngredient2Recipe(new Ingredient2Recipe());
		  
		  //Ergebnisliste vom Typ Ingredient2RecipeNonPersistent
		  ArrayList<Ingredient2RecipeNonPersistent> result = new ArrayList<Ingredient2RecipeNonPersistent>();
		  //Durchlaufe alle Ingredient2Recipe Connections
		  for(int i = 0; i < ingredient2recipe.size(); i++) {
			  //... und fuege alle drei Attribute in ein Temp Element
			  Ingredient2RecipeNonPersistent temp = new Ingredient2RecipeNonPersistent();
			  temp.setRecipe_id(ingredient2recipe.get(i).getRecipe_id());
			  temp.setIngredient_id(ingredient2recipe.get(i).getIngredient_id());
			  temp.setAmount(ingredient2recipe.get(i).getAmount());
			  
			  //Durchlaufe alle Ingredients
			  for(int j = 0; j < ingredients.size(); j ++) {
				  //... und suche den Ingredientnamen des Ingredients heraus, das zur Ingredient2Recipe Connection passt
				  if(ingredients.get(j).getId().equals(ingredient2recipe.get(i).getIngredient_id())) {
					  //das vierte Attribut setzen
					  temp.setIngredient_name(ingredients.get(j).getName());
				  }
			  }
			  //... und zur Ergebnisliste hinzufuegen
			  result.add(temp); 
			  System.out.println("dao_ing2recnp: " + temp.getIngredient_name());
		  }
		  System.out.println("dao succeeds");
		  return result;
	   }

	   /**
	    * Takes an ArrayList of Type Ingredient2RecipeNonPersistent  takes the Ingredient2Recipe Attributes and saves them in the Ingredient2Recipe Table
	    * @param ingredient2RecipeNonPersistent
	    * @return ... an ArrayList of all Ingredients2Recipe with the respective Name
	    */
	   public ArrayList<Ingredient2RecipeNonPersistent> insertIngredient2RecipeNonPersistent(ArrayList<Ingredient2RecipeNonPersistent> ingredient2RecipeNonPersistent) {
		   
		   //Run through the input Arraylist
		   for(int i = 0; i < ingredient2RecipeNonPersistent.size(); i++) {
			   //Create a new Ingredient2Recipe Object and fill its attributes  with the values from the given Arraylist
			   Ingredient2Recipe temp = new Ingredient2Recipe();
			   temp.setIngredient_id(ingredient2RecipeNonPersistent.get(i).getIngredient_id());
			   temp.setRecipe_id(ingredient2RecipeNonPersistent.get(i).getRecipe_id());
			   temp.setAmount(ingredient2RecipeNonPersistent.get(i).getAmount());
			   
			   //save the Ingredient2Recipe Object in the Database
			   i2rDAO = new Ingredient2RecipeDAO(user, password);
			   i2rDAO.insertIngredient2Recipe(temp);
		   }
		   
		   //... and return a list of all Ingredient2Recipe Connections with the respective Ingredient Names
		   return selectAllIngredient2RecipeNonPersistent();
	   }
	   
	   /**
	    * Used to insert a new Ingredient into the Database
	    * @param c 
	    */
	public Ingredient insertIngredient(Ingredient c) {
		
		//Establish a connection to the DB
		openConnection();
		
		//Build the query
		query = "INSERT INTO INGREDIENT (ID, NAME, GDA_ENERGY , GDA_PROTEIN , GDA_CARBO , GDA_FAT , GDA_FIBER , GDA_SODIUM, PRICE ) VALUES (ID_INGREDIENT_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			//interpret the query...
			pstmt = connection.prepareStatement(query);
			
			//...and assign the Values of the given Object to the query
			pstmt.setString(1, c.getName());
			pstmt.setDouble(2, c.getGda_energy());
			pstmt.setDouble(3, c.getGda_protein());
			pstmt.setDouble(4, c.getGda_carbo());
			pstmt.setDouble(5, c.getGda_fat());
			pstmt.setDouble(6, c.getGda_fiber());
			pstmt.setDouble(7, c.getGda_sodium());
			pstmt.setDouble(8, c.getPrice());
			
			//executes the Insert query on the DB
			pstmt.executeUpdate();
		} catch (Exception e) {
		}
		
		//closes the connection to the DB
		closeConnection();
		
		ArrayList<Ingredient> result = new ArrayList<Ingredient>();
		result = selectIngredient(c);
		Ingredient b = new Ingredient();
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
	    * Generic Method, used to delete a Ingredient from the Database
	    * @param c 
	    */
	
	public ArrayList<Ingredient> deleteIngredient(Ingredient c) {
		
		ArrayList<Ingredient> result = new ArrayList<Ingredient>();
		result = selectIngredient(c);
		
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
		if(c.getId() != null) {query = query + " AND ID = ?"; index++;}
		if(c.getName() != null) {query = query + " AND NAME = ?"; index++;}
		if(c.getGda_energy() != null) {query = query + " AND GDA_ENERGY = ?"; index++;}
		if(c.getGda_protein() != null) {query = query + " AND GDA_PROTEIN = ?"; index++;}
		if(c.getGda_carbo() != null) {query = query + " AND GDA_CARBO = ?"; index++;}
		if(c.getGda_fat() != null) {query = query + " AND GDA_FAT = ?"; index++;}
		if(c.getGda_fiber() != null) {query = query + " AND GDA_FIBER = ?"; index++;}
		if(c.getGda_sodium() != null) {query = query + " AND GDA_SODIUM = ?"; index++;}
		if(c.getPrice() != null) {query = query + " AND PRICE = ?"; index++;}
		
		
		//in case every given attribute is null, the query shall not be executed
		if(index == 0) {status = false;} 	
		query = query+";";
			
		try {
			//prepares the query
			pstmt = connection.prepareStatement(query);
			
			//sets as much attributes to the specified index of the prepared
			//statement as were given in as delete criteria
			if(c.getPrice() != null) {pstmt.setDouble(index--, c.getPrice());}
			if(c.getGda_sodium() != null) {pstmt.setDouble(index--, c.getGda_sodium());}
			if(c.getGda_fiber() != null) {pstmt.setDouble(index--, c.getGda_fiber());}
			if(c.getGda_fat() != null) {pstmt.setDouble(index--, c.getGda_fat());}
			if(c.getGda_carbo() != null) {pstmt.setDouble(index--, c.getGda_carbo());}
			if(c.getGda_protein() != null) {pstmt.setDouble(index--, c.getGda_protein());}
			if(c.getGda_energy() != null) {pstmt.setDouble(index--, c.getGda_energy());}
			if(c.getName() != null) {pstmt.setString(index--, c.getName());}
			if(c.getId() != null) {pstmt.setInt(index--, c.getId());}
			
			//if at least one attribute is not null, the query shall be executed
			if(status != false) {
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			return null;
		}
		
		//closes the connection to the DB
		closeConnection();
		return result;
	}
	
	public ArrayList<Ingredient> selectAllIngredients() {
		
		//Result ArrayList, which is returned to the invoking routine
		ArrayList<Ingredient> result = new ArrayList<Ingredient>();
		
		//Establish a connection to the DB
		openConnection();
		
		//Builds the query
		query = "SELECT * FROM INGREDIENT;";
		
		try {
			
			//prepares the query
			pstmt = connection.prepareStatement(query);
			
			//executes the query on the DB and receives the result set
			rs = pstmt.executeQuery();

			//While the result set contains results, they shall be
			//converted to Ingredient Objects and asserted to a result ArrayList
			while(rs.next()) {
				Ingredient res = new Ingredient();
				res.setId(rs.getInt(1));
				res.setName(rs.getString(2));
				res.setGda_energy(rs.getDouble(3));
				res.setGda_protein(rs.getDouble(4));
				res.setGda_carbo(rs.getDouble(5));
				res.setGda_fat(rs.getDouble(6));
				res.setGda_fiber(rs.getDouble(7));
				res.setGda_sodium(rs.getDouble(8));
				res.setPrice(rs.getDouble(9));
				result.add(res);	
			}
		} catch (SQLException e) {
		}
		
		//closes the connection to the DB...
		closeConnection();
		//...and returns the ArrayList
		return result;
		
	}
	
	public ArrayList<Ingredient> selectIngredient(Ingredient c) {
		
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
		if(c.getId() != null) {query = query + " AND ID = ?"; index++;}
		if(c.getName() != null) {query = query + " AND NAME = ?"; index++;}
		if(c.getGda_energy() != null) {query = query + " AND GDA_ENERGY = ?"; index++;}
		if(c.getGda_protein() != null) {query = query + " AND GDA_PROTEIN = ?"; index++;}
		if(c.getGda_carbo() != null) {query = query + " AND GDA_CARBO = ?"; index++;}
		if(c.getGda_fat() != null) {query = query + " AND GDA_FAT = ?"; index++;}
		if(c.getGda_fiber() != null) {query = query + " AND GDA_FIBER = ?"; index++;}
		if(c.getGda_sodium() != null) {query = query + " AND GDA_SODIUM = ?"; index++;}
		if(c.getPrice() != null) {query = query + " AND PRICE = ?"; index++;}
		query = query+";";
		
		try {
			
			//prepares the query
			pstmt = connection.prepareStatement(query);
			
			//sets as much attributes to the specified index of the prepared
			//statement as were given in as delete criteria
			
			if(c.getPrice() != null) {pstmt.setDouble(index--, c.getPrice());}
			if(c.getGda_sodium() != null) {pstmt.setDouble(index--, c.getGda_sodium());}
			if(c.getGda_fiber() != null) {pstmt.setDouble(index--, c.getGda_fiber());}
			if(c.getGda_fat() != null) {pstmt.setDouble(index--, c.getGda_fat());}
			if(c.getGda_carbo() != null) {pstmt.setDouble(index--, c.getGda_carbo());}
			if(c.getGda_protein() != null) {pstmt.setDouble(index--, c.getGda_protein());}
			if(c.getGda_energy() != null) {pstmt.setDouble(index--, c.getGda_energy());}
			if(c.getName() != null) {pstmt.setString(index--, c.getName());}
			if(c.getId() != null) {pstmt.setInt(index--, c.getId());}
			
			//executes the query on the DB and receives the result set
			rs = pstmt.executeQuery();

			//While the result set contains results, they shall be
			//converted to Ingredient Objects and asserted to a result ArrayList
			while(rs.next()) {
				Ingredient res = new Ingredient();
				res.setId(rs.getInt(1));
				res.setName(rs.getString(2));
				res.setGda_energy(rs.getDouble(3));
				res.setGda_protein(rs.getDouble(4));
				res.setGda_carbo(rs.getDouble(5));
				res.setGda_fat(rs.getDouble(6));
				res.setGda_fiber(rs.getDouble(7));
				res.setGda_sodium(rs.getDouble(8));
				res.setPrice(rs.getDouble(9));
				result.add(res);	
			}
		} catch (SQLException e) {
		}
		
		//closes the connection to the DB...
		closeConnection();
		//...and returns the ArrayList
		return result;
	}
	
	
	public ArrayList<Ingredient> UpdateIngredient(Ingredient newI, Ingredient oldI) {
		
		//Establish a connection to the DB
		openConnection();
		
		//index variable which counts the number of arguments which are not NULL
		int index = 0;
		
		//Builds the query
		query = "UPDATE INGREDIENT SET ";
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented --> NEW ATTRIBUTES
		if(newI.getId() != null) {query = query + " ID = ?, "; index++;}
		if(newI.getName() != null) {query = query + " NAME = ?, "; index++;}
		if(newI.getGda_energy() != null) {query = query + " GDA_ENERGY = ?, "; index++;}
		if(newI.getGda_protein() != null) {query = query + " GDA_PROTEIN = ?, "; index++;}
		if(newI.getGda_carbo() != null) {query = query + " GDA_CARBO = ?, "; index++;}
		if(newI.getGda_fat() != null) {query = query + " GDA_FAT = ?, "; index++;}
		if(newI.getGda_fiber() != null) {query = query + " GDA_FIBER = ?, "; index++;}
		if(newI.getGda_sodium() != null) {query = query + " GDA_SODIUM = ?, "; index++;}
		if(newI.getPrice() != null) {query = query + " PRICE = ? "; index++;}
		
		//The last "," from the subsequent query shall be removed in order to prevent a syntax error
		//furthermore the WHERE Clause with a dummy argument shall be asserted
		query = query.substring(0, query.length()-1) + " WHERE 1=1 ";
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented --> SEARCH CRITERIA
		if(oldI.getId() != null) {query = query + " AND ID = ?"; index++;}
		if(oldI.getName() != null) {query = query + " AND NAME = ?"; index++;}
		if(oldI.getGda_energy() != null) {query = query + " AND GDA_ENERGY = ?"; index++;}
		if(oldI.getGda_protein() != null) {query = query + " AND GDA_PROTEIN = ?"; index++;}
		if(oldI.getGda_carbo() != null) {query = query + " AND GDA_CARBO = ?"; index++;}
		if(oldI.getGda_fat() != null) {query = query + " AND GDA_FAT = ?"; index++;}
		if(oldI.getGda_fiber() != null) {query = query + " AND GDA_FIBER = ?"; index++;}
		if(oldI.getGda_sodium() != null) {query = query + " AND GDA_SODIUM = ?"; index++;}
		if(oldI.getPrice() != null) {query = query + " AND PRICE = ?"; index++;}
		
		query = query+";";
		
		try {	
			//prepares the query
			pstmt = connection.prepareStatement(query);
			
			//sets as much attributes to the specified index of the prepared
			//statement as were given as argument
			if(oldI.getPrice() != null) {pstmt.setDouble(index--, oldI.getPrice());}
			if(oldI.getGda_sodium() != null) {pstmt.setDouble(index--, oldI.getGda_sodium());}
			if(oldI.getGda_fiber() != null) {pstmt.setDouble(index--, oldI.getGda_fiber());}
			if(oldI.getGda_fat() != null) {pstmt.setDouble(index--, oldI.getGda_fat());}
			if(oldI.getGda_carbo() != null) {pstmt.setDouble(index--, oldI.getGda_carbo());}
			if(oldI.getGda_protein() != null) {pstmt.setDouble(index--, oldI.getGda_protein());}
			if(oldI.getGda_energy() != null) {pstmt.setDouble(index--, oldI.getGda_energy());}
			if(oldI.getName() != null) {pstmt.setString(index--, oldI.getName());}
			if(oldI.getId() != null) {pstmt.setInt(index--, oldI.getId());}
			
			if(newI.getPrice() != null) {pstmt.setDouble(index--, newI.getPrice());}
			if(newI.getGda_sodium() != null) {pstmt.setDouble(index--, newI.getGda_sodium());}
			if(newI.getGda_fiber() != null) {pstmt.setDouble(index--, newI.getGda_fiber());}
			if(newI.getGda_fat() != null) {pstmt.setDouble(index--, newI.getGda_fat());}
			if(newI.getGda_carbo() != null) {pstmt.setDouble(index--, newI.getGda_carbo());}
			if(newI.getGda_protein() != null) {pstmt.setDouble(index--, newI.getGda_protein());}
			if(newI.getGda_energy() != null) {pstmt.setDouble(index--, newI.getGda_energy());}
			if(newI.getName() != null) {pstmt.setString(index--, newI.getName());}
			if(newI.getId() != null) {pstmt.setInt(index--, newI.getId());}
			
			//Execute the Update on the DB
			pstmt.executeUpdate();
		} catch (SQLException e) {
			return null;
		}
		//Close the connection to the DB
		closeConnection();
		return selectIngredient(newI);
	}
}