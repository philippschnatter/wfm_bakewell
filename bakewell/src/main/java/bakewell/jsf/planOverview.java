package bakewell.jsf;

import java.util.Date;

/**
 * @author Alex K
 *
 */
public class planOverview {
	private String firstName = null;
	private String lastName = null;
	private String address = null;
	private String telNo = null;
	private String mailAddress = null;
	private String company = null;
	private String product_Name = null;
	private Date deliveryDate = null;
	private Date production_Start = null;
	private Date production_End = null;
	private String production_Facility = null;
	private String production_Contractor = null;
	private String transport_Contractor = null;
	private Double transport_cost = null;
	private String packagingContractor=null;
	private Double packagingCost=null;
	
	
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


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getTelNo() {
		return telNo;
	}


	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}


	public String getMailAddress() {
		return mailAddress;
	}


	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	public String getProduct_Name() {
		return product_Name;
	}


	public void setProduct_Name(String product_Name) {
		this.product_Name = product_Name;
	}


	public Date getDeliveryDate() {
		return deliveryDate;
	}


	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}


	public Date getProduction_Start() {
		return production_Start;
	}


	public void setProduction_Start(Date production_Start) {
		this.production_Start = production_Start;
	}


	public Date getProduction_End() {
		return production_End;
	}


	public void setProduction_End(Date production_End) {
		this.production_End = production_End;
	}


	public String getProduction_Facility() {
		return production_Facility;
	}


	public void setProduction_Facility(String production_Facility) {
		this.production_Facility = production_Facility;
	}


	public String getProduction_Contractor() {
		return production_Contractor;
	}


	public void setProduction_Contractor(String production_Contractor) {
		this.production_Contractor = production_Contractor;
	}


	public String getTransport_Contractor() {
		return transport_Contractor;
	}


	public void setTransport_Contractor(String transport_Contractor) {
		this.transport_Contractor = transport_Contractor;
	}


	public Double getTransport_cost() {
		return transport_cost;
	}


	public void setTransport_cost(Double transport_cost) {
		this.transport_cost = transport_cost;
	}


	public String getPackagingContractor() {
		return packagingContractor;
	}


	public void setPackagingContractor(String packagingContractor) {
		this.packagingContractor = packagingContractor;
	}


	public double getPackagingCost() {
		return packagingCost;
	}


	public void setPackagingCost(double packagingCost) {
		this.packagingCost = packagingCost;
	}


	public String proceed(){
		return "proceed";
	}
}
