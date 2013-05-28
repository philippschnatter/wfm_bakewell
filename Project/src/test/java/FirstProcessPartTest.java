import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.cmd.StartProcessInstanceByMessageCmd;
import org.activiti.engine.impl.cmd.StartProcessInstanceCmd;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class FirstProcessPartTest {
	private static RuntimeService runtimeService;
	private static ProcessEngine processEngine;
	private static ProcessInstance processInstance;

	private String yourEMail = "yourEMail@yourHost.com";
	
	@BeforeClass
	public static void init()
	{
		
		// Create Activiti process engine
		processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration().buildProcessEngine();
		
		RepositoryService repositoryService = processEngine.getRepositoryService();
		DeploymentBuilder builder = repositoryService.createDeployment();
		String xmlFile = "ProductCreation_v3_daniel_notfinished.bpmn";
		builder.addClasspathResource("diagrams/"+xmlFile);
		builder.name(xmlFile);
		builder.deploy();
		runtimeService = processEngine.getRuntimeService();
	}
	
	@Test
	public void startProcessInstance() 
	{
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("price", 150);
		processInstance = runtimeService.startProcessInstanceById("ProductCreationProcess");
		assertNotNull(processInstance.getId());
		
	}
}
