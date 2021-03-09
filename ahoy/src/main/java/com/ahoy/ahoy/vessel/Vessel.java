package com.ahoy.ahoy.vessel;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "vessel")
public class Vessel {

    @Id
    @Column(name = "abbrvsim")
    private String shortName;

    @Column(name = "fullvsim")
    private String longName;

    public Vessel(){};
    public Vessel(String shortName, String longName) {
        this.shortName = shortName;
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    @Override
    public String toString() {
        return "Vessel{" +
                "shortName='" + shortName + '\'' +
                ", longName='" + longName + '\'' +
                '}';
    }
}
