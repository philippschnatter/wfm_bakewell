package bakewell.jsf;

import java.text.ParseException;
import java.util.Date;
import java.util.ArrayList;

import bakewell.beans.Customer;
import bakewell.beans.Product;
import bakewell.db.CustomerDAO;
import bakewell.db.ProductDAO;

public class jsfService {
	private String DBPath = "jdbc:h2:file:C:/Users/stefan/Documents/Workflow/git/bakewell/src/main/resources/db/wfDB";
	private CustomerDAO customerDAO = new CustomerDAO(DBPath, "sa", "");
	private ProductDAO productDAO = new ProductDAO(DBPath, "sa", "");

	public Customer createCustomer(String firstName, String lastName, String address, String telNo, String mailAddress, String company) {
		Customer customer = new Customer();
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setAddress(address);
		customer.setCompany(company);
		customer.setTelNo(telNo);
		customer.setMailAddress(mailAddress);
		
		Customer insertedCustomer = customerDAO.insertCustomer(customer);
		
//		this.customerId = insertedCustomer.getId().toString();
		
		return insertedCustomer;
	}

	public Product createProduct(Date production_Start, Date production_End, int customerId, String product_Name, String production_Contractor, String production_Facility, String transport_Contractor, Double transport_cost) {
		Product newProduct = new Product();
		
		try {
			newProduct.setProduction_Start(convertToSqlDate(production_Start));
			newProduct.setProduction_End(convertToSqlDate(production_End));
		} catch(ParseException e) {}
		
		newProduct.setCustomer_id(customerId);
		newProduct.setProduct_Name(product_Name);
		newProduct.setProduction_Contractor(production_Contractor);
		newProduct.setProduction_Facility(production_Facility);
		newProduct.setTransport_Contractor(transport_Contractor);
		newProduct.setTransport_cost(transport_cost);
		
		Product insertedProduct = productDAO.insertProduct(newProduct);
//		this.productId = insertedProduct.getId();
		
		return insertedProduct;
	}

	public Product getProduct(Integer id) {
		Product newProduct = new Product();
		newProduct.setId(id);
		
		ArrayList<Product> dbProductList = productDAO.selectProduct(newProduct);
		Product dbProduct = dbProductList.get(0);
		
		return dbProduct;
	}

	public Customer getCustomer(Integer id) {
		Customer newCustomer = new Customer();
		newCustomer.setId(id);
		
		ArrayList<Customer> dbCustomerList = customerDAO.selectCustomer(newCustomer);
		Customer dbCustomer = dbCustomerList.get(0);
		
		return dbCustomer;
	}

	
	private java.sql.Date convertToSqlDate(Date jDate) throws ParseException {
		//java.util.Date jDate = sdf.parse(jDate);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		java.sql.Date sqlDate = new java.sql.Date(jDate.getTime());
		
		return sqlDate;
	}

}
