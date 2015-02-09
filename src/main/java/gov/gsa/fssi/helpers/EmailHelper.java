package main.java.gov.gsa.fssi.helpers;

import java.util.Properties;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailHelper {
	static Logger logger = LoggerFactory.getLogger(EmailHelper.class);	
	
	public static void main(String [] args)
	   {    
	      // Recipient's email ID needs to be mentioned.
	      String to = "davidlarrimore@gmail.com";

	      // Sender's email ID needs to be mentioned
	      String from = "davidlarrimore@gmail.com";

	      // Assuming you are sending email from localhost
	      String host = "aspmx.l.google.com";

	      // Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	      properties.setProperty("mail.smtp.host", host);

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties);

	      try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject("This is the Subject Line!");

	         // Now set the actual message
	         message.setText("This is actual message");

	         // Send message
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	   }
}
