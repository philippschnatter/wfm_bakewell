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

public class ProcessList {
	  @Inject
	  private RepositoryService repositoryService;

	  @Produces
	  @Named("processDefinitionList")
	  public List getProcessDefinitionList() {
	    return repositoryService.createProcessDefinitionQuery()
	            .list();
	  }
}
