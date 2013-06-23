package bakewell.jsf;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

import bakewell.beans.Customer;
import bakewell.beans.Product;

/**
 * @author Alex K
 *
 */
public class customerOffer {
	
//	ActivitiFactory activiti = ActivitiFactory.getInstance();
	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	String taskId = request.getParameter("taskId");
	
	private String firstName = null;
	private String lastName = null;
	private String address = null;
	private String telNo = null;
	private String mailAddress = null;
	private String company = null;
	private String product_Name = null;
	private String deliveryDate = null;
	
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
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	public String approve(){
		createCustomer();
		createProduct();
		
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
	
	private String createCustomer() {
		Customer customer = new Customer();
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setAddress(address);
		customer.setCompany(company);
		
		CustomerDAO customerDAO = new CustomerDAO("jdbc:h2:file:C:/Users/stefan/Documents/Workflow/git/bakewell/src/main/resources/db/wfDB", "sa", "");
		Customer insertedCustomer = customerDAO.insertCustomer(customer);
		System.out.println(insertedCustomer.getId().toString());
		System.out.println(customer.getFirstName());
//		CustomerDAO cDAO = new CustomerDAO("jdbc:h2:file:C:/Users/stefan/Documents/Workflow/git/bakewell/src/main/resources/db/wfDB", "sa", "");
//		Customer testCustomer = new Customer("firstNameTest1", "lastNameTest1", "addressTest1", "1234567890", "test1@mail1.com", "testCompany1", "testPassword1");
//		Customer failCustomer = new Customer(); 
//		ArrayList<Customer> result = new ArrayList<Customer>();
//		
//		Customer a = cDAO.insertCustomer(testCustomer);
//		result = cDAO.selectCustomer(testCustomer);
	
		return "success";
	}
	
	private String createProduct() {
		
		
		return "success";
	}
	
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
