package bakewell.jsf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;

import bakewell.beans.Ingredient2RecipeNonPersistent;
import bakewell.beans.Product;
import bakewell.beans.Recipe;
import bakewell.beans.Ingredient;
import bakewell.beans.Ingredient2Recipe;

import bakewell.db.RecipeDAO;

import bakewell.jsf.jsfService;

public class newRecipe {
	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	private String taskId = request.getParameter("taskId");
//	private String pid = request.getParameter("pid");
	private String pid = "";
	private Integer productId = null;
	private jsfService jsfService = new jsfService();
	private ArrayList<Ingredient2RecipeNonPersistent> ingredientList = null;
	
	private Integer flourId = 4;
	private Integer yeastId = 17;
	private Integer saltId = 10;
	
	private Integer recipeId = null;
	
	public Double getFlourAmount() {
		return flourAmount;
	}


	public void setFlourAmount(Double flourAmount) {
		this.flourAmount = flourAmount;
	}


	public Double getYeastAmount() {
		return yeastAmount;
	}


	public void setYeastAmount(Double yeastAmount) {
		this.yeastAmount = yeastAmount;
	}


	public Double getSaltAmount() {
		return saltAmount;
	}


	public void setSaltAmount(Double saltAmount) {
		this.saltAmount = saltAmount;
	}

	private String recipeDesc = null;
	private String recipeTitle = null;
	
	public String getRecipeDesc() {
		return recipeDesc;
	}


	public void setRecipeDesc(String recipeDesc) {
		this.recipeDesc = recipeDesc;
	}


	public String getRecipeTitle() {
		return recipeTitle;
	}


	public void setRecipeTitle(String recipeTitle) {
		this.recipeTitle = recipeTitle;
	}

	
	private Double flourAmount = null;
	private Double yeastAmount = null;
	private Double saltAmount = null;
	

	public ArrayList<Ingredient2RecipeNonPersistent> getIngredientList() {
		return ingredientList;
	}


	public void setIngredientList(ArrayList<Ingredient2RecipeNonPersistent> ingredientList) {
		this.ingredientList = ingredientList;
	}
	
	@PostConstruct
	public void init() {
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		taskId = request.getParameter("taskId");
		
		ActivitiFactory engine = ActivitiFactory.getInstance();
		ProcessEngine processEngine = engine.getProcessEngine();
		
		RuntimeService runtimeService = processEngine.getRuntimeService();
		FormService formService = processEngine.getFormService();
		
//		processEngine.getTaskService().createTaskQuery().singleResult().getProcessInstanceId();
//		processEngine.getTaskService().createTaskQuery().list().get(0).getProcessInstanceId();
//		this.productId = Integer.parseInt((String)runtimeService.getVariable(pid, "productid"));
	}
	
	public String proceed() {
		Recipe newRecipe = new Recipe();
		newRecipe.setDescription(this.recipeDesc);
		newRecipe.setName(this.recipeTitle);
		Recipe dbRecipe = jsfService.createRecipe(newRecipe);
		this.recipeId = dbRecipe.getId();
		
		ArrayList<Ingredient2Recipe> newIng2RecList = new ArrayList<Ingredient2Recipe>();
		Ingredient2Recipe flourIng2Rec = new Ingredient2Recipe();
		flourIng2Rec.setIngredient_id(4);
		flourIng2Rec.setAmount(flourAmount);
		flourIng2Rec.setRecipe_id(recipeId);
		
		Ingredient2Recipe yeastIng2Rec = new Ingredient2Recipe();
		yeastIng2Rec.setIngredient_id(17);
		yeastIng2Rec.setAmount(yeastAmount);
		yeastIng2Rec.setRecipe_id(recipeId);
		
		Ingredient2Recipe saltIng2Rec = new Ingredient2Recipe();
		saltIng2Rec.setIngredient_id(10);
		saltIng2Rec.setAmount(saltAmount);
		saltIng2Rec.setRecipe_id(recipeId);
		
		newIng2RecList.add(flourIng2Rec);
		newIng2RecList.add(yeastIng2Rec);
		newIng2RecList.add(saltIng2Rec);
		
		dbRecipe.setIngredients(newIng2RecList);
		jsfService.updateRecipe(dbRecipe);
		
		ActivitiFactory engine = ActivitiFactory.getInstance();
		ProcessEngine processEngine = engine.getProcessEngine();
		
//		RuntimeService runtimeService = processEngine.getRuntimeService();
		FormService formService = processEngine.getFormService();
		Map<String, String> map = new HashMap<String, String>();
//		map.put("IsNewRecipe", "true");
//		runtimeService.setVariable(engine.getInstance().toString(), "MeetsBusinessGoals", "true");
		formService.submitTaskFormData(this.taskId, map);
		
		return "proceed";
	}
}