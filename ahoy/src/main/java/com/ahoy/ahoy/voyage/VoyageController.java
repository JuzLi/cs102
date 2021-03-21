package com.ahoy.ahoy.voyage;


import com.ahoy.ahoy.alert.Alert;
import com.ahoy.ahoy.alert.AlertService;
import com.ahoy.ahoy.berth.Berth;
import com.ahoy.ahoy.berth.BerthRepository;
import com.ahoy.ahoy.portnet.PortnetConnector;
import com.ahoy.ahoy.alert.AlertRepository;
import com.ahoy.ahoy.vessel.Vessel;
import com.ahoy.ahoy.vessel.VesselRepository;
import com.ahoy.ahoy.portnet.DatabaseUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RestController
public class VoyageController {

    @Autowired
    VoyageService voyageService;
    @Autowired
    VoyageRepository voyageRepository;
    @Autowired
    VesselRepository vesselRepository;
    @Autowired
    DatabaseUpdate databaseUpdate;
    @Autowired
    BerthRepository berthRepository;


    @Autowired
    AlertService alertService;
    @Autowired
    AlertRepository alertRepository;

    @GetMapping("/test")
    public String allVesselsName(){
        databaseUpdate.retrieveVesselsBerthing();
        databaseUpdate.getVoyageDetails();
        return "Success";
    }

    @GetMapping("/test2")
    public String alert(){
        Voyage voyage = voyageRepository.findByPrimarykey("ALS JUPITER", "109S");
        VoyageDetails voyageDetails = voyageService.findLatestDetails(voyage);
        Alert alert = new Alert();
        alert.setAlertcontent("The ship has been attacked by pirates");
        alert.setAlertdatetime("2021-03-15");
        alert.setAlerttype("Speed");
        alert.setVoyage(voyage);
        alert.setAlertCount(alertRepository.countAlertsOfVoyageAndType(voyage,"Speed"));
        alertRepository.save(alert);
        return alert.toString();
    }


    @GetMapping("/test3")
    public String generateAutoAlert(){
        Vessel v = vesselRepository.findByShortName("ABHIMATA 1");
        Berth b = berthRepository.findByBerthNum("K09");
        try {
            voyageService.createVoyage(v, "B120A", "B120A", "B120A", "2021-03-23 11:00:00", "2021-03-23 14:00:00", b, "ALONGSIDE");
        } catch (Exception e){
            e.printStackTrace();
        }
        return "success";
    }




}
