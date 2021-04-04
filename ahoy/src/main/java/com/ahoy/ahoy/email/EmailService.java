package com.ahoy.ahoy.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;




    public EmailService(){

    }
    public void createRecoveryEmail(String username, String email,String tempPass){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setFrom("ahoy.alert@gmail.com");
        mail.setSubject("Recover Account");
        mail.setText("Hello " + username + "\n Your temporary password is " + tempPass);
        javaMailSender.send(mail);

    }
    public void sendEmail(Email email) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email.getEmailAddress());
        mail.setFrom("ahoy.alert@gmail.com");
        mail.setSubject("Your Voyage Alerts!");
        mail.setText(email.populateText());
        javaMailSender.send(mail);
    }

}
