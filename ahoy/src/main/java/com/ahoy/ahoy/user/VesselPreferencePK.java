package com.ahoy.ahoy.user;

import javax.persistence.Embeddable;
import java.io.Serializable;
@Embeddable
public class VesselPreferencePK implements Serializable {
    private String abbrvslm;
    private String username;

    public String getAbbrvslm() {
        return abbrvslm;
    }

    public void setAbbrvslm(String abbrvslm) {
        this.abbrvslm = abbrvslm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
