package com.ahoy.ahoy.voyage;


import com.ahoy.ahoy.portnet.PortnetConnector;
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

    @GetMapping("/test")
    public String allVesselsName(){
        databaseUpdate.retrieveVesselsBerthing();
        databaseUpdate.getVoyageDetails();
        return "Success";
    }



}
