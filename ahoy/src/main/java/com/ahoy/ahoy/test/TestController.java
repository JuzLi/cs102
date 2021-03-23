package com.ahoy.ahoy.test;


import com.ahoy.ahoy.alert.Alert;
import com.ahoy.ahoy.alert.AlertService;
import com.ahoy.ahoy.berth.Berth;
import com.ahoy.ahoy.berth.BerthRepository;
import com.ahoy.ahoy.portnet.PortnetConnector;
import com.ahoy.ahoy.alert.AlertRepository;
import com.ahoy.ahoy.user.User;
import com.ahoy.ahoy.user.UserService;
import com.ahoy.ahoy.user.VesselPreferences;
import com.ahoy.ahoy.user.VesselPreferencesRepository;
import com.ahoy.ahoy.vessel.Vessel;
import com.ahoy.ahoy.vessel.VesselRepository;
import com.ahoy.ahoy.portnet.DatabaseUpdate;
import com.ahoy.ahoy.voyage.Voyage;
import com.ahoy.ahoy.voyage.VoyageDetails;
import com.ahoy.ahoy.voyage.VoyageRepository;
import com.ahoy.ahoy.voyage.VoyageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
public class TestController {

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
    UserService userService;
    @Autowired
    VesselPreferencesRepository vesselPreferencesRepository;
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

    @ResponseBody
    @RequestMapping(path = "/test4", method = RequestMethod.POST)
    public List<Vessel> test4(@RequestParam("abbrvslm") String name){

        return vesselRepository.findVesselsLike(name);

    }

    @ResponseBody
    @RequestMapping(path = "/test5", method = RequestMethod.POST)
    public List<Vessel> test5(@RequestBody Map<String,String> map){
//        System.out.println(vesselname);
//        System.out.println(map.toString());
        List<Vessel> vesselList= vesselRepository.findVesselsLike(map.get("abbrvslm"));
        Vessel v = vesselList.get(0);
        System.out.println(v);
        return vesselRepository.findVesselsLike(map.get("abbrvslm"));

    }

    @GetMapping("/test6")
    public List<Vessel> test6(){
        return userService.subscribedVessels();
    }




}
