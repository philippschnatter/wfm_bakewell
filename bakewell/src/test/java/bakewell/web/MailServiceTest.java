package bakewell.web;

import bakewell.webservice.MailService;



public class MailServiceTest
{
	public static void main(String[] args)
	{
		MailService mailService = new MailService();

		
		try
		{
			System.out.print("Sending message ");
			mailService.sendMessage();
			System.out.println("... done");
		}
		catch (Exception e)
		{
			System.out.println("... failed");
			e.printStackTrace();
		}
	}
}
