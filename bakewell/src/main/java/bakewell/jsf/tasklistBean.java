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

import bakewell.jsf.ActivitiFactory;
import bakewell.activiti.ActivitiConstants;

/**
 * @author stefan
 *
 */
public class tasklistBean {
	private String instanceId = "";
	private String taskId = "";
//	private List<ItemEntry> items;
	private String page = "";
	
	public tasklistBean() {
	}
	
	public String getInstanceId() { return this.instanceId; }
	public void setInstanceId(String instanceId) { this.instanceId = instanceId; }
	
	public String getTaskId() { return this.taskId; }
	public void setTaskId(String taskId) { this.taskId = taskId; }
	
	public String getPage() { return page; }
	public void setPage(String page) { this.page = page; }
	
	public String start() {
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
		List<Task> tasks = engine.getProcessEngine().getTaskService().createTaskQuery().taskAssignee("").processInstanceId(instance.getId()).list();
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
 
 
		return "xxx";
	}
	
	public void test() {
		System.out.println("testxxx");
	}
}
