package com.ahoy.ahoy.vessel;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "vessel")
public class Vessel {

    @Id
    @Column(name = "abbrvslm")
    private String abbrvslm;

    @Column(name = "fullvslm")
    private String fullvslm;

    public Vessel(){};

    public Vessel(String abbrvslm, String fullvslm) {
        this.abbrvslm = abbrvslm;
        this.fullvslm = fullvslm;
    }

    public String getAbbrvslm() {
        return abbrvslm;
    }

    public void setAbbrvslm(String abbrvslm) {
        this.abbrvslm = abbrvslm;
    }

    public String getFullvslm() {
        return fullvslm;
    }

    public void setFullvslm(String fullvslm) {
        this.fullvslm = fullvslm;
    }

    @Override
    public String toString() {
        return "Vessel{" +
                "abbrvslm='" + abbrvslm + '\'' +
                ", fullvslm='" + fullvslm + '\'' +
                '}';
    }
}
