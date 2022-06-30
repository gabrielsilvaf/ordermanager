package net.gabrielsilvaf.ordermanager.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import net.gabrielsilvaf.ordermanager.model.Order;
import net.gabrielsilvaf.ordermanager.model.User;


@Service
public class EmailService {
	
    private static final Logger logger = LogManager.getLogger(EmailService.class);

	
	@Autowired 
	private JavaMailSender mailSender;

    private void sendMail(String to, String subject, String textMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(textMessage);
        message.setTo(to);
        message.setSubject(subject);

        try {
            mailSender.send(message);
            
        } catch (Exception e) {
            throw new RuntimeException("Error trying to send email");
        }
    }
    
    public void sendEmailOrderConfirmation(Order order) {
    	User user = order.getUser();
    	
    	sendMail(user.getEmail(), 
    			"Order completed",
    			"Dear " + user.getName() + ". You order (" + order.getId() + ") is completed.");
    	
		logger.info("Email sent about the order " + order.getId());

    }
	
	 
}
