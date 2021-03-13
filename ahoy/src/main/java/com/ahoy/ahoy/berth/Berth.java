package com.ahoy.ahoy.berth;

import com.ahoy.ahoy.voyage.Voyage;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "berth")
public class Berth {
    @Id
    @Column(name = "berthnum")
    private String berthnum;

    @OneToMany(mappedBy = "berth")
    private Set<Voyage> voyage;

    public Berth() {
    }

    public Berth(String berthnum) {
        this.berthnum = berthnum;
    }

    public String getBerthnum() {
        return berthnum;
    }

    public void setBerthnum(String berthnum) {
        this.berthnum = berthnum;
    }

    @Override
    public String toString() {
        return "Berth{" +
                "berthnum='" + berthnum + '\'' +
                '}';
    }
}
