package com.ahoy.ahoy.user;

import javax.persistence.*;

@Entity
@Table(name = "alertpreference")
public class AlertPreference {
    @EmbeddedId private AlertPreferencePK alertPreferencePK;

    private String alerttype;
    @MapsId("username")
    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    public AlertPreference() {
        this.alertPreferencePK = new AlertPreferencePK();
    }

    public AlertPreferencePK getAlertPreferencePK() {
        return alertPreferencePK;
    }

    public void setAlertPreferencePK(AlertPreferencePK alertPreferencePK) {
        this.alertPreferencePK = alertPreferencePK;
    }

    public String getAlerttype() {
        return alerttype;
    }

    public void setAlerttype(String alerttype) {
        this.alerttype = alerttype;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
