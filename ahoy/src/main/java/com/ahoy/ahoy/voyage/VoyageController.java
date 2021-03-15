package com.ahoy.ahoy.voyage;


import com.ahoy.ahoy.alert.Alert;
import com.ahoy.ahoy.portnet.PortnetConnector;
import com.ahoy.ahoy.repo.AlertRepository;
import com.ahoy.ahoy.repo.VesselRepository;
import com.ahoy.ahoy.repo.VoyageDetailsRepository;
import com.ahoy.ahoy.repo.VoyageRepository;
import com.ahoy.ahoy.scheduled.DatabaseUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class VoyageController {

    @Autowired
    VesselRepository vesselRepository;
    @Autowired
    VoyageRepository voyageRepository;
    @Autowired
    VoyageDetailsRepository voyageDetailsRepository;
    @Autowired
    DatabaseUpdate databaseUpdate;
    @Autowired
    PortnetConnector portnetConnector;
    @Autowired
    VoyageService voyageService;
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
        VoyageDetails voyageDetails = voyageDetailsRepository.latestDetails(voyage, voyageDetailsRepository.countAlertsOfVoyage(voyage));
        Alert alert = new Alert();
        alert.setAlertcontent("The ship has been attacked by pirates");
        alert.setAlertdatetime("2021-03-15");
        alert.setAlerttype("Compromised");
        alert.setVoyageDetails(voyageDetails);
        alertRepository.save(alert);
        return alert.toString();
    }


}
