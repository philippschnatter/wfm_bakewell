package bakewell.activityTasks;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class CustomerRemainder implements JavaDelegate
{
	

	@Override
	public void execute(DelegateExecution execution) throws Exception
	{
		int remainder=1;
		Object remAmount = execution.getVariable("RemainderAmount");
		if(remAmount!=null)
		{
			remainder= (int)remAmount;
		}			
		
		execution.setVariable("RemainderAmount", remainder);
	}

}
