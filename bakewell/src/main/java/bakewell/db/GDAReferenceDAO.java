package bakewell.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bakewell.beans.GDAReference;

/**
 * This Class contains generic CRUD Methods to access the UserDB.
 * @author Alex
 *
 */
public class GDAReferenceDAO {
	
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
	public GDAReferenceDAO(String user, String password) {
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
	public GDAReferenceDAO(String url, String user, String password) {
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
	    * Used to insert a new GDAReference into the Database
	    * @param c 
	    */
	public GDAReference insertGDAReference(GDAReference c) {
		
		//Establish a connection to the DB
		openConnection();
		
		//Build the query
		query = "INSERT INTO GDAREFERENCE (ID, NAME, AMOUNT ) VALUES (ID_GDAREFERENCE_SEQ.nextval, ?, ?)";
		
		try {
			//interpret the query...
			pstmt = connection.prepareStatement(query);
			
			//...and assign the Values of the given Object to the query
			pstmt.setString(1, c.getName());
			pstmt.setDouble(2, c.getAmount());
			
			//executes the Insert query on the DB
			pstmt.executeUpdate();
		} catch (Exception e) {
			return null;
		}
		
		//closes the connection to the DB
		closeConnection();
		
		ArrayList<GDAReference> result = new ArrayList<GDAReference>();
		result = selectGDAReference(c);
		GDAReference b = new GDAReference();
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
	    * Generic Method, used to delete a GDAReference from the Database
	    * @param c 
	    */
	
	public ArrayList<GDAReference> deleteGDAReference(GDAReference c) {
		
		ArrayList<GDAReference> result = new ArrayList<GDAReference>();
		result = selectGDAReference(c);
		
		//Establish a connection to the DB
		openConnection();
		 
		//index variable which counts the number of arguments which are not NULL
		int index = 0;
		
		//determines whether the Query shall be sent to the DB
		//in order to prevent it from executing "DELETE FROM \"GDAReference\" WHERE 1=1" on the DB
		boolean status = true;
		
		//Builds the query
		query = "DELETE FROM GDAREFERENCE WHERE 1=1";
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented
		if(c.getId() != null) {query = query + " AND ID = ?"; index++;}
		if(c.getName() != null) {query = query + " AND NAME = ?"; index++;}
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
	
	
	public ArrayList<GDAReference> selectGDAReference(GDAReference c) {
		
		//Result ArrayList, which is returned to the invoking routine
		ArrayList<GDAReference> result = new ArrayList<GDAReference>();
		
		//Establish a connection to the DB
		openConnection();
		
		//Builds the query
		query = "SELECT * FROM GDAREFERENCE WHERE 1=1";
		
		//index variable which counts the number of arguments which are not NULL
		int index = 0;
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented
		if(c.getId() != null) {query = query + " AND ID = ?"; index++;}
		if(c.getName() != null) {query = query + " AND NAME = ?"; index++;}
		if(c.getAmount() != null) {query = query + " AND AMOUNT = ?"; index++;}
		query = query+";";
		
		try {
			
			//prepares the query
			pstmt = connection.prepareStatement(query);
			
			//sets as much attributes to the specified index of the prepared
			//statement as were given in as delete criteria
			
			if(c.getAmount() != null) {pstmt.setDouble(index--, c.getAmount());}
			if(c.getName() != null) {pstmt.setString(index--, c.getName());}
			if(c.getId() != null) {pstmt.setInt(index--, c.getId());}
			
			//executes the query on the DB and receives the result set
			rs = pstmt.executeQuery();

			//While the result set contains results, they shall be
			//converted to GDAReference Objects and asserted to a result ArrayList
			while(rs.next()) {
				GDAReference res = new GDAReference();
				res.setId(rs.getInt(1));
				res.setName(rs.getString(2));
				res.setAmount(rs.getDouble(3));
				result.add(res);	
			}
		} catch (SQLException e) {
		}
		
		//closes the connection to the DB...
		closeConnection();
		//...and returns the ArrayList
		return result;
	}
	
	
	public ArrayList<GDAReference> UpdateGDAReference(GDAReference newC, GDAReference oldC) {
		
		//Establish a connection to the DB
		openConnection();
		
		//index variable which counts the number of arguments which are not NULL
		int index = 0;
		
		//Builds the query
		query = "UPDATE GDAREFERENCE SET ";
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented --> NEW ATTRIBUTES
		if(newC.getId() != null) {query = query + " ID = ?, "; index++;}
		if(newC.getName() != null) {query = query + " NAME = ?, "; index++;}
		if(newC.getAmount() != null) {query = query + " AMOUNT = ? "; index++;}
		
		//The last "," from the subsequent query shall be removed in order to prevent a syntax error
		//furthermore the WHERE Clause with a dummy argument shall be asserted
		query = query.substring(0, query.length()-1) + " WHERE 1=1 ";
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented --> SEARCH CRITERIA
		if(oldC.getId() != null) {query = query + " ID = ?"; index++;}
		if(oldC.getName() != null) {query = query + " AND NAME = ?"; index++;}
		if(oldC.getAmount() != null) {query = query + " AND AMOUNT = ?"; index++;}
		
		query = query+";";
		
		try {	
			//prepares the query
			pstmt = connection.prepareStatement(query);
			
			//sets as much attributes to the specified index of the prepared
			//statement as were given as argument
			if(oldC.getAmount() != null) {pstmt.setDouble(index--, oldC.getAmount());}
			if(oldC.getName() != null) {pstmt.setString(index--, oldC.getName());}
			if(oldC.getId() != null) {pstmt.setInt(index--, oldC.getId());}
			
			if(newC.getAmount() != null) {pstmt.setDouble(index--, newC.getAmount());}
			if(newC.getName() != null) {pstmt.setString(index--, newC.getName());}
			if(newC.getId() != null) {pstmt.setInt(index--, newC.getId());}
			
			//Execute the Update on the DB
			pstmt.executeUpdate();
		} catch (SQLException e) {
			return null;
		}
		//Close the connection to the DB
		closeConnection();
		return selectGDAReference(newC);
	}
}