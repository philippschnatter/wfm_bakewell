package bakewell.jsf;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import bakewell.beans.Product;
import bakewell.beans.Recipe;

/**
 * 
 * @author Alexander Kiennast
 *
 */
public class recipeSelection {

	String taskId=null;
	jsfService jsfService=null;
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
		if(selectedRecipeId!=null){
			ActivitiFactory engine = ActivitiFactory.getInstance();
			RuntimeService runtimeService = engine.getProcessEngine().getRuntimeService();
			Map<String, Object> variableMap = new HashMap<String, Object>();
			String processName = ActivitiConstants.PROCESS_NAME;
			ProcessInstance instance = runtimeService.startProcessInstanceByKey(processName, variableMap);
			String processId = instance.getId();
			Integer productId = (Integer)runtimeService.getVariable(processId, "productId");
			
			Product temp=jsfService.getProduct(productId);
			temp.setRecipe_id(selectedRecipeId);
			
			jsfService.updateProduct(temp);
		}
		//TODO Activiti black magic stuff for proceeding to next form
		return "proceed";
	}

}
