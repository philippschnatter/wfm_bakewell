package bakewell.beans;

import java.util.Date;

public class Product {

	private Integer id = null;
	private Integer recipe_id = null;
	private Integer customer_id = null;
	private String product_Name = null;
	private Date deliveryDate = null;
	private Date production_Start = null;
	private Date production_End = null;
	private String production_Facility = null;
	private String production_Contractor = null;
	private String transport_Contractor = null;
	private Double transport_cost = null;
	
	public Product() {
		
	}

	public Product(Integer recipe_id, Integer customer_id, String product_Name,
			Date deliveryDate, Date production_Start, Date production_End,
			String production_Facility, String production_Contractor,
			String transport_Contractor, Double transport_cost) {
		this.recipe_id = recipe_id;
		this.customer_id = customer_id;
		this.product_Name = product_Name;
		this.deliveryDate = deliveryDate;
		this.production_Start = production_Start;
		this.production_End = production_End;
		this.production_Facility = production_Facility;
		this.production_Contractor = production_Contractor;
		this.transport_Contractor = transport_Contractor;
		this.transport_cost = transport_cost;
	}



	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRecipe_id() {
		return recipe_id;
	}
	public void setRecipe_id(Integer recipe_id) {
		this.recipe_id = recipe_id;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
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
	
	
	
}
