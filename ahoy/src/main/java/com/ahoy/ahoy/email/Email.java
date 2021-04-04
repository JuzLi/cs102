package com.ahoy.ahoy.email;

//this pojo object for the email object to be sent
//email object contains an ArrayList of Alerts to be sent

import com.ahoy.ahoy.alert.Alert;

import java.util.ArrayList;
import java.util.List;

public class Email {
    private String name; //name of sendee
    private String emailAddress;
    private List<Alert> alertList;
    private String subject;


    public Email(){
        alertList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void addAlert(Alert alert){
        alertList.add(alert);
    }

    public void setAlertList(List<Alert> alertList){
        this.alertList = alertList;
    }

    //generate Email message from alerts: as soon as all messages are out
    public String populateText(){
        String text = "Ahoy " + name + "! \n" +
                "Shiver me timbers matey, you have the following alerts for ye subscribed voyages! \n\n";

        int count = 1;
        for (Alert a : alertList) {
            text += "Alert " + count++ + " : ";
            text += a.toText() + "\n \n \n";
        }
        text += "That's it! Goodbye and godspeed!\n The Ahoy Team";

        return text;
    }







}
