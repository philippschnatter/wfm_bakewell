package bakewell.jsf;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

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
public class orderTransport {
	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	String taskId = request.getParameter("taskId");
	String pid = request.getParameter("pid");
	
	jsfService jsfService = new jsfService();
	
	private String transport_Contractor = "";
	private Double transport_cost = null;
	private Integer productId = null;
	
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


	public String proceed(){
		Product dbProduct = jsfService.getProduct(productId);
		dbProduct.setTransport_Contractor(transport_Contractor);
		dbProduct.setTransport_cost(transport_cost);
		
		jsfService.updateProduct(dbProduct);

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
	
	@PostConstruct
	void init() {
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

}
