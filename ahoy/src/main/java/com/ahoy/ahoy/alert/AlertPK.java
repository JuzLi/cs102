package com.ahoy.ahoy.alert;

import com.ahoy.ahoy.voyage.VoyageDetailsPK;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AlertPK implements Serializable {
    private VoyageDetailsPK voyageDetailsPK;

    public String alerttype;

    public VoyageDetailsPK getVoyageDetailsPK() {
        return voyageDetailsPK;
    }

    public void setVoyageDetailsPK(VoyageDetailsPK voyageDetailsPK) {
        this.voyageDetailsPK = voyageDetailsPK;
    }

    public String getAlerttype() {
        return alerttype;
    }

    public void setAlerttype(String alerttype) {
        this.alerttype = alerttype;
    }
}
