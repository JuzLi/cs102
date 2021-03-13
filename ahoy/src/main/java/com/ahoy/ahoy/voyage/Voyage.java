package com.ahoy.ahoy.voyage;

import com.ahoy.ahoy.berth.Berth;
import com.ahoy.ahoy.vessel.Vessel;

import javax.persistence.*;

@Entity
@Table(name = "voyage")
public class Voyage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int voyageID;

    @ManyToOne
    @JoinColumn(name = "abbrvslm")
    private Vessel vessel;


    private String invoyn;

    private String fullinvoyn;
    private String outvoyn;
    private String btrdt;
    private String unbthgdt;
    private String status;

    @ManyToOne
    @JoinColumn(name = "berthnum")
    private Berth berth;

    public Voyage() {
    }

    public int getVoyageID() {
        return voyageID;
    }

    public void setVoyageID(int voyageID) {
        this.voyageID = voyageID;
    }

    public Vessel getVessel() {
        return vessel;
    }

    public void setVessel(Vessel vessel) {
        this.vessel = vessel;
    }

    public String getInvoyn() {
        return invoyn;
    }

    public void setInvoyn(String invoyn) {
        this.invoyn = invoyn;
    }

    public String getFullinvoyn() {
        return fullinvoyn;
    }

    public void setFullinvoyn(String fullinvoyn) {
        this.fullinvoyn = fullinvoyn;
    }

    public String getOutvoyn() {
        return outvoyn;
    }

    public void setOutvoyn(String outvoyn) {
        this.outvoyn = outvoyn;
    }

    public String getBtrdt() {
        return btrdt;
    }

    public void setBtrdt(String btrdt) {
        this.btrdt = btrdt;
    }

    public String getUnbthgdt() {
        return unbthgdt;
    }

    public void setUnbthgdt(String unbthgdt) {
        this.unbthgdt = unbthgdt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Berth getBerth() {
        return berth;
    }

    public void setBerth(Berth berth) {
        this.berth = berth;
    }
}
