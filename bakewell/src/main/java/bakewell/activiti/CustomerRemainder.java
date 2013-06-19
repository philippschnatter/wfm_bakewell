package bakewell.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class CustomerRemainder implements JavaDelegate
{
	

	@Override
	public void execute(DelegateExecution execution) throws Exception
	{
		
		int remainder=1;
		String remAmount = (String)execution.getVariable("RemainderAmount");
		if(remAmount!=null)
		{
			remainder= Integer.parseInt(remAmount);
		}			
		
		execution.setVariable("RemainderAmount", remainder);
	}

}
