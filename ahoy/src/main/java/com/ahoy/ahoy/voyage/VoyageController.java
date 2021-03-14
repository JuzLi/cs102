package com.ahoy.ahoy.voyage;


import com.ahoy.ahoy.portnet.PortnetConnector;
import com.ahoy.ahoy.repo.VesselRepository;
import com.ahoy.ahoy.repo.VoyageDetailsRepository;
import com.ahoy.ahoy.repo.VoyageRepository;
import com.ahoy.ahoy.scheduled.DatabaseUpdate;
import com.ahoy.ahoy.vessel.Vessel;
import com.google.gson.JsonObject;
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

    @GetMapping("/loadDB")
    public String allVesselsName(){
        databaseUpdate.retrieveVesselsBerthing();

        JsonObject j = portnetConnector.getVesselDetails("APLCALIFORNIA", "E8PW1M");
        return j.get("NEXT_PORT_NAME").getAsString();
    }

    @GetMapping("/voyageDetails")
    public String createVoyageDetails(){
        Vessel v = vesselRepository.findByShortName("AAL SHANGHAI");
        Voyage voyage = voyageRepository.findByPrimarykey(v.getAbbrvslm(),"20008");
        voyageService.createVoyageDetails(voyage);

        return "Success";

    }

}
