package com.sshyu.zibnote.application.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.sshyu.zibnote.domain.email.model.Email;
import com.sshyu.zibnote.domain.email.port.in.EmailUseCase;

@Service
public class EmailService implements EmailUseCase {
    
    @Autowired
    JavaMailSender emailSender;

    @Override
    public void sendEmail(final Email email) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom(email.getFrom());
        message.setTo(email.getTo()); 
        message.setSubject(email.getSubject()); 
        message.setText(email.getText());
        emailSender.send(message);
    }

}
