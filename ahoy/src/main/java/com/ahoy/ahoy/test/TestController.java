package com.ahoy.ahoy.test;


import com.ahoy.ahoy.alert.Alert;
import com.ahoy.ahoy.alert.AlertService;
import com.ahoy.ahoy.berth.Berth;
import com.ahoy.ahoy.berth.BerthRepository;
import com.ahoy.ahoy.alert.AlertRepository;
import com.ahoy.ahoy.user.AlertPreferenceRepository;
import com.ahoy.ahoy.user.UserService;
import com.ahoy.ahoy.user.VesselPreferenceRepository;
import com.ahoy.ahoy.vessel.Vessel;
import com.ahoy.ahoy.vessel.VesselRepository;
import com.ahoy.ahoy.portnet.DatabaseUpdate;
import com.ahoy.ahoy.voyage.Voyage;
import com.ahoy.ahoy.voyage.VoyageDetails;
import com.ahoy.ahoy.voyage.VoyageRepository;
import com.ahoy.ahoy.voyage.VoyageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
public class TestController {

    @Autowired
    AlertPreferenceRepository alertPreferenceRepository;
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
    VesselPreferenceRepository vesselPreferencesRepository;
    @Autowired
    AlertService alertService;
    @Autowired
    AlertRepository alertRepository;

    @GetMapping("/test/1")
    public String allVesselsName(){
        databaseUpdate.retrieveVesselsBerthing();
        databaseUpdate.getVoyageDetails();
        return "Success";
    }

    @GetMapping("/test/2")
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


    @GetMapping("/test/3")
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
    @RequestMapping(path = "/test/4", method = RequestMethod.POST)
    public List<Vessel> test4(@RequestParam("abbrvslm") String name){

        return vesselRepository.findVesselsLike(name);

    }

    @ResponseBody
    @RequestMapping(path = "/test/5", method = RequestMethod.POST)
    public List<Vessel> test5(@RequestBody Map<String,String> map){
        List<Vessel> vesselList = vesselRepository.findVesselsLike(map.get("abbrvslm"));
        return userService.removeSubscribedVesselsFromList(vesselList);
    }

    @ResponseBody
    @RequestMapping(path = "/test/6", method = RequestMethod.POST)
    public String test6(@RequestBody Map<String,String> map){
        Vessel v = vesselRepository.findByShortName(map.get("abbrvslm"));
        userService.createVesselPreference(v);
        return "Success";
    }


    @RequestMapping(path = "/test/7")
    public String test7(){
        userService.createAlertPreference("Change in Berth");
        return "Success";
    }

    @RequestMapping(path = "/test/8")
    public List<String> test8(){
        System.out.println(userService.unsubscribedAlertTypes());
        System.out.println(userService.subscribedAlertTypes());
        userService.removeAlertPreference("Change in Berth");
        return userService.unsubscribedAlertTypes();
    }



}
