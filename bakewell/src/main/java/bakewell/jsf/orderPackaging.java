package bakewell.jsf;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;

/**
 * @author Alex K
 *
 */
public class orderPackaging {
	private String packagingContractor;
	private double packagingCost;
	
	private String taskId;
	
	@PostConstruct
	void init() {
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		taskId = request.getParameter("taskId");
	}
	
	public String getTaskId() {
		return this.taskId;
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


	public void setPackagingCost(String packagingCost) {
		this.packagingCost = Double.parseDouble(packagingCost);
	}


	public String proceed(){
		System.out.println(this.taskId);
		ActivitiFactory engine = ActivitiFactory.getInstance();
		ProcessEngine processEngine = engine.getProcessEngine();
		FormService formService = processEngine.getFormService();
		Map<String, String> map = new HashMap<String, String>();
//		map.put("HasSolvency", "true");
//		map.put("IsNewRecipe", "true");
//		runtimeService.setVariable(engine.getInstance().toString(), "MeetsBusinessGoals", "true");
		formService.submitTaskFormData(this.taskId, map);
		
		return "proceed";
	}
}
