/**
 * 
 */
package bakewell.jsf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.FormService;

import bakewell.jsf.ActivitiFactory;
import bakewell.activiti.ActivitiConstants;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;

/**
 * @author stefan
 *
 */
public class tasklistBean {
//		processEngine = ActivitiFactory.getProcessEngine();
//	  @Produces
//	  @Named("processDefinitionList")
//	  public List getProcessDefinitionList() {
//	    return repositoryService.createProcessDefinitionQuery().list();
	
	private String instanceId = "";
	private String taskId = "";
//	private List<ItemEntry> items;
	private String page = "";
	TaskService taskService = null;
	FormService formService = null;
	
	
	public tasklistBean() {
	}
	
	public String getInstanceId() { return this.instanceId; }
	public void setInstanceId(String instanceId) { this.instanceId = instanceId; }
	
	public String getTaskId() { return this.taskId; }
	public void setTaskId(String taskId) { this.taskId = taskId; }
	
	public String getPage() { return page; }
	public void setPage(String page) { this.page = page; }
	
	@PostConstruct
	public void init() {
		
		System.out.println("abc");
		FacesContext fc = FacesContext.getCurrentInstance();
		String processName = ActivitiConstants.PROCESS_NAME;
		// Get values for entries in messages.properties
//		ResourceBundle bundle = fc.getApplication().getResourceBundle(fc, "m");
		
//		String processName = bundle.getString("app_process_name");
		
//		String price_var = bundle.getString("newOrder_price_var");
		// Get request-parameter sent from newOrder.jsf to show-case method parameter invocation
		// as JSF does not support calls to methods with parameters!
		
//		String processDescription = fc.getExternalContext().getRequestParameterMap().get("processDescription").toString();
		
		// calculate the total price
//		Double price = this.calculateTotalPrice();
		
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
		this.setInstanceId(id);
		
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
 
		taskService = engine.getProcessEngine().getTaskService();
		formService = engine.getProcessEngine().getFormService();
		//System.out.println(taskService.createTaskQuery().taskAssignee("Controller").list().toString());
	}
	
	public List<Task> start() {
		return taskService.createTaskQuery().list();
	}
	
	public FormService getFormService() {
		return formService;
	}
	
	public void test() {
		System.out.println("testxxx");
	}
}
