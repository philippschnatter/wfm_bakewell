package bakewell.activiti;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.cmd.StartProcessInstanceByMessageCmd;
import org.activiti.engine.impl.cmd.StartProcessInstanceCmd;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MainProcessTest {
	private static RuntimeService runtimeService;
	private static ProcessEngine processEngine;
	private static ProcessInstance processInstance;
    private TaskService taskService;
    private FormService formService;
    private IdentityService identityService;
	private String yourEMail = "yourEMail@yourHost.com";
//	private FinalDocument finalDocument;
	private String pid;
	
	private static final String USER_CONTROLLER = "Controller";
	private static final String USER_CUSTOMER = "Customer";
	private static final String USER_PROCESSMANAGER = "ProcessManager";
	
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
		processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
				.setMailServerPort(1025)
				.buildProcessEngine();
		runtimeService = processEngine.getRuntimeService();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		DeploymentBuilder builder = repositoryService.createDeployment();
		String xmlFile = "ProductCreation_v3_daniel_WithMail.bpmn";
		builder.addClasspathResource("diagrams/"+xmlFile);
		builder.name(xmlFile);
		builder.deploy();

		// Create our 3 users
		IdentityService identityService = processEngine.getIdentityService();
		User controller = identityService.newUser(USER_CONTROLLER);
		identityService.saveUser(controller);
		User customer = identityService.newUser(USER_CUSTOMER);
		identityService.saveUser(controller);
		User processManager = identityService.newUser(USER_PROCESSMANAGER);
		identityService.saveUser(controller);
	}
	

	private void createNewProcess(){
		processInstance = runtimeService.startProcessInstanceByKey("ProductCreationProcess");
		assertNotNull(processInstance.getId());

		taskService = processEngine.getTaskService(); 
		pid = processInstance.getProcessInstanceId();
		formService = processEngine.getFormService();
	
		runtimeService.setVariable(pid, ActivitiConstants.CV_REMAINDER_AMOUNT, "0");
		runtimeService.setVariable(pid, ActivitiConstants.MAIL_INFORM_PRODUCTMANAGER_SUBJECT, "New Product");
		runtimeService.setVariable(pid, ActivitiConstants.MAIL_INFORM_PRODUCTMANAGER_TEXT, "Dear Manager, <br/> We have invented a new product");
		runtimeService.setVariable(pid, ActivitiConstants.MAIL_SEND_OFFER_SUBJECT, "Offer for Product");
		runtimeService.setVariable(pid, ActivitiConstants.MAIL_SEND_OFFER_TEXT, "Dear Customer, <br/> We made a new offer for your product </br> please review it");
	
		runtimeService.setVariable(pid, ActivitiConstants.MAIL_SEND_TERMINATION_SUBJECT, "Termination of job");
		runtimeService.setVariable(pid, ActivitiConstants.MAIL_SEND_TERMINATION_TEXT, "Dear customer <br/> Our offer has expired");
	
	}
	
	@After
	public void Cleanup(){
		
		try{
			runtimeService.deleteProcessInstance(pid, "unit test");
		}catch(Exception e){
	
		}
	}
	
	// assert helpers
	private void assertNextTaskHasId(String taskId){
		Task nextTasks = taskService.createTaskQuery().singleResult();
		assertEquals(nextTasks.getTaskDefinitionKey(), taskId);
	}
	private void assertShouldBeInState(String state){
		List<String> activeActivityIds = runtimeService.getActiveActivityIds(pid);
		assertTrue(activeActivityIds.contains(state));
		assertEquals(activeActivityIds.size(), 1);
		
	}
	
	private void assertShouldContainStates(List<String> states){
		List<String> activeActivityIds = runtimeService.getActiveActivityIds(pid);
		for (String state : states) {
			assertTrue(activeActivityIds.contains(state));
		}
		
		assertEquals(activeActivityIds.size(), states.size());	
	}
	
	// test state helpers
	private void evaluateRequirements(String meetsBusinessGoals, String isNewReceipe){
		String evalRequirementsId = taskService.createTaskQuery().taskDefinitionKey("EvaluateRequirements").
				taskAssignee(USER_CONTROLLER).singleResult().getId();
		//assertNotNull(evalRequirements);
		/*FinalDocument finalDocument = new FinalDocument();
		finalDocument.MeetsBusinessGoals=true;
		runtimeService.setVariable(pid, "finalDocument", finalDocument);
		runtimeService.setVariable(pid, "test", true);
		FinalDocument doc = (FinalDocument)runtimeService.getVariable(pid, "finalDocument");*/
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("MeetsBusinessGoals", meetsBusinessGoals);
		map.put("IsNewRecipe", isNewReceipe);
		formService.submitTaskFormData(evalRequirementsId, map);
	}
		
	private void checkCreditWorthiness(String hasSolvency){
		String checkCreditId = taskService.createTaskQuery().taskDefinitionKey("CheckCreditWorthiness").
				taskAssignee(USER_CONTROLLER).singleResult().getId();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("HasSolvency", hasSolvency);
		formService.submitTaskFormData(checkCreditId, map);
	}
	
	private void checkExistingRecipe(String isNewRecipe){
		String checkExistingRecipesId = taskService.createTaskQuery().taskDefinitionKey("CheckExistingRecipes").
				taskAssignee(USER_PROCESSMANAGER).singleResult().getId();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("IsNewRecipe", isNewRecipe);
		formService.submitTaskFormData(checkExistingRecipesId, map);
	}
	private void createRecipe(){
		String createRecipeId = taskService.createTaskQuery().taskDefinitionKey("CreateRecipe").
				taskAssignee(USER_PROCESSMANAGER).singleResult().getId();
		HashMap<String, String> map = new HashMap<String, String>();
		formService.submitTaskFormData(createRecipeId, map);
	}
	
	private void checkLegalConstrains(String legalConstraintsOk){
		String constraintsId = taskService.createTaskQuery().taskDefinitionKey("CheckLegalConstraints").
				taskAssignee(USER_PROCESSMANAGER).singleResult().getId();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("LegalConstraintsOk", legalConstraintsOk);
		formService.submitTaskFormData(constraintsId, map);
	}
	
	private void findSubstitute(String substituteFound){
		String constraintsId = taskService.createTaskQuery().taskDefinitionKey("FindSubstitute").
				taskAssignee(USER_PROCESSMANAGER).singleResult().getId();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("SubstituteFound", substituteFound);
		formService.submitTaskFormData(constraintsId, map);
	}
	
	private void compileProductionPlan(){
		String constraintsId = taskService.createTaskQuery().taskDefinitionKey("CompileProductionPlan").
				taskAssignee(USER_PROCESSMANAGER).singleResult().getId();
		HashMap<String, String> map = new HashMap<String, String>();
		formService.submitTaskFormData(constraintsId, map);
		
	}
	
	private void planProductionProcess(){
		String constraintsId = taskService.createTaskQuery().taskDefinitionKey("BookProductionFacility").
				taskAssignee(USER_PROCESSMANAGER).singleResult().getId();
		HashMap<String, String> map = new HashMap<String, String>();
		formService.submitTaskFormData(constraintsId, map);
		
		String constraintsId2 = taskService.createTaskQuery().taskDefinitionKey("OrderTransport").singleResult().getId();
		HashMap<String, String> map2 = new HashMap<String, String>();
		formService.submitTaskFormData(constraintsId2, map2);
	}
	
	private void gotToPremProductionPlan(){
		evaluateRequirements("true","true");
		checkCreditWorthiness("true");
		checkExistingRecipe("false");
		createRecipe();
		checkLegalConstrains("true");
		planProductionProcess();
	}
	
	private void determineFinalPrice(){
		String constraintsId = taskService.createTaskQuery().taskDefinitionKey("DetermineFinalProductPrice").
				taskAssignee(USER_PROCESSMANAGER).singleResult().getId();
		HashMap<String, String> map = new HashMap<String, String>();
		formService.submitTaskFormData(constraintsId, map);
	}
	
	private void checkOffer(String isOfferAccepted){
		String constraintsId = taskService.createTaskQuery().taskDefinitionKey("CheckOffer").
				taskAssignee(USER_CUSTOMER).singleResult().getId();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("OfferResult", isOfferAccepted);
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
	public void testFirstTaskWithWrongUser(){
		createNewProcess();
		Task taskGeneral = taskService.createTaskQuery().
				taskDefinitionKey("EvaluateRequirements").
				singleResult();
		
		Task taskForCustomer = taskService.createTaskQuery().
				taskDefinitionKey("EvaluateRequirements").
				taskAssignee("Customer").
				singleResult();
		
		// wrong user
		assertNull(taskForCustomer);
		
		Task taskForProcessManager = taskService.createTaskQuery().
				taskDefinitionKey("EvaluateRequirements").
				taskAssignee("ProcessManager").
				singleResult();
		
		// wrong user
		assertNull(taskForProcessManager);
		
		Task taskForController = taskService.createTaskQuery().
				taskDefinitionKey("EvaluateRequirements").
				taskAssignee("Controller").
				singleResult();
		
		// correct user
		assertNotNull(taskForController);
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
		
		List<String> resultStates = new LinkedList<String>();
		resultStates.add("CheckExistingRecipes");
		resultStates.add("BookProductionFacility");
		resultStates.add("OrderTransport");
		assertShouldContainStates(resultStates);
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
	public void testCheckExistingRecipesPositive(){
		createNewProcess();
		evaluateRequirements("true","true");
		checkCreditWorthiness("true");
		checkExistingRecipe("true");
		
		List<String> resultStates = new LinkedList<String>();
		resultStates.add("BookProductionFacility");
		resultStates.add("OrderTransport");
		assertShouldContainStates(resultStates);
	}
	
	@Test
	public void testCheckExistingRecipesNegative(){
		createNewProcess();
		evaluateRequirements("true","true");
		checkCreditWorthiness("true");
		checkExistingRecipe("true");
		
		List<String> resultStates = new LinkedList<String>();
		resultStates.add("BookProductionFacility");
		resultStates.add("OrderTransport");
		resultStates.add("CreateRecipe");
		assertShouldContainStates(resultStates);
	}
	
	
	@Test
	public void testCreateRecipe(){
		createNewProcess();
		evaluateRequirements("true","true");
		checkCreditWorthiness("true");
		checkExistingRecipe("false");
		createRecipe();
		
		List<String> resultStates = new LinkedList<String>();
		resultStates.add("BookProductionFacility");
		resultStates.add("OrderTransport");
		resultStates.add("CheckLegalConstraints");
		assertShouldContainStates(resultStates);
	}
	
	@Test
	public void testCheckLegalConstraintsPositive(){
		createNewProcess();
		evaluateRequirements("true","true");
		checkCreditWorthiness("true");
		checkExistingRecipe("false");
		createRecipe();
		checkLegalConstrains("true");
		planProductionProcess();
		assertNextTaskHasId("CompileProductionPlan");
	}
	
	
	@Test
	public void testCheckLegalConstraintsWithError(){
		createNewProcess();
		evaluateRequirements("true","true");
		checkCreditWorthiness("true");
		checkExistingRecipe("false");
		createRecipe();
		checkLegalConstrains("false");
		planProductionProcess();
		assertNextTaskHasId("FindSubstitute");
	}
	
	@Test
	public void testFindSubstitutePositive(){
		createNewProcess();
		evaluateRequirements("true","true");
		checkCreditWorthiness("true");
		checkExistingRecipe("false");
		createRecipe();
		checkLegalConstrains("false");
		findSubstitute("true");
		planProductionProcess();
		assertNextTaskHasId("CheckLegalConstraints");
	}
	
	@Test
	public void testFindSubstituteNegative(){
		createNewProcess();
		evaluateRequirements("true","true");
		checkCreditWorthiness("true");
		checkExistingRecipe("false");
		createRecipe();
		checkLegalConstrains("false");
		planProductionProcess();
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
			
		List<String> resultStates = new LinkedList<String>();
		resultStates.add("WaitForCustomer");
		resultStates.add("CheckOffer");
		assertShouldContainStates(resultStates);
	}
	
	/*
	@Test
	public void testTimer(){
		createNewProcess();
		gotToPremProductionPlan();
		compileProductionPlan();
		determineFinalPrice();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String> resultStates = new LinkedList<String>();
		resultStates.add("WaitForCustomer");
		resultStates.add("CheckOffer");
		assertShouldContainStates(resultStates);
	}*/
	
	@Test
	public void testCheckOffer(){
		createNewProcess();
		gotToPremProductionPlan();
		compileProductionPlan();
		determineFinalPrice();
		checkOffer("Accepted");
			
		assertNull(taskService.createTaskQuery().singleResult());
	}

}
