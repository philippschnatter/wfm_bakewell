package bakewell.webservice;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailService
{

	private String from = "wfm@localhost";
	private String host = "localhost";
	private String to = "schnaxl@gmail.com";
	private String message = "hello world";


	
	public boolean sendMessage() 
	{

		MimeMessage message = new MimeMessage(getSession());

		try
		{
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Order summary");
			message.setText(this.message);
			Transport.send(message);
		}
		catch (AddressException e)
		{
			e.printStackTrace();
			
		}
		catch (MessagingException e)
		{
			e.printStackTrace();
			
		}
		return true;
	}

	/**
	 * Create and return an e-mail {@link Session} to the SMTP server.
	 */
	private Session getSession()
	{
		// Configure session parameters
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
//		properties.setProperty("mail.smtp.auth", "true");
//		properties.put("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.port", "1025");

		// Retrieve Authenticator for authenticated message sending
		Authenticator authenticator = new Authenticator();
		properties.setProperty("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName());

		return Session.getDefaultInstance(properties, authenticator);
	}

	/**
	 * Custom {@link javax.mail.Authenticator} for e-mail message
	 * {@link Session}.
	 */
	private class Authenticator extends javax.mail.Authenticator
	{
		private PasswordAuthentication authentication;

		public Authenticator()
		{
			// Create PasswordAuthentification with username and password.
			authentication = new PasswordAuthentication("wfm", "wfm");
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication()
		{
			return authentication;
		}
	}
}
