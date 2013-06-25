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
import bakewell.beans.Ingredient2Recipe;
import bakewell.beans.Product;
import bakewell.beans.Recipe;

/**
 * This Class contains generic CRUD Methods to access the UserDB.
 * @author Alex
 *
 */
public class ProductDAO {
	
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
	
	RecipeDAO rDAO = null;
	IngredientDAO iDAO = null;

	/**
	 * Constructor, which takes the url, username and the password in order
	 * to create a connection to the DB
	 * @param user
	 * @param password
	 */
	public ProductDAO(String user, String password) {
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
	public ProductDAO(String url, String user, String password) {
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
	    * @param productid ... Searches the Recipe for a product
	    * @return ... returns the Recipe, which is connected to the product
	    */
		public Recipe selectRecipeByProductId(int productid) {
			//initializing of all DAOs which are needed 
			rDAO = new RecipeDAO(user, password);
			//Creating a new searchproduct
			Product p = new Product();
			p.setId(productid);
			//Searching for the complete Product accodring to the ID in order to get all Product attributes
			ArrayList<Product> res = selectProduct(p);
			if(res.isEmpty()) {
				p = new Product();
			} else {
				p = res.get(0);
			}
			
			Recipe r = new Recipe();
			//Fetch the Recipe ID from the Product Attributes and inserting it into a new search recipe
			r.setId(p.getRecipe_id());
			//search the recipe in the DB in order to get all attributes, including the ArrayList with all Ingredient2Recipe Connections
			ArrayList<Recipe> result = rDAO.selectRecipe(r);
			if(result.isEmpty()) {
				return null;
			} else {
				return result.get(0);
			}
		}
	   
	   /**
	    * 
	    * Searches the Ingredients of a product, together with the respective amounts
	    * 
	    * @param productid ... the product id, which is a new identifier
	    * @return ... a Map <Ingredient, Double> with the Ingredient of a product with the quantity of the ingredient in the recipe/product
	    */
	   public Map<Ingredient, Double> selectIngredientsOfProduct(int productid) {
	
			//initializing of all DAOs which are needed 
			rDAO = new RecipeDAO(user, password);
			iDAO = new IngredientDAO(user, password);
			Map<Ingredient, Double> ingredientMap = new HashMap<Ingredient, Double>();
			
			//Creating a new searchproduct
			Product p = new Product();
			p.setId(productid);
			//Searching for the complete Product accodring to the ID in order to get all Product attributes
			p = selectProduct(p).get(0);
			Recipe r = new Recipe();
			//Fetch the Recipe ID from the Product Attributes and inserting it into a new search recipe
			r.setId(p.getRecipe_id());
			//search the recipe in the DB in order to get all attributes, including the ArrayList with all Ingredient2Recipe Connections
			r = rDAO.selectRecipe(r).get(0);
			//Get the Ingredient2Recipe Connections
			ArrayList<Ingredient2Recipe> ingredient2recipe = r.getIngredients();
			//... and run through them
			for(int i = 0; i < ingredient2recipe.size(); i++) {
				//Create a new search Ingredient and fetching the id from each ingredient2recipe
				Ingredient ing = new Ingredient();
				ing.setId(ingredient2recipe.get(i).getIngredient_id());
				//search the full ingredient with all attributes
				ing = iDAO.selectIngredient(ing).get(0);
				//and add it to the result Hashmap together with the Amounts which are stored in the Ingredient2Recipe Connections
				ingredientMap.put(ing, ingredient2recipe.get(i).getAmount());
			}
			
		return ingredientMap;
	}
	
	   /**
	    * Used to insert a new Product into the Database
	    * @param c 
	    */
	public Product insertProduct(Product c) {
		
		//Establish a connection to the DB
		openConnection();
		
		//Build the query
		query = "INSERT INTO PRODUCT (ID, RECIPE_ID, CUSTOMER_ID , PRODUCTNAME , DELIVERYDATE , PRODUCTION_START , PRODUCTION_END , PRODUCTION_FACILITY, PRODUCTION_CONTRACTOR, TRANSPORT_CONTRACTOR, TRANSPORT_COST, COMMENT ) VALUES (ID_PRODUCT_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			//interpret the query...
			pstmt = connection.prepareStatement(query);
			
			//...and assign the Values of the given Object to the query
			pstmt.setInt(1, c.getRecipe_id());
			pstmt.setInt(2, c.getCustomer_id());
			pstmt.setString(3, c.getProduct_Name());
			pstmt.setDate(4, c.getDeliveryDate());
			pstmt.setDate(5, c.getProduction_Start());
			pstmt.setDate(6, c.getProduction_End());
			pstmt.setString(7, c.getProduction_Facility());
			pstmt.setString(8, c.getProduction_Contractor());
			pstmt.setString(9, c.getTransport_Contractor());
			pstmt.setDouble(10, c.getTransport_cost());
			pstmt.setString(11, c.getComment());
			
			//executes the Insert query on the DB
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//closes the connection to the DB
		closeConnection();
		
		ArrayList<Product> result = new ArrayList<Product>();
		result = selectProduct(c);
		Product b = new Product();
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
	    * Generic Method, used to delete a Product from the Database
	    * @param c 
	    */
	
	public ArrayList<Product> deleteProduct(Product c) {
		
		ArrayList<Product> result = new ArrayList<Product>();
		result = selectProduct(c);
		
		//Establish a connection to the DB
		openConnection();
		 
		//index variable which counts the number of arguments which are not NULL
		int index = 0;
		
		//determines whether the Query shall be sent to the DB
		//in order to prevent it from executing "DELETE FROM \"Product\" WHERE 1=1" on the DB
		boolean status = true;
		
		//Builds the query
		query = "DELETE FROM PRODUCT WHERE 1=1";
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented		
		if(c.getId() != null) {query = query + " AND ID = ?"; index++;}
		if(c.getRecipe_id() != null) {query = query + " AND RECIPE_ID = ?"; index++;}
		if(c.getCustomer_id() != null) {query = query + " AND CUSTOMER_ID = ?"; index++;}
		if(c.getProduct_Name() != null) {query = query + " AND PRODUCTNAME = ?"; index++;}
		if(c.getDeliveryDate() != null) {query = query + " AND DELIVERYDATE = ?"; index++;}
		if(c.getProduction_Start() != null) {query = query + " AND PRODUCTION_START = ?"; index++;}
		if(c.getProduction_End() != null) {query = query + " AND PRODUCTION_END = ?"; index++;}
		if(c.getProduction_Facility() != null) {query = query + " AND PRODUCTION_FACILITY = ?"; index++;}
		if(c.getProduction_Contractor() != null) {query = query + " AND PRODUCTION_CONTRACTOR = ?"; index++;}
		if(c.getTransport_Contractor() != null) {query = query + " AND TRANSPORT_CONTRACTOR = ?"; index++;}
		if(c.getTransport_cost() != null) {query = query + " AND TRANSPORT_COST = ?"; index++;}
		if(c.getComment() != null) {query = query + " AND COMMENT = ?"; index++;}
		
		
		//in case every given attribute is null, the query shall not be executed
		if(index == 0) {status = false;} 	
		query = query+";";
			
		try {
			//prepares the query
			pstmt = connection.prepareStatement(query);
			
			//sets as much attributes to the specified index of the prepared
			//statement as were given in as delete criteria
			if(c.getComment() != null) {pstmt.setString(index--, c.getComment());}
			if(c.getTransport_cost() != null) {pstmt.setDouble(index--, c.getTransport_cost());}
			if(c.getTransport_Contractor() != null) {pstmt.setString(index--, c.getTransport_Contractor());}
			if(c.getProduction_Contractor() != null) {pstmt.setString(index--, c.getProduction_Contractor());}
			if(c.getProduction_Facility() != null) {pstmt.setString(index--, c.getProduction_Facility());}
			if(c.getProduction_End() != null) {pstmt.setDate(index--, c.getProduction_End());}
			if(c.getProduction_Start() != null) {pstmt.setDate(index--, c.getProduction_Start());}
			if(c.getDeliveryDate() != null) {pstmt.setDate(index--, c.getDeliveryDate());}
			if(c.getProduct_Name() != null) {pstmt.setString(index--, c.getProduct_Name());}
			if(c.getCustomer_id() != null) {pstmt.setInt(index--, c.getCustomer_id());}
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
	
	
	public ArrayList<Product> selectProduct(Product c) {
		
		//Result ArrayList, which is returned to the invoking routine
		ArrayList<Product> result = new ArrayList<Product>();
		
		//Establish a connection to the DB
		openConnection();
		
		//Builds the query
		query = "SELECT * FROM PRODUCT WHERE 1=1";
		
		//index variable which counts the number of arguments which are not NULL
		int index = 0;
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented
		if(c.getId() != null) {query = query + " AND ID = ?"; index++;}
		if(c.getRecipe_id() != null) {query = query + " AND RECIPE_ID = ?"; index++;}
		if(c.getCustomer_id() != null) {query = query + " AND CUSTOMER_ID = ?"; index++;}
		if(c.getProduct_Name() != null) {query = query + " AND PRODUCTNAME = ?"; index++;}
		if(c.getDeliveryDate() != null) {query = query + " AND DELIVERYDATE = ?"; index++;}
		if(c.getProduction_Start() != null) {query = query + " AND PRODUCTION_START = ?"; index++;}
		if(c.getProduction_End() != null) {query = query + " AND PRODUCTION_END = ?"; index++;}
		if(c.getProduction_Facility() != null) {query = query + " AND PRODUCTION_FACILITY = ?"; index++;}
		if(c.getProduction_Contractor() != null) {query = query + " AND PRODUCTION_CONTRACTOR = ?"; index++;}
		if(c.getTransport_Contractor() != null) {query = query + " AND TRANSPORT_CONTRACTOR = ?"; index++;}
		if(c.getTransport_cost() != null) {query = query + " AND TRANSPORT_COST = ?"; index++;}
		if(c.getComment() != null) {query = query + " AND COMMENT = ?"; index++;}
		query = query+";";
		
		try {
			
			//prepares the query
			pstmt = connection.prepareStatement(query);
			
			//sets as much attributes to the specified index of the prepared
			//statement as were given in as delete criteria
			
			if(c.getComment() != null) {pstmt.setString(index--, c.getComment());}
			if(c.getTransport_cost() != null) {pstmt.setDouble(index--, c.getTransport_cost());}
			if(c.getTransport_Contractor() != null) {pstmt.setString(index--, c.getTransport_Contractor());}
			if(c.getProduction_Contractor() != null) {pstmt.setString(index--, c.getProduction_Contractor());}
			if(c.getProduction_Facility() != null) {pstmt.setString(index--, c.getProduction_Facility());}
			if(c.getProduction_End() != null) {pstmt.setDate(index--, c.getProduction_End());}
			if(c.getProduction_Start() != null) {pstmt.setDate(index--, c.getProduction_Start());}
			if(c.getDeliveryDate() != null) {pstmt.setDate(index--, c.getDeliveryDate());}
			if(c.getProduct_Name() != null) {pstmt.setString(index--, c.getProduct_Name());}
			if(c.getCustomer_id() != null) {pstmt.setInt(index--, c.getCustomer_id());}
			if(c.getRecipe_id() != null) {pstmt.setInt(index--, c.getRecipe_id());}
			if(c.getId() != null) {pstmt.setInt(index--, c.getId());}
			
			//executes the query on the DB and receives the result set
			rs = pstmt.executeQuery();

			//While the result set contains results, they shall be
			//converted to Product Objects and asserted to a result ArrayList
			while(rs.next()) {
				Product res = new Product();
				res.setId(rs.getInt(1));
				res.setRecipe_id(rs.getInt(2));
				res.setCustomer_id(rs.getInt(3));
				res.setProduct_Name(rs.getString(4));
				res.setDeliveryDate(rs.getDate(5));
				res.setProduction_Start(rs.getDate(6));
				res.setProduction_End(rs.getDate(7));
				res.setProduction_Facility(rs.getString(8));
				res.setProduction_Contractor(rs.getString(9));
				res.setTransport_Contractor(rs.getString(10));
				res.setTransport_cost(rs.getDouble(11));
				res.setComment(rs.getString(12));
				result.add(res);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//closes the connection to the DB...
		closeConnection();
		//...and returns the ArrayList
		return result;
	}
	
	
	public ArrayList<Product> UpdateProduct(Product newC, Product oldC) {
		
		//Establish a connection to the DB
		openConnection();
		
		//index variable which counts the number of arguments which are not NULL
		int index = 0;
		
		//Builds the query
		query = "UPDATE PRODUCT SET ";
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented --> NEW ATTRIBUTES
		if(oldC.getId() != null) {query = query + " ID = ?, "; index++;}
		if(oldC.getRecipe_id() != null) {query = query + " RECIPE_ID = ?, "; index++;}
		if(oldC.getCustomer_id() != null) {query = query + " CUSTOMER_ID = ?, "; index++;}
		if(oldC.getProduct_Name() != null) {query = query + " PRODUCTNAME = ?, "; index++;}
		if(oldC.getDeliveryDate() != null) {query = query + " DELIVERYDATE = ?, "; index++;}
		if(oldC.getProduction_Start() != null) {query = query + "PRODUCTION_START = ?, "; index++;}
		if(oldC.getProduction_End() != null) {query = query + " PRODUCTION_END = ?, "; index++;}
		if(oldC.getProduction_Facility() != null) {query = query + " PRODUCTION_FACILITY = ?, "; index++;}
		if(oldC.getProduction_Contractor() != null) {query = query + " PRODUCTION_CONTRACTOR = ?, "; index++;}
		if(oldC.getTransport_Contractor() != null) {query = query + " TRANSPORT_CONTRACTOR = ?, "; index++;}
		if(oldC.getTransport_cost() != null) {query = query + " TRANSPORT_COST = ?, "; index++;}
		if(oldC.getComment() != null) {query = query + " COMMENT = ? "; index++;}
		
		//The last "," from the subsequent query shall be removed in order to prevent a syntax error
		//furthermore the WHERE Clause with a dummy argument shall be asserted
		query = query.substring(0, query.length()-1) + " WHERE 1=1 ";
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented --> SEARCH CRITERIA
		if(newC.getId() != null) {query = query + " AND ID = ?"; index++;}
		if(newC.getRecipe_id() != null) {query = query + " AND RECIPE_ID = ?"; index++;}
		if(newC.getCustomer_id() != null) {query = query + " AND CUSTOMER_ID = ?"; index++;}
		if(newC.getProduct_Name() != null) {query = query + " AND PRODUCTNAME = ?"; index++;}
		if(newC.getDeliveryDate() != null) {query = query + " AND DELIVERYDATE = ?"; index++;}
		if(newC.getProduction_Start() != null) {query = query + " AND PRODUCTION_START = ?"; index++;}
		if(newC.getProduction_End() != null) {query = query + " AND PRODUCTION_END = ?"; index++;}
		if(newC.getProduction_Facility() != null) {query = query + " AND PRODUCTION_FACILITY = ?"; index++;}
		if(newC.getProduction_Contractor() != null) {query = query + " AND PRODUCTION_CONTRACTOR = ?"; index++;}
		if(newC.getTransport_Contractor() != null) {query = query + " AND TRANSPORT_CONTRACTOR = ?"; index++;}
		if(newC.getTransport_cost() != null) {query = query + " AND TRANSPORT_COST = ?"; index++;}
		if(newC.getComment() != null) {query = query + " AND COMMENT = ?"; index++;}
		
		query = query+";";
		
		try {	
			//prepares the query
			pstmt = connection.prepareStatement(query);
			
			//sets as much attributes to the specified index of the prepared
			//statement as were given as argument
			if(oldC.getComment() != null) {pstmt.setString(index--, oldC.getComment());}
			if(oldC.getTransport_cost() != null) {pstmt.setDouble(index--, oldC.getTransport_cost());}
			if(oldC.getTransport_Contractor() != null) {pstmt.setString(index--, oldC.getTransport_Contractor());}
			if(oldC.getProduction_Contractor() != null) {pstmt.setString(index--, oldC.getProduction_Contractor());}
			if(oldC.getProduction_Facility() != null) {pstmt.setString(index--, oldC.getProduction_Facility());}
			if(oldC.getProduction_End() != null) {pstmt.setDate(index--, oldC.getProduction_End());}
			if(oldC.getProduction_Start() != null) {pstmt.setDate(index--, oldC.getProduction_Start());}
			if(oldC.getDeliveryDate() != null) {pstmt.setDate(index--, oldC.getDeliveryDate());}
			if(oldC.getProduct_Name() != null) {pstmt.setString(index--, oldC.getProduct_Name());}
			if(oldC.getCustomer_id() != null) {pstmt.setInt(index--, oldC.getCustomer_id());}
			if(oldC.getRecipe_id() != null) {pstmt.setInt(index--, oldC.getRecipe_id());}
			if(oldC.getId() != null) {pstmt.setInt(index--, oldC.getId());}
			
			if(newC.getComment() != null) {pstmt.setString(index--, newC.getComment());}
			if(newC.getTransport_cost() != null) {pstmt.setDouble(index--, newC.getTransport_cost());}
			if(newC.getTransport_Contractor() != null) {pstmt.setString(index--, newC.getTransport_Contractor());}
			if(newC.getProduction_Contractor() != null) {pstmt.setString(index--, newC.getProduction_Contractor());}
			if(newC.getProduction_Facility() != null) {pstmt.setString(index--, newC.getProduction_Facility());}
			if(newC.getProduction_End() != null) {pstmt.setDate(index--, newC.getProduction_End());}
			if(newC.getProduction_Start() != null) {pstmt.setDate(index--, newC.getProduction_Start());}
			if(newC.getDeliveryDate() != null) {pstmt.setDate(index--, newC.getDeliveryDate());}
			if(newC.getProduct_Name() != null) {pstmt.setString(index--, newC.getProduct_Name());}
			if(newC.getCustomer_id() != null) {pstmt.setInt(index--, newC.getCustomer_id());}
			if(newC.getRecipe_id() != null) {pstmt.setInt(index--, newC.getRecipe_id());}
			if(newC.getId() != null) {pstmt.setInt(index--, newC.getId());}
			
			//Execute the Update on the DB
			pstmt.executeUpdate();
		} catch (SQLException e) {
			return null;
		}
		//Close the connection to the DB
		closeConnection();
		return selectProduct(newC);
	}
}