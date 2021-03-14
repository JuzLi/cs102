package com.ahoy.ahoy.voyage;

import lombok.Data;

import java.io.Serializable;

@Data
public class VoyageID implements Serializable {
    private String vessel;
    private String invoyn;

    public VoyageID() {
    }

    public VoyageID(String vessel, String invoyn) {
        this.vessel = vessel;
        this.invoyn = invoyn;
    }

    public String getVessel() {
        return vessel;
    }

    public void setVessel(String vessel) {
        this.vessel = vessel;
    }

    public String getInvoyn() {
        return invoyn;
    }

    public void setInvoyn(String invoyn) {
        this.invoyn = invoyn;
    }
}
