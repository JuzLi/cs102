package com.ahoy.ahoy.voyage;

import com.ahoy.ahoy.alert.Alert;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="voyagedetails")
public class VoyageDetails implements Serializable {

    @EmbeddedId
    public VoyageDetailsPK voyageDetailsPK;

    private float avg_speed;
    private int distance_to_go;
    private float max_speed;
    private int is_patching_activated;
    private String patching_predicted_btr;
    private String predicted_btr;
    private String vslvoy;
    private String timestamp;


    @MapsId("voyagePK")
    @JoinColumns({
            @JoinColumn(name = "abbrvslm", referencedColumnName = "abbrvslm"),
            @JoinColumn(name = "invoyn", referencedColumnName = "invoyn")
    }
    )
    @ManyToOne
    private Voyage voyage;

    public VoyageDetails() {
        this.voyageDetailsPK = new VoyageDetailsPK();
    }

    public VoyageDetailsPK getVoyageDetailsPK() {
        return voyageDetailsPK;
    }

    public void setVoyageDetailsPK(VoyageDetailsPK voyageDetailsPK) {
        this.voyageDetailsPK = voyageDetailsPK;
    }

    public float getAvg_speed() {
        return avg_speed;
    }

    public void setAvg_speed(float avg_speed) {
        this.avg_speed = avg_speed;
    }

    public int getDistance_to_go() {
        return distance_to_go;
    }

    public void setDistance_to_go(int distance_to_go) {
        this.distance_to_go = distance_to_go;
    }

    public float getMax_speed() {
        return max_speed;
    }

    public void setMax_speed(float max_speed) {
        this.max_speed = max_speed;
    }

    public int getIs_patching_activated() {
        return is_patching_activated;
    }

    public void setIs_patching_activated(int is_patching_activated) {
        this.is_patching_activated = is_patching_activated;
    }

    public String getPatching_predicted_btr() {
        return patching_predicted_btr;
    }

    public void setPatching_predicted_btr(String patching_predicted_btr) {
        this.patching_predicted_btr = patching_predicted_btr;
    }

    public String getPredicted_btr() {
        return predicted_btr;
    }

    public void setPredicted_btr(String predicted_btr) {
        this.predicted_btr = predicted_btr;
    }

    public String getVslvoy() {
        return vslvoy;
    }

    public void setVslvoy(String vslvoy) {
        this.vslvoy = vslvoy;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }
}
