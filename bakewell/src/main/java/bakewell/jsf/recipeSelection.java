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

import bakewell.beans.Product;
import bakewell.beans.Recipe;
import bakewell.jsf.jsfService;

/**
 * 
 * @author Alexander Kiennast
 *
 */
public class recipeSelection {
	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	String taskId = request.getParameter("taskId");
	String pid = request.getParameter("pid");
	
	jsfService jsfService = new jsfService();
	
	private String transport_Contractor = "";
	private Double transport_cost = null;
	private Integer productId = null;
	
	ArrayList<Recipe> recipeList=null;
	Integer selectedRecipeId=null;

	@PostConstruct
	public void init() { 
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String taskId = request.getParameter("taskId");
		jsfService jsfService = new jsfService();
		recipeList=jsfService.getAllRecipes();
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public ArrayList<Recipe> getRecipeList() {
		return recipeList;
	}

	public void setRecipeList(ArrayList<Recipe> recipeList) {
		this.recipeList = recipeList;
	}

	public Integer getSelectedRecipe() {
		return selectedRecipeId;
	}

	public void setSelectedRecipe(Integer selectedRecipeId) {
		this.selectedRecipeId = selectedRecipeId;
	}
	
	public String proceed(){
		ActivitiFactory engine = ActivitiFactory.getInstance();
		ProcessEngine processEngine = engine.getProcessEngine();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		FormService formService = processEngine.getFormService();
//		RuntimeService runtimeService = processEngine.getRuntimeService();
		Map<String, String> map = new HashMap<String, String>();
		
		if(selectedRecipeId!=null){
			String processId = null;
			Integer productId = (Integer)runtimeService.getVariable(processId, "productId");
			
			Product temp=jsfService.getProduct(productId);
			temp.setRecipe_id(selectedRecipeId);
			
			jsfService.updateProduct(temp);
			
			map.put("IsNewRecipe", "false");
		}
		else {
			map.put("IsNewRecipe", "true");
		}

		
//		map.put("IsNewRecipe", "true");
//		runtimeService.setVariable(engine.getInstance().toString(), "MeetsBusinessGoals", "true");
		formService.submitTaskFormData(this.taskId, map);
		

		//TODO Activiti black magic stuff for proceeding to next form
		return "proceed";
	}

}
