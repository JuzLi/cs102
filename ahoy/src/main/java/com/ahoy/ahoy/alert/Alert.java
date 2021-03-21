package com.ahoy.ahoy.alert;

import com.ahoy.ahoy.voyage.Voyage;
import com.ahoy.ahoy.voyage.VoyageDetails;

import javax.persistence.*;

@Entity
@Table(name = "alert")
public class Alert {
    @EmbeddedId private AlertPK alertPK;

    private String alertcontent;
    private String alertdatetime;

    @MapsId("voyagePK")
    @JoinColumns({
            @JoinColumn(name = "abbrvslm", referencedColumnName = "abbrvslm"),
            @JoinColumn(name = "invoyn", referencedColumnName = "invoyn")
    }
    )
    @ManyToOne
    private Voyage voyage;

    public Alert() {
        this.alertPK = new AlertPK();
    }

    public AlertPK getAlertPK() {
        return alertPK;
    }

    public void setAlertPK(AlertPK alertPK) {
        this.alertPK = alertPK;
    }

    public String getAlertcontent() {
        return alertcontent;
    }

    public void setAlertcontent(String alertcontent) {
        this.alertcontent = alertcontent;
    }

    public String getAlertdatetime() {
        return alertdatetime;
    }

    public void setAlertdatetime(String alertdatetime) {
        this.alertdatetime = alertdatetime;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public void setAlerttype(String type){
        this.alertPK.setAlerttype(type);
    }

    public void setAlertCount(int i){
        this.alertPK.setAlertCount(i);
    }

    @Override
    public String toString() {
        return "Alert{" +
                "alertPK=" + alertPK +
                ", alertcontent='" + alertcontent + '\'' +
                ", alertdatetime='" + alertdatetime + '\'' +
                ", voyage=" + voyage +
                '}';
    }
}
