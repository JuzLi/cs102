package com.ahoy.ahoy.email;

//this pojo object for the email object to be sent
//email object contains an ArrayList of Alerts to be sent

import com.ahoy.ahoy.alert.Alert;

import java.util.ArrayList;

public class Email {
    private String name; //name of sendee
    private String emailAddress;
    private ArrayList<Alert> alertList;
    private String subject;


    public Email(){
        alertList = new ArrayList<Alert>();
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

    //alert methods
    public void addAlert(Alert alert){
        alertList.add(alert);
    }


    




}
