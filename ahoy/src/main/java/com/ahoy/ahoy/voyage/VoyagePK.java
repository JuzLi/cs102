package com.ahoy.ahoy.voyage;

import lombok.Data;

import java.io.Serializable;

@Data
public class VoyagePK implements Serializable {
    private String abbrvslm;
    private String invoyn;

    public VoyagePK() {
    }


    public String getAbbrvslm() {
        return abbrvslm;
    }

    public void setAbbrvslm(String abbrvslm) {
        this.abbrvslm = abbrvslm;
    }

    public String getInvoyn() {
        return invoyn;
    }

    public void setInvoyn(String invoyn) {
        this.invoyn = invoyn;
    }
}
