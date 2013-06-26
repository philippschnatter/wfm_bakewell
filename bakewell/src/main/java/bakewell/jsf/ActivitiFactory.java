package bakewell.jsf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;

import org.activiti.engine.runtime.ProcessInstance;

import bakewell.activiti.ActivitiConstants;

public class ActivitiFactory {
	
	private ProcessEngine processEngine = null;
	private String pid = null;
	private ProcessInstance processInstance = null;
	
	private static ActivitiFactory instance;
//    private static Logger logger = Logger.getLogger(ActivitiEngine.class);
    private Map<String, String> taskErrorMap = new HashMap<String, String>();
	
	public static ActivitiFactory getInstance()
	{
		if (instance == null)
		{
//			logger.info("Starting Activiti Engine");
			instance = new ActivitiFactory();
		}
		return instance;
	}

	
	public Deployment init()
	{
		String description = ActivitiConstants.PROCESS_NAME;
		
		if (processEngine == null)
		{
			// use an H2 in-memory database for Activiti
			ProcessEngineConfiguration config = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
					.setMailServerPort(1025);
			
			/**
			 *  use the Activiti Explorer's H2 database - the process will be shown in the Activiti Explorer 
			 *  
			 *  only use this method if you have installed and started H2 on your own
			 *  @see http://forums.activiti.org/en/viewtopic.php?f=9&t=5762
			 *  @see http://www.h2database.com/html/quickstart.html
			 *  
			 *  *) use a generic H2 Server in the web-interface after invoking h2.bat or h2.sh and set the
			 *     JDBC URL to your needs, f.e. to jdbc:h2:tcp://localhost/activiti
			 *  *) change the jdbc.url inside WEB-INF/classes/db.properties of the activiti-explorer to match
			 *     your H2 jdbc.url f.e. jdbc.url=jdbc:h2:tcp://localhost/activiti
			 **/
//			ProcessEngineConfiguration config = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
//			processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration().buildProcessEngine();
			config.setProcessEngineName("Activiti 5.11 (Standalone)");
			processEngine = config.buildProcessEngine();
			
			CreateUsers();
		}
		
		if (processEngine == null)
		{
//			logger.error("Could not start the Activiti Engine");
			return null;
		}
		else
		{
//			logger.info("Activiti Engine started");
		
//			logger.info("deploying process description!");
			RepositoryService repositoryService = processEngine.getRepositoryService();
			List<Deployment> deployments = repositoryService.createDeploymentQuery().deploymentId(description).list();
			Deployment deployment = null;
			if (deployments == null || deployments.size() == 0)
			{
				DeploymentBuilder builder = repositoryService.createDeployment();
				builder.addClasspathResource("diagrams/"+ActivitiConstants.DIAGRAMM_NAME);
				builder.name(description);
				deployment = builder.deploy();
//				logger.info("process deployed!");
				return deployment;
			}
			else{
				deployment = deployments.get(0);
			}
			
			return deployment;
		}
		
	}
	
	private void CreateUsers(){
		IdentityService identityService = processEngine.getIdentityService();
		User controller = identityService.newUser(ActivitiConstants.USER_CONTROLLER);
		identityService.saveUser(controller);
		User customer = identityService.newUser(ActivitiConstants.USER_CUSTOMER);
		identityService.saveUser(customer);
		User processManager = identityService.newUser(ActivitiConstants.USER_PROCESSMANAGER);
		identityService.saveUser(processManager);
	}
	
	public ProcessInstance createProcessInstance(){
		
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put(ActivitiConstants.CV_REMAINDER_AMOUNT, "0");
		
		// mail stuff
		variableMap.put(ActivitiConstants.MAIL_INFORM_PRODUCTMANAGER_SUBJECT, "New Product");
		variableMap.put(ActivitiConstants.MAIL_INFORM_PRODUCTMANAGER_TEXT, "Dear Manager, <br/> We have invented a new product");
		variableMap.put(ActivitiConstants.MAIL_SEND_OFFER_SUBJECT, "Offer for Product");
		variableMap.put(ActivitiConstants.MAIL_SEND_OFFER_TEXT, "Dear Customer, <br/> We made a new offer for your product </br> please review it");
	
		variableMap.put(ActivitiConstants.MAIL_SEND_TERMINATION_SUBJECT, "Termination of job");
		variableMap.put(ActivitiConstants.MAIL_SEND_TERMINATION_TEXT, "Dear customer <br/> Our offer has expired");
			
		return processEngine.getRuntimeService().startProcessInstanceByKey(ActivitiConstants.PROCESS_NAME, variableMap);
	}
	
	public ProcessEngine getProcessEngine() { return this.processEngine; }
	public void setProcessEngine(ProcessEngine processEngine) { this.processEngine = processEngine; }

	public String getProcessInstanceId() {
		return this.pid;
	}
	public void destroy()
	{
		ProcessEngines.destroy();
//		logger.info("Activiti Engine stopped");
	}
}
