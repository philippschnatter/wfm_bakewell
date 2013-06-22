package bakewell.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bakewell.beans.Customer;

/**
 * This Class contains generic CRUD Methods to access the UserDB.
 * @author Alex
 *
 */
public class CustomerDAO {
	
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
	public CustomerDAO(String user, String password) {
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
	public CustomerDAO(String url, String user, String password) {
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
	    * Used to insert a new Customer into the Database
	    * @param c 
	    */
	   
	public Customer insertCustomer(Customer c) {
		
		//Establish a connection to the DB
		openConnection();
		
		//Build the query
		query = "INSERT INTO CUSTOMER (ID, FIRSTNAME, LASTNAME , ADDRESS , TELNO , MAILADDRESS , COMPANY , PASSWORD ) VALUES (ID_CUSTOMER_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			//interpret the query...
			pstmt = connection.prepareStatement(query);
			
			//...and assign the Values of the given Object to the query
			pstmt.setString(1, c.getFirstName());
			pstmt.setString(2, c.getLastName());
			pstmt.setString(3, c.getAddress());
			pstmt.setString(4, c.getTelNo());
			pstmt.setString(5, c.getMailAddress());
			pstmt.setString(6, c.getCompany());
			pstmt.setString(7, c.getPassword());
			
			//executes the Insert query on the DB
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		//closes the connection to the DB
		closeConnection();
		
		ArrayList<Customer> result = new ArrayList<Customer>();
		result = selectCustomer(c);
		Customer b = new Customer();
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
	    * Generic Method, used to delete a Customer from the Database
	    * @param c 
	    */
	
	public ArrayList<Customer> deleteCustomer(Customer c) {
		
		ArrayList<Customer> result = new ArrayList<Customer>();
		result = selectCustomer(c);
		
		//Establish a connection to the DB
		openConnection();
		 
		//index variable which counts the number of arguments which are not NULL
		int index = 0;
		
		//determines whether the Query shall be sent to the DB
		//in order to prevent it from executing "DELETE FROM \"Customer\" WHERE 1=1" on the DB
		boolean status = true;
		
		//Builds the query
		query = "DELETE FROM CUSTOMER WHERE 1=1";
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented
		if(c.getId() != null) {query = query + " AND ID = ?"; index++;}
		if(c.getFirstName() != null) {query = query + " AND FIRSTNAME = ?"; index++;}
		if(c.getLastName() != null) {query = query + " AND LASTNAME = ?"; index++;}
		if(c.getAddress() != null) {query = query + " AND ADDRESS = ?"; index++;}
		if(c.getTelNo() != null) {query = query + " AND TELNO = ?"; index++;}
		if(c.getMailAddress() != null) {query = query + " AND MAILADDRESS = ?"; index++;}
		if(c.getCompany() != null) {query = query + " AND COMPANY = ?"; index++;}
		if(c.getPassword() != null) {query = query + " AND PASSWORD = ?"; index++;}
		
		//in case every given attribute is null, the query shall not be executed
		if(index == 0) {status = false;} 	
		query = query+";";
			
		try {
			//prepares the query
			pstmt = connection.prepareStatement(query);
			
			//sets as much attributes to the specified index of the prepared
			//statement as were given in as delete criteria
			if(c.getPassword() != null) {pstmt.setString(index--, c.getPassword());}
			if(c.getCompany() != null) {pstmt.setString(index--, c.getCompany());}
			if(c.getMailAddress() != null) {pstmt.setString(index--, c.getMailAddress());}
			if(c.getTelNo() != null) {pstmt.setString(index--, c.getTelNo());}
			if(c.getAddress() != null) {pstmt.setString(index--, c.getAddress());}
			if(c.getLastName() != null) {pstmt.setString(index--, c.getLastName());}
			if(c.getFirstName() != null) {pstmt.setString(index--, c.getFirstName());}
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
	
	public ArrayList<Customer> selectCustomer(Customer c) {
		
		//Result ArrayList, which is returned to the invoking routine
		ArrayList<Customer> result = new ArrayList<Customer>();
		
		//Establish a connection to the DB
		openConnection();
		
		//Builds the query
		query = "SELECT * FROM CUSTOMER WHERE 1=1";
		
		//index variable which counts the number of arguments which are not NULL
		int index = 0;
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented
		if(c.getId() != null) {query = query + " AND ID = ?"; index++;}
		if(c.getFirstName() != null) {query = query + " AND FIRSTNAME = ?"; index++;}
		if(c.getLastName() != null) {query = query + " AND LASTNAME = ?"; index++;}
		if(c.getAddress() != null) {query = query + " AND ADDRESS = ?"; index++;}
		if(c.getTelNo() != null) {query = query + " AND TELNO = ?"; index++;}
		if(c.getMailAddress() != null) {query = query + " AND MAILADDRESS = ?"; index++;}
		if(c.getCompany() != null) {query = query + " AND COMPANY = ?"; index++;}
		if(c.getPassword() != null) {query = query + " AND PASSWORD = ?"; index++;}
		query = query+";";
		
		try {
			
			//prepares the query
			pstmt = connection.prepareStatement(query);
			
			//sets as much attributes to the specified index of the prepared
			//statement as were given in as delete criteria
			
			if(c.getPassword() != null) {pstmt.setString(index--, c.getPassword());}
			if(c.getCompany() != null) {pstmt.setString(index--, c.getCompany());}
			if(c.getMailAddress() != null) {pstmt.setString(index--, c.getMailAddress());}
			if(c.getTelNo() != null) {pstmt.setString(index--, c.getTelNo());}
			if(c.getAddress() != null) {pstmt.setString(index--, c.getAddress());}
			if(c.getLastName() != null) {pstmt.setString(index--, c.getLastName());}
			if(c.getFirstName() != null) {pstmt.setString(index--, c.getFirstName());}
			if(c.getId() != null) {pstmt.setInt(index--, c.getId());}
			
			//executes the query on the DB and receives the result set
			rs = pstmt.executeQuery();

			//While the result set contains results, they shall be
			//converted to Customer Objects and asserted to a result ArrayList
			while(rs.next()) {
				Customer res = new Customer();
				res.setId(rs.getInt(1));
				res.setFirstName(rs.getString(2));
				res.setLastName(rs.getString(3));
				res.setAddress(rs.getString(4));
				res.setTelNo(rs.getString(5));
				res.setMailAddress(rs.getString(6));
				res.setCompany(rs.getString(7));
				res.setPassword(rs.getString(8));
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
	
	
	public ArrayList<Customer> UpdateCustomer(Customer newC, Customer oldC) {
		
		//Establish a connection to the DB
		openConnection();
		
		//index variable which counts the number of arguments which are not NULL
		int index = 0;
		
		//Builds the query
		query = "UPDATE CUSTOMER SET ";
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented --> NEW ATTRIBUTES
		if(newC.getId() != null) {query = query + " ID = ?,"; index++;}
		if(newC.getFirstName() != null) {query = query + " FIRSTNAME = ?,"; index++;}
		if(newC.getLastName() != null) {query = query + " LASTNAME = ?,"; index++;}
		if(newC.getAddress() != null) {query = query + " ADDRESS = ?,"; index++;}
		if(newC.getTelNo() != null) {query = query + " TELNO = ?,"; index++;}
		if(newC.getMailAddress() != null) {query = query + " MAILADDRESS = ?,"; index++;}
		if(newC.getCompany() != null) {query = query + " COMPANY = ?,"; index++;}
		if(newC.getPassword() != null) {query = query + " PASSWORD = ?,"; index++;}
		
		//The last "," from the subsequent query shall be removed in order to prevent a syntax error
		//furthermore the WHERE Clause with a dummy argument shall be asserted
		query = query.substring(0, query.length()-1) + " WHERE 1=1 ";
		
		//if a given Object is not null, it shall be appended to the query and the index 
		//shall be incremented --> SEARCH CRITERIA
		if(oldC.getId() != null) {query = query + " AND ID = ?,"; index++;}
		if(oldC.getFirstName() != null) {query = query + " AND FIRSTNAME = ?"; index++;}
		if(oldC.getLastName() != null) {query = query + " AND LASTNAME = ?"; index++;}
		if(oldC.getAddress() != null) {query = query + " AND ADDRESS = ?"; index++;}
		if(oldC.getTelNo() != null) {query = query + " AND TELNO = ?"; index++;}
		if(oldC.getMailAddress() != null) {query = query + " AND MAILADDRESS = ?"; index++;}
		if(oldC.getCompany() != null) {query = query + " AND COMPANY = ?"; index++;}
		if(oldC.getPassword() != null) {query = query + " AND PASSWORD = ?"; index++;}
		
		query = query+";";
		
		try {	
			//prepares the query
			pstmt = connection.prepareStatement(query);
			
			//sets as much attributes to the specified index of the prepared
			//statement as were given as argument
			if(oldC.getPassword() != null) {pstmt.setString(index--, oldC.getPassword());}
			if(oldC.getCompany() != null) {pstmt.setString(index--, oldC.getCompany());}
			if(oldC.getMailAddress() != null) {pstmt.setString(index--, oldC.getMailAddress());}
			if(oldC.getTelNo() != null) {pstmt.setString(index--, oldC.getTelNo());}
			if(oldC.getAddress() != null) {pstmt.setString(index--, oldC.getAddress());}
			if(oldC.getLastName() != null) {pstmt.setString(index--, oldC.getLastName());}
			if(oldC.getFirstName() != null) {pstmt.setString(index--, oldC.getFirstName());}
			if(oldC.getId() != null) {pstmt.setInt(index--, oldC.getId());}
			
			if(newC.getPassword() != null) {pstmt.setString(index--, newC.getPassword());}
			if(newC.getCompany() != null) {pstmt.setString(index--, newC.getCompany());}
			if(newC.getMailAddress() != null) {pstmt.setString(index--, newC.getMailAddress());}
			if(newC.getTelNo() != null) {pstmt.setString(index--, newC.getTelNo());}
			if(newC.getAddress() != null) {pstmt.setString(index--, newC.getAddress());}
			if(newC.getLastName() != null) {pstmt.setString(index--, newC.getLastName());}
			if(newC.getFirstName() != null) {pstmt.setString(index--, newC.getFirstName());}
			if(newC.getId() != null) {pstmt.setInt(index--, newC.getId());}
			
			//Execute the Update on the DB
			pstmt.executeUpdate();
		} catch (SQLException e) {
			return null;
		}
		//Close the connection to the DB
		closeConnection();
		return selectCustomer(newC);
	}
}