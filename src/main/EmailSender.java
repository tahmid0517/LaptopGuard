package main;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender 
{
	static final String username = "laptopguard517@gmail.com";
	static final String password = "laptop0517";
	
	private static String addressToSendTo;
	
	public static void loadEmailAddress()
	{
		if(Settings.getInstance().shouldSendEmail())
			addressToSendTo = Settings.getInstance().getEmail();
	}
	
	public static void send(String timestamp) 
	{
		loadEmailAddress();
		if(addressToSendTo == null)
			return;
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() 
			{
				return new PasswordAuthentication(username, password);
			}
		  });

		try 
		{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(addressToSendTo));
			message.setSubject("LAPTOP GUARD");
			message.setText("Dear User, \n\n Someone may have unplugged your laptop on " 
					+ timestamp + "!");
			Transport.send(message);
			System.out.println("Message sent");
		} 
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
	}
}
