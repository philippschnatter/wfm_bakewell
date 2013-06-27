package bakewell.jsf;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import javax.annotation.PostConstruct;

import bakewell.beans.Product;
import bakewell.beans.Customer;

import bakewell.jsf.jsfService;

/**
 * @author Alex K
 *
 */
public class creditCheck {
	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	String taskId = request.getParameter("taskId");
	String pid = request.getParameter("pid");
	
	jsfService jsfService = new jsfService();
	
	// Customer variables
	private Integer customerId = null;
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public jsfService getJsfService() {
		return jsfService;
	}

	public void setJsfService(jsfService jsfService) {
		this.jsfService = jsfService;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public String getRecipeDescription() {
		return recipeDescription;
	}

	public void setRecipeDescription(String recipeDescription) {
		this.recipeDescription = recipeDescription;
	}

	public Integer getRecipe_id() {
		return recipe_id;
	}

	public void setRecipe_id(Integer recipe_id) {
		this.recipe_id = recipe_id;
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

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProduct_Name() {
		return product_Name;
	}

	public void setProduct_Name(String product_Name) {
		this.product_Name = product_Name;
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

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	private String firstName = "";
	private String lastName = "";
	private String address = "";
	private String telNo = "";
	private String mailAddress = "";
	private String company = "";
		
	// Recipe variables
	private String recipeName = "";
	private String recipeDescription = "";
	private Integer recipe_id = null;
	
	// Product variables
	private Date production_Start = null;
	private Date production_End = null;
	private Date deliveryDate = null;
	private Integer productId = null;
	private String product_Name = "";

	private String production_Facility = "";
	private String production_Contractor = "";
	private String transport_Contractor = "";
	private Double transport_cost = null;

	@PostConstruct
	void init() {
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		taskId = request.getParameter("taskId");
		
		ActivitiFactory engine = ActivitiFactory.getInstance();
		ProcessEngine processEngine = engine.getProcessEngine();
		
		RuntimeService runtimeService = processEngine.getRuntimeService();
		FormService formService = processEngine.getFormService();
		
		processEngine.getTaskService().createTaskQuery().singleResult().getProcessInstanceId();
		this.productId = Integer.parseInt((String)runtimeService.getVariable(pid, "productid"));
		
		Product dbProduct = jsfService.getProduct(productId);
		Customer dbCustomer = jsfService.getCustomer(dbProduct.getCustomer_id());
		
		this.address = dbCustomer.getAddress();
		this.company = dbCustomer.getCompany();
		this.customerId = dbCustomer.getId();
		this.firstName = dbCustomer.getFirstName();
		this.lastName = dbCustomer.getLastName();
		this.mailAddress = dbCustomer.getMailAddress();
		this.telNo = dbCustomer.getTelNo();
		
		System.out.println("productid: " + productId);
			
	}
	
	public String getTaskId() {
		return taskId;
	}
	
	public String approve(){
		System.out.println(this.taskId);
		ActivitiFactory engine = ActivitiFactory.getInstance();
		ProcessEngine processEngine = engine.getProcessEngine();
		
//		RuntimeService runtimeService = processEngine.getRuntimeService();
		FormService formService = processEngine.getFormService();
		Map<String, String> map = new HashMap<String, String>();
		map.put("HasSolvency", "true");
		map.put("IsNewRecipe", "true");
//		runtimeService.setVariable(engine.getInstance().toString(), "MeetsBusinessGoals", "true");
		formService.submitTaskFormData(this.taskId, map);
		
		
		return "approve";
	}
	public String reject(){
		return "reject";
	}
}
