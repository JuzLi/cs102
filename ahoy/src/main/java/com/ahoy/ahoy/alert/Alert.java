package com.ahoy.ahoy.alert;

import com.ahoy.ahoy.voyage.VoyageDetails;

import javax.persistence.*;

@Entity
@Table(name = "alert")
public class Alert {
    @EmbeddedId private AlertPK alertPK;

    private String alerttype;
    private String alertcontent;
    private String alertdatetime;

    @MapsId("voyageDetailsPK")
    @JoinColumns({@JoinColumn(name = "abbrvslm", referencedColumnName = "abbrvslm"),
            @JoinColumn(name = "invoyn", referencedColumnName = "invoyn"),
            @JoinColumn(name = "voyagedetailsid", referencedColumnName = "voyagedetailsid")})
    @OneToOne
    private VoyageDetails voyageDetails;

    public Alert() {
        this.alertPK = new AlertPK();
    }

    public AlertPK getAlertPK() {
        return alertPK;
    }

    public void setAlertPK(AlertPK alertPK) {
        this.alertPK = alertPK;
    }

    public String getAlerttype() {
        return alerttype;
    }

    public void setAlerttype(String alerttype) {
        this.alerttype = alerttype;
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

    public VoyageDetails getVoyageDetails() {
        return voyageDetails;
    }

    public void setVoyageDetails(VoyageDetails voyageDetails) {
        this.voyageDetails = voyageDetails;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "alertPK=" + alertPK +
                ", alerttype='" + alerttype + '\'' +
                ", alertcontent='" + alertcontent + '\'' +
                ", alertdatetime='" + alertdatetime + '\'' +
                ", voyageDetails=" + voyageDetails +
                '}';
    }
}
