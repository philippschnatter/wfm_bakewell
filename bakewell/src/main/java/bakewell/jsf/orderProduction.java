package bakewell.jsf;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import java.util.Date;

import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;

import bakewell.beans.Customer;
import bakewell.beans.Product;
import bakewell.jsf.jsfService;

/**
 * @author Alex K
 *
 */
public class orderProduction {
	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	String taskId = request.getParameter("taskId");
	String pid = request.getParameter("pid");
	private Integer productId = null;
	
	jsfService jsfService = new jsfService();
	
	private Date production_Start = null;
	private Date production_End = null;
	private String production_Facility = null;
	private String production_Contractor = null;
	
	
	public String getTaskID() {
		return taskId;
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

	@PostConstruct
	public void init() {
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		taskId = request.getParameter("taskId");
		
		ActivitiFactory engine = ActivitiFactory.getInstance();
		ProcessEngine processEngine = engine.getProcessEngine();
		
		RuntimeService runtimeService = processEngine.getRuntimeService();
		FormService formService = processEngine.getFormService();
		
//		processEngine.getTaskService().createTaskQuery().singleResult().getProcessInstanceId();
		processEngine.getTaskService().createTaskQuery().list().get(0).getProcessInstanceId();
		this.productId = Integer.parseInt((String)runtimeService.getVariable(pid, "productid"));		
		
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
