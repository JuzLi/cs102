package com.ahoy.ahoy.alert;

import com.ahoy.ahoy.voyage.VoyageDetailsPK;
import com.ahoy.ahoy.voyage.VoyagePK;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AlertPK implements Serializable {
    private VoyagePK voyagePK;
    private int alertCount;
    public String alerttype;



    public VoyagePK getVoyagePK() {
        return voyagePK;
    }

    public void setVoyagePK(VoyagePK voyagePK) {
        this.voyagePK = voyagePK;
    }

    public int getAlertCount() {
        return alertCount;
    }

    public void setAlertCount(int alertCount) {
        this.alertCount = alertCount;
    }

    public String getAlerttype() {
        return alerttype;
    }

    public void setAlerttype(String alerttype) {
        this.alerttype = alerttype;
    }

    @Override
    public String toString() {
        return "AlertPK{" +
                "voyagePK=" + voyagePK +
                ", alertCount=" + alertCount +
                ", alerttype='" + alerttype + '\'' +
                '}';
    }
}
