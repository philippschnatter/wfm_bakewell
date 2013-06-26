package bakewell.jsf;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;

/**
 * @author Alex K
 *
 */
public class orderProduction {
	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	String taskId = request.getParameter("taskId");

	
	private Date production_Start = null;
	private Date production_End = null;
	private String production_Facility = "";
	private String production_Contractor = "";
	
	
	public String getTaskID() {
		return taskId;
	}
	
	public Date getProduction_Start() {
		return production_Start;
	}


	@SuppressWarnings("deprecation")
	public void setProduction_Start(String production_Start) {
		this.production_Start = new Date(production_Start);
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


	public String proceed(){
		System.out.println("order production proceed start");
		ActivitiFactory engine = ActivitiFactory.getInstance();
		ProcessEngine processEngine = engine.getProcessEngine();
//		RuntimeService runtimeService = processEngine.getRuntimeService();
		FormService formService = processEngine.getFormService();
		Map<String, String> map = new HashMap<String, String>();
//		map.put("HasSolvency", "true");
//		map.put("IsNewRecipe", "true");
//		runtimeService.setVariable(engine.getInstance().toString(), "MeetsBusinessGoals", "true");
		formService.submitTaskFormData(this.taskId, map);
		System.out.println("order production proceed");
		return "proceed";
	}
}
