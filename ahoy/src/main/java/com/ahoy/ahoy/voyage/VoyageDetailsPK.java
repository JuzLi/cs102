package com.ahoy.ahoy.voyage;

import javax.persistence.Embeddable;
import java.io.Serializable;
@Embeddable
public class VoyageDetailsPK implements Serializable {
    private VoyagePK voyagePK;
    private int voyageDetailsID;

    public VoyagePK getVoyagePK() {
        return voyagePK;
    }

    public void setVoyagePK(VoyagePK voyagePK) {
        this.voyagePK = voyagePK;
    }

    public int getVoyageDetailsID() {
        return voyageDetailsID;
    }

    public void setVoyageDetailsID(int voyageDetailsID) {
        this.voyageDetailsID = voyageDetailsID;
    }
}
