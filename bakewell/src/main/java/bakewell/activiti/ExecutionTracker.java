package bakewell.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class ExecutionTracker implements ExecutionListener{

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		System.out.println("Execution started: "+ execution.getEventName());
		
	}

}
