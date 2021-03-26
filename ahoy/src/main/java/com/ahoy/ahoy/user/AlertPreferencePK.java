package com.ahoy.ahoy.user;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AlertPreferencePK implements Serializable {
    public String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
