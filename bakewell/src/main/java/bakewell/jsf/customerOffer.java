package bakewell.jsf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.faces.context.FacesContext;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.FormService;

import org.activiti.cdi.BusinessProcess;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;
import javax.servlet.http.*;

import bakewell.activiti.ActivitiConstants;
import bakewell.db.CustomerDAO;
import bakewell.db.ProductDAO;
import bakewell.db.IngredientDAO;
import bakewell.db.Ingredient2RecipeDAO;
import bakewell.db.RecipeDAO;

import bakewell.beans.Customer;
import bakewell.beans.Product;
import bakewell.beans.Ingredient;
import bakewell.beans.Ingredient2Recipe;
import bakewell.beans.Recipe;

import bakewell.jsf.JsfService;

/**
 * @author Alex K
 *
 */
public class customerOffer {
	
//	ActivitiFactory activiti = ActivitiFactory.getInstance();
	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	String taskId = request.getParameter("taskId");
	JsfService jsfService = new JsfService();
	
	// Customer variables
	private Integer customerId = null;
	private String firstName = null;
	private String lastName = null;
	private String address = null;
	private String telNo = null;
	private String mailAddress = null;
	private String company = null;
		
	// Recipe variables
	private String recipeName = null;
	private String recipeDescription = null;
	private Integer recipe_id = null;
	
	// Product variables
	private Date production_Start = null;
	private Date production_End = null;
	private Date deliveryDate = null;
	private Integer productId = null;
	private String product_Name = null;

	private String production_Facility = null;
	private String production_Contractor = null;
	private String transport_Contractor = null;
	private Double transport_cost = null;

	
//	public void addToIngredientList(String ingId, String amount) {
//		Ingredient2Recipe AddedIngredient = new Ingredient2Recipe(Integer.parseInt(this.recipe_id), Integer.parseInt(ingId), Double.parseDouble(amount));
//		this.Ingredient2RecipeList.add(AddedIngredient);
//	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
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

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	
	
	public ArrayList<Ingredient2Recipe> getIngredient2RecipeList() {
		return Ingredient2RecipeList;
	}

	public void setIngredient2RecipeList(
			ArrayList<Ingredient2Recipe> ingredient2RecipeList) {
		Ingredient2RecipeList = ingredient2RecipeList;
	}

	
	// Ingredient variables
	private ArrayList<Ingredient2Recipe> Ingredient2RecipeList = null;
	
	public String getTaskId() {
		return taskId;
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
	
	public String approve(){
		Customer newCustomer = jsfService.createCustomer(firstName, lastName, address, telNo, mailAddress, company);
		this.customerId = newCustomer.getId();
		Product newProduct = jsfService.createProduct(production_Start, production_End, customerId, product_Name, production_Contractor, production_Facility, transport_Contractor, transport_cost);
		this.productId = newProduct.getId();
//		createRecipe();
//		createIngredient2Recipe();
		
		ActivitiFactory engine = ActivitiFactory.getInstance();
		ProcessEngine processEngine = engine.getProcessEngine();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		FormService formService = processEngine.getFormService();
		Map<String, String> map = new HashMap<String, String>();
		map.put("MeetsBusinessGoals", "true");
//		runtimeService.setVariable(engine.getInstance().toString(), "MeetsBusinessGoals", "true");
		formService.submitTaskFormData(this.taskId, map);
		
		return "approve";
	}
	public String reject(){
		return "reject";
	}
	

	
	String createRecipe() {
		Recipe newRecipe = new Recipe();
		
		newRecipe.setName(recipeName);
		newRecipe.setDescription(recipeDescription);
		
		RecipeDAO recipeDAO = new RecipeDAO("jdbc:h2:file:C:/Users/stefan/Documents/Workflow/git/bakewell/src/main/resources/db/wfDB", "sa", "");
		Recipe insertedRecipe = recipeDAO.insertRecipe(newRecipe);
		
		this.recipe_id = insertedRecipe.getId();
		
		return "success";
	}
	
	HashMap<String, Double> createIngredientList() {
		HashMap ingList = new HashMap();
		
//		IngredientDAO ingDAO = new IngredientDAO("jdbc:h2:file:C:/Users/stefan/Documents/Workflow/git/bakewell/src/main/resources/db/wfDB", "sa", "");
//		ingDAO.select
		
		return ingList;
	}
	
	String createIngredient2Recipe() {
		HashMap<String, Double> ingList = createIngredientList();
		IngredientDAO ingDAO = new IngredientDAO("jdbc:h2:file:C:/Users/stefan/Documents/Workflow/git/bakewell/src/main/resources/db/wfDB", "sa", "");
		
		for(Map.Entry<String, Double> entry : ingList.entrySet()) {
			String ingName = entry.getKey();
			Double ingAmount = entry.getValue();
			Ingredient2Recipe newI2R = new Ingredient2Recipe();
			
			newI2R.setRecipe_id(recipe_id);
//			newI2R.setIngredient_id(ingredient_id);
			
		}		
		return "success";
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}
	
	public void addRecipe() {
		Recipe newRecipe = new Recipe();
		newRecipe.setName(this.recipeName);
		newRecipe.setDescription(this.recipeDescription);
		
		RecipeDAO recipeDAO = new RecipeDAO("jdbc:h2:file:C:/Users/stefan/Documents/Workflow/git/bakewell/src/main/resources/db/wfDB", "sa", "");
		Recipe returnedRecipe = recipeDAO.insertRecipe(newRecipe);
		
		this.setRecipe_id(returnedRecipe.getId());
	}
			
//	public ArrayList<Ingredient2Recipe> getIngredient2RecipeList() {
//		IngredientDAO ingDAO = new IngredientDAO("jdbc:h2:file:C:/Users/stefan/Documents/Workflow/git/bakewell/src/main/resources/db/wfDB", "sa", "");
//		ArrayList<Ingredient> ingredientList = ingDAO.selectAllIngredients();
//		
//		ArrayList<Ingredient2Recipe> ingredient2RecipeList;
//		
//		Iterator<Ingredient> alIt = ingredientList.iterator();
//		
//		while(alIt.hasNext()) {
//			Ingredient2Recipe newIng2Rec;
//			newIng2Rec.setIngredient_id(alIt.next().getId());
//			newIng2Rec.set
//			
//		}
//		
//		return ingredientList;
//	}
	
	
	public void submit() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String processName = ActivitiConstants.PROCESS_NAME;
		
		// Start the business process and pass the calculated price as a start variable
		Map<String, Object> variableMap = new HashMap<String, Object>();
//		variableMap.put(price_var, price);
		
		ActivitiFactory engine = ActivitiFactory.getInstance();
		
//		engine.init("diagrams/ProductCreation_v3_daniel_notfinished.bpmn");
		engine.init();
		
		RuntimeService runtimeService = engine.getProcessEngine().getRuntimeService();
		ProcessInstance instance = runtimeService.startProcessInstanceByKey(processName, variableMap);
		
		// Retrieve process ID
		String id = instance.getId();
//		this.setInstanceId(id);
		
		// Retrieve next task
//		List<Task> tasks = engine.getProcessEngine().getTaskService().createTaskQuery().taskAssignee("").processInstanceId(instance.getId()).list();
		//System.out.println(tasks);
		System.out.println("abc");
		//return tasks.toString();
		
		//		if (tasks.size() > 0)
//		{
//			// Get first task
//			Task task = tasks.get(0);
//			this.setTaskId(task.getId());
//	
//			this.setPage("/"+engine.getProcessEngine().getFormService().getTaskFormData(task.getId()).getFormKey());
//			this.setTaskId(task.getId());
//			
//			return "success";
//		}
 
//		taskService = engine.getProcessEngine().getTaskService();
//		formService = engine.getProcessEngine().getFormService();
		//System.out.println(taskService.createTaskQuery().taskAssignee("Controller").list().toString());

	}
}
