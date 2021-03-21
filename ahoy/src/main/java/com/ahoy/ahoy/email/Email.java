package com.ahoy.ahoy.email;

//this pojo object for the email object to be sent

public class Email {
    private String name;
    private String emailAddress;
    private String message;

    public Email(){
    }

    public String getName() {
        return name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getMessage() {
        return message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
