package bakewell.activiti;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.BeforeClass;
import org.junit.Test;

public class TryoutsTest {
	private static RuntimeService runtimeService;
	private static ProcessEngine processEngine;
	private static ProcessInstance processInstance;
    private TaskService taskService;
    private FormService formService;
    private IdentityService identityService;
	private String yourEMail = "yourEMail@yourHost.com";
//	private FinalDocument finalDocument;
	private String pid;
	
	@BeforeClass
	public static void init()
	{	
		MailServiceImpl service = new MailServiceImpl();
		service.setRecipient("wfm@localhost");
		
		service.setMessage("hello");
		service.sendMessage();
		// Create Activiti process engine
		processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
				.setMailServerPort(1025)
				
				.buildProcessEngine();
		runtimeService = processEngine.getRuntimeService();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		DeploymentBuilder builder = repositoryService.createDeployment();
		String xmlFile = "EventTest.bpmn";
		builder.addClasspathResource("diagrams/"+xmlFile);
		builder.name(xmlFile);
		builder.deploy();
	}
	


	private void createNewProcess(){
		processInstance = runtimeService.startProcessInstanceByKey("myProcess");
	

		assertNotNull(processInstance.getId());
		identityService = processEngine.getIdentityService();
		taskService = processEngine.getTaskService(); 
		pid = processInstance.getProcessInstanceId();
		formService = processEngine.getFormService();
		runtimeService.setVariable(pid, "mailvar1", "value1");
		runtimeService.setVariable(pid, "mailvar2", "value2");
	}
	
	
	@Test
	public void testMail(){
		createNewProcess();
		Task nextTask = taskService.createTaskQuery().singleResult();
		HashMap<String, String> map = new HashMap<String, String>();
		formService.submitTaskFormData(nextTask.getId(), map);
	}
	
	@Test
	public void testRemindCustomer(){
		createNewProcess();
		runtimeService.setVariable(pid, "RemainderAmount", "0");
		Task nextTask = taskService.createTaskQuery().singleResult();
		HashMap<String, String> map = new HashMap<String, String>();
		formService.submitTaskFormData(nextTask.getId(), map);
		
		Task nextTask2 = taskService.createTaskQuery().singleResult();
		formService.submitTaskFormData(nextTask2.getId(), map);
		
		Task nextTask3 = taskService.createTaskQuery().singleResult();
		formService.submitTaskFormData(nextTask3.getId(), map);
		
		Task lastTasks = taskService.createTaskQuery().singleResult();
		
	}
}
