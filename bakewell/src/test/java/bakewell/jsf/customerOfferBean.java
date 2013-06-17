/**
 * 
 */
package bakewell.jsf;

import java.util.Date;

/**
 * @author stefan
 *
 */
public class customerOfferBean {


	private String company;
	private String firstName;
	private String lastName;
	private String telephoneNumber;
	private String mailAddress;
	private String businessAddress;
	private String productName;
	private Date deliveryDate;
	
	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getTelephoneNumber() {
		return telephoneNumber;
	}


	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}


	public String getMailAddress() {
		return mailAddress;
	}


	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}


	public String getBusinessAddress() {
		return businessAddress;
	}


	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public Date getDeliveryDate() {
		return deliveryDate;
	}


	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}


	public String reject()
	{
		return "reject";
	}
	
	public String approve()
	{
		System.out.println("hurra!");
		return "approve";
	}
	public customerOfferBean() {
	}
}
