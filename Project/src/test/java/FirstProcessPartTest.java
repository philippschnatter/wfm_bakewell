import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.cmd.StartProcessInstanceByMessageCmd;
import org.activiti.engine.impl.cmd.StartProcessInstanceCmd;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.BeforeClass;
import org.junit.Test;

import DanielsBeans.FinalDocument;
import static org.junit.Assert.*;

public class FirstProcessPartTest {
	private static RuntimeService runtimeService;
	private static ProcessEngine processEngine;
	private static ProcessInstance processInstance;

	private String yourEMail = "yourEMail@yourHost.com";
	private FinalDocument finalDocument;
	
	@BeforeClass
	public static void init()
	{
		/*
		ProcessEngine processEngine = ProcessEngineConfiguration
				.createStandaloneProcessEngineConfiguration()
			 	.buildProcessEngine(); 
			
			RepositoryService repositoryService = processEngine.getRepositoryService();
			RuntimeService runtimeService = processEngine.getRuntimeService();
			IdentityService identityService = processEngine.getIdentityService();
			TaskService taskService = processEngine.getTaskService(); 
			repositoryService.createDeployment()
				.addClasspathResource("chapter1/bookorder.bpmn20.xml")
				.deploy();
		
		*/
		
		
		
		// Create Activiti process engine
		processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration().buildProcessEngine();
		runtimeService = processEngine.getRuntimeService();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		DeploymentBuilder builder = repositoryService.createDeployment();
		String xmlFile = "ProductCreation_v3_daniel_notfinished.bpmn";
		builder.addClasspathResource("diagrams/"+xmlFile);
		builder.name(xmlFile);
		builder.deploy();

	
	}
	

	
	@Test
	public void testFirstPartPositive(){
		processInstance = runtimeService.startProcessInstanceByKey("ProductCreationProcess");
		assertNotNull(processInstance.getId());
		IdentityService identityService = processEngine.getIdentityService();
		TaskService taskService = processEngine.getTaskService(); 
		String piId = processInstance.getProcessInstanceId();
		// First task is evaluate requirements
		FormService formService = processEngine.getFormService();

		String evalRequirementsId = taskService.createTaskQuery().taskDefinitionKey("EvaluateRequirements").singleResult().getId();
		//assertNotNull(evalRequirements);
		FinalDocument finalDocument = new FinalDocument();
		finalDocument.MeetsBusinessGoals=true;
		runtimeService.setVariable(piId, "finalDocument", finalDocument);
		runtimeService.setVariable(piId, "test", true);
		FinalDocument doc = (FinalDocument)runtimeService.getVariable(piId, "finalDocument");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("MeetsBusinessGoals", "true");
		formService.submitTaskFormData(evalRequirementsId, map);
		
		List<Task> tasks = taskService.createTaskQuery().list();
		
		// 
	
		
		
	}
}
