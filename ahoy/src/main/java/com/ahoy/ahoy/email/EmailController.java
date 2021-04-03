package com.ahoy.ahoy.email;

import com.ahoy.ahoy.alert.Alert;
import com.ahoy.ahoy.alert.AlertRepository;
import com.ahoy.ahoy.user.User;
import com.ahoy.ahoy.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.mail.MailException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


//this controller is to test the email sending function
@RestController
public class EmailController {

    @Autowired
    EmailService emailService;

    @Autowired
    UserService userService;

    //sends an email of all the alerts TODAY
    @RequestMapping("/emailtest")
    public String emailTest (){
        User user = userService.getCurrentUser();
        Email email = new Email();

        //get current date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String datetimeString = now.toString();
        String[] dateArr = datetimeString.split("T");
        String date = dateArr[0];

        email.setName(user.getUsername());
        email.setEmailAddress(user.getEmail());

        List<Alert> alertList = userService.retrieveAlerts(user, date);
        email.setAlertList(alertList);

        try{
            //create message to send
            emailService.sendEmail(email);
            System.out.println("alert email sent to " + user.getEmail());
            return "alert email sent to " + user.getEmail();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "error, email not sent";
        }
    }

    @RequestMapping("/emailStringTest")
    public String returnString(){
        List<Alert> alertList = userService.retrieveAlerts(userService.getCurrentUser(), "2021-04-03");
        String result = "";
        for (Alert a:alertList){
            result += a.toText() + "\r\n";
        }
        return result;
    }

    @RequestMapping("/emailObjectTest")
    public List<Alert> returnObject(){
        return userService.retrieveAlerts(userService.getCurrentUser(), "2021-04-03");
    }



}
