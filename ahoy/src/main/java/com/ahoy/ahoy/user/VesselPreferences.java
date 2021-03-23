package com.ahoy.ahoy.user;

import com.ahoy.ahoy.vessel.Vessel;

import javax.persistence.*;

@Entity
@Table(name = "VesselPreference")
public class VesselPreferences {
    @EmbeddedId private VesselPreferencePK vesselPreferencePK;

    @MapsId("abbrvslm")
    @ManyToOne
    @JoinColumn(name = "abbrvslm")
    private Vessel vessel;

    @MapsId("username")
    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    public VesselPreferences() {
        this.vesselPreferencePK = new VesselPreferencePK();
    }

    public VesselPreferencePK getVesselPreferencePK() {
        return vesselPreferencePK;
    }

    public void setVesselPreferencePK(VesselPreferencePK vesselPreferencePK) {
        this.vesselPreferencePK = vesselPreferencePK;
    }

    public Vessel getVessel() {
        return vessel;
    }

    public void setVessel(Vessel vessel) {
        this.vessel = vessel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
