package com.ahoy.ahoy.voyage;

import com.ahoy.ahoy.alert.Alert;
import com.ahoy.ahoy.berth.Berth;
import com.ahoy.ahoy.vessel.Vessel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "voyage")

public class Voyage implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int voyageID;
    @EmbeddedId
    private VoyagePK voyagePK;

    @MapsId("abbrvslm")
    @ManyToOne
    @JoinColumn(name = "abbrvslm")
    private Vessel vessel;


    private String fullinvoyn;
    private String outvoyn;
    private String btrdt;
    private String unbthgdt;
    private String status;

    @ManyToOne
    @JoinColumn(name = "berthnum")
    private Berth berth;

    @OneToMany(mappedBy = "voyage")
    private Set<VoyageDetails> voyageDetailsSet;

    @OneToMany(mappedBy = "voyage")
    private Set<Alert> alertSet;

    public Voyage() {
        this.voyagePK = new VoyagePK();
    }


    public Vessel getVessel() {
        return vessel;
    }

    public void setVessel(Vessel vessel) {
        this.vessel = vessel;
    }

    public String getInvoyn() {
        return this.voyagePK.getInvoyn();
    }

    public void setInvoyn(String invoyn) {
        this.voyagePK.setInvoyn(invoyn);
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

    public VoyagePK getVoyagePK() {
        return voyagePK;
    }

    public void setVoyagePK(VoyagePK voyagePK) {
        this.voyagePK = voyagePK;
    }

    @Override
    public String toString() {
        return "Voyage{" +
                "voyagePK=" + voyagePK +
                ", vessel=" + vessel +
                ", fullinvoyn='" + fullinvoyn + '\'' +
                ", outvoyn='" + outvoyn + '\'' +
                ", btrdt='" + btrdt + '\'' +
                ", unbthgdt='" + unbthgdt + '\'' +
                ", status='" + status + '\'' +
                ", berth=" + berth +
                ", voyageDetailsSet=" + voyageDetailsSet +
                ", alertSet=" + alertSet +
                '}';
    }
}
