package com.ahoy.ahoy.vessel;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "vessel")
public class Vessel {

    @Id
    private String shortName;


    private String longName;


    private String fullName;

    public Vessel(){};
    public Vessel(String shortName, String longName, String fullName) {
        this.shortName = shortName;
        this.longName = longName;
        this.fullName = fullName;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "Vessel{" +
                "shortName='" + shortName + '\'' +
                ", longName='" + longName + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
