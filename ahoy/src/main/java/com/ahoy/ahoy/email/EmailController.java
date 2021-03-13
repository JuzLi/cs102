package com.ahoy.ahoy.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.mail.MailException;


//this controller is to test the email sending function
@RestController
public class EmailController {

    @Autowired
    EmailService emailService;

    @RequestMapping("/emailtest")
    public String emailTest (){

        try{
            //create message to send
            Email msg = new Email();
            msg.setName("Jonathan");
            msg.setEmailAddress("arvin.aik.2020@sis.smu.edu.sg");
            msg.setMessage("Ahoy " + msg.getName() + ", this is an automated alert from AHOY.");

            emailService.sendEmail(msg);
            System.out.println("email sent");
        }
        catch (MailException e) {
            System.out.println("Error : " + e.getMessage());
        }

        return "Success! Email sent!";
    }
}
