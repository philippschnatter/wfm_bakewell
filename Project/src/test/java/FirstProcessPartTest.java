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
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import DanielsBeans.FinalDocument;
import static org.junit.Assert.*;

public class FirstProcessPartTest {
	private static RuntimeService runtimeService;
	private static ProcessEngine processEngine;
	private static ProcessInstance processInstance;
    private TaskService taskService;
    private FormService formService;
    private IdentityService identityService;
	private String yourEMail = "yourEMail@yourHost.com";
	private FinalDocument finalDocument;
	private String pid;
	
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
	

	private void createNewProcess(){
		processInstance = runtimeService.startProcessInstanceByKey("ProductCreationProcess");
		assertNotNull(processInstance.getId());
		identityService = processEngine.getIdentityService();
		taskService = processEngine.getTaskService(); 
		pid = processInstance.getProcessInstanceId();
		formService = processEngine.getFormService();
	}
	@After
	public void Cleanup(){
		
try{
	runtimeService.deleteProcessInstance(pid, "unit test");
}catch(Exception e){
	
}

	}
	
	private void evaluateRequirements(String meetsBusinessGoals, String isNewReceipe){
		String evalRequirementsId = taskService.createTaskQuery().taskDefinitionKey("EvaluateRequirements").singleResult().getId();
		//assertNotNull(evalRequirements);
		/*FinalDocument finalDocument = new FinalDocument();
		finalDocument.MeetsBusinessGoals=true;
		runtimeService.setVariable(pid, "finalDocument", finalDocument);
		runtimeService.setVariable(pid, "test", true);
		FinalDocument doc = (FinalDocument)runtimeService.getVariable(pid, "finalDocument");*/
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("MeetsBusinessGoals", meetsBusinessGoals);
		map.put("IsNewReceipe", isNewReceipe);
		formService.submitTaskFormData(evalRequirementsId, map);
	}
	
	private void checkCreditWorthiness(String hasSolvency){
		String checkCreditId = taskService.createTaskQuery().taskDefinitionKey("CheckCreditWorthiness").singleResult().getId();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("HasSolvency", hasSolvency);
		formService.submitTaskFormData(checkCreditId, map);
	}
	
	private void assertNextTaskHasId(String taskId){
		Task nextTasks = taskService.createTaskQuery().singleResult();
		assertEquals(nextTasks.getTaskDefinitionKey(), taskId);
	}
	
	private void checkLegalConstrains(String legalConstraintsOk){
		String constraintsId = taskService.createTaskQuery().taskDefinitionKey("CheckLegalConstraints").singleResult().getId();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("LegalConstraintsOk", legalConstraintsOk);
		formService.submitTaskFormData(constraintsId, map);
	}
	
	private void findSubstitute(String substituteFound){
		String constraintsId = taskService.createTaskQuery().taskDefinitionKey("FindSubstitute").singleResult().getId();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("SubstituteFound", substituteFound);
		formService.submitTaskFormData(constraintsId, map);
	}
	
	private void compileProductionPlan(){
		String constraintsId = taskService.createTaskQuery().taskDefinitionKey("CompileProductionPlan").singleResult().getId();
		HashMap<String, String> map = new HashMap<String, String>();
		formService.submitTaskFormData(constraintsId, map);
		
	}
	
	private void gotToPremProductionPlan(){
		evaluateRequirements("true","true");
		checkCreditWorthiness("true");
		checkLegalConstrains("true");
	}
	
	private void determineFinalPrice(){
		String constraintsId = taskService.createTaskQuery().taskDefinitionKey("DetermineFinalProductPrice").singleResult().getId();
		HashMap<String, String> map = new HashMap<String, String>();
		formService.submitTaskFormData(constraintsId, map);
	}
	
	@Test
	public void testEvalRequirementsPositive(){
		createNewProcess();
		
		// First task is evaluate requirements
		evaluateRequirements("true","true");
		assertNextTaskHasId("CheckCreditWorthiness");
	}
	
	@Test
	public void testEvalRequirementsNegative(){
		createNewProcess();
		
		// First task is evaluate requirements
		evaluateRequirements("false","true");
		// should be at end state
		assertNull(taskService.createTaskQuery().singleResult());
	}
	
	@Test
	public void testCheckCreditWorthinessPositive(){
		createNewProcess();
		evaluateRequirements("true","true");
		checkCreditWorthiness("true");
		
		assertNextTaskHasId("CheckLegalConstraints");
	}
	
	@Test
	public void testCheckCreditWorthinessNegative(){
		createNewProcess();
		evaluateRequirements("true","true");
		checkCreditWorthiness("false");
		// should be at end state
		assertNull(taskService.createTaskQuery().singleResult());
	}
	
	@Test
	public void testCheckLegalConstraintsPositive(){
		createNewProcess();
		evaluateRequirements("true","true");
		checkCreditWorthiness("true");
		checkLegalConstrains("true");
		assertNextTaskHasId("CompileProductionPlan");
	}
	
	@Test
	public void testCheckLegalConstraintsWithError(){
		createNewProcess();
		evaluateRequirements("true","true");
		checkCreditWorthiness("true");
		checkLegalConstrains("false");
		assertNextTaskHasId("FindSubstitute");
	}
	
	@Test
	public void testFindSubstitutePositive(){
		createNewProcess();
		evaluateRequirements("true","true");
		checkCreditWorthiness("true");
		checkLegalConstrains("false");
		findSubstitute("true");
		assertNextTaskHasId("CheckLegalConstraints");
	}
	
	@Test
	public void testFindSubstituteNegative(){
		createNewProcess();
		evaluateRequirements("true","true");
		checkCreditWorthiness("true");
		checkLegalConstrains("false");
		findSubstitute("false");
		
		// process will end here
		assertNull(taskService.createTaskQuery().singleResult());
	}
	
	@Test
	public void testCompileProductionPlan(){
		createNewProcess();
		gotToPremProductionPlan();
		compileProductionPlan();
		assertNextTaskHasId("DetermineFinalProductPrice");
	}
	
	@Test
	public void testDetermineFinalPrice(){
		createNewProcess();
		gotToPremProductionPlan();
		compileProductionPlan();
		determineFinalPrice();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
