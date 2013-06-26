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
		this.productId = (Integer)runtimeService.getVariable(pid, "productid");
		
		Product dbProduct = jsfService.getProduct(productId);
		Customer dbCustomer = jsfService.getCustomer(dbProduct.getCustomer_id());
		
		this.address = dbCustomer.getAddress();
		this.company = dbCustomer.getCompany();
		this.customerId = dbCustomer.getId();
		this.firstName = dbCustomer.getFirstName();
		this.lastName = dbCustomer.getLastName();
		this.mailAddress = dbCustomer.getMailAddress();
		this.telNo = dbCustomer.getTelNo();
			
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
