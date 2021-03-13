package com.ahoy.ahoy.berth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "berth")
public class Berth {
    @Id
    @Column(name = "berthnum")
    private String berthnum;

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
