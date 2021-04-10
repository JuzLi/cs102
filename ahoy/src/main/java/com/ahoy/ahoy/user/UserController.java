package com.ahoy.ahoy.user;

import com.ahoy.ahoy.alert.Alert;
import com.ahoy.ahoy.alert.AlertService;
import com.ahoy.ahoy.vessel.Vessel;
import com.ahoy.ahoy.vessel.VesselRepository;
import com.ahoy.ahoy.voyage.Voyage;
import com.ahoy.ahoy.voyage.VoyageRepository;
import com.ahoy.ahoy.voyage.VoyageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private VesselRepository vesselRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    VoyageService voyageService;
    @Autowired
    VoyageRepository voyageRepository;
    @Autowired
    AlertService alertService;



    @ResponseBody
    @RequestMapping(path = "/ajax/createVesselPreference", method = RequestMethod.POST)
    public String createVesselPreference(@RequestBody Map<String,String> map){
        Vessel v = vesselRepository.findByShortName(map.get("abbrvslm"));
        userService.createVesselPreference(v);
        return "Success";
    }
    @ResponseBody
    @RequestMapping(path = "/ajax/removeVesselPreference", method = RequestMethod.POST)
    public String removeVesselPreference(@RequestBody Map<String,String> map){
        Vessel v = vesselRepository.findByShortName(map.get("abbrvslm") );
        userService.removeSubscribedVessel(v);
        return "Success";
    }


    @ResponseBody
    @RequestMapping(path = "/ajax/searchVessels", method = RequestMethod.POST)
    public List<Vessel> searchVessels(@RequestBody Map<String,String> map){
        List<Vessel> vesselList = vesselRepository.findVesselsLike(map.get("abbrvslm"));
        return userService.removeSubscribedVesselsFromList(vesselList);
    }

    @ResponseBody
    @RequestMapping(path = "/ajax/createAlertPreference", method = RequestMethod.POST)
    public String createAlertPreference(@RequestBody Map<String,String> map){
        userService.createAlertPreference(map.get("alertType"));
        return "Success";
    }

    @ResponseBody
    @RequestMapping(path = "/ajax/deleteAlertPreference", method = RequestMethod.POST)
    public String deleteAlertPreference(@RequestBody Map<String,String> map){
        userService.removeAlertPreference(map.get("alertType"));
        return "Success";
    }

    @ResponseBody
    @RequestMapping(path = "/ajax/searchSubscribedAlerts", method = RequestMethod.GET)
    public List<String> searchSubscribedAlerts(){
        System.out.println(userService.subscribedAlertTypes());
        return userService.subscribedAlertTypes();
    }

    @ResponseBody
    @RequestMapping(path = "/ajax/searchUnsubscribedAlerts", method = RequestMethod.GET)
    public List<String> searchUnsubscribedAlerts(){
        System.out.println(userService.unsubscribedAlertTypes());
        return userService.unsubscribedAlertTypes();
    }

    @ResponseBody
    @RequestMapping(path = "/ajax/changeEmail", method = RequestMethod.POST)
    public String changeEmail(@RequestBody Map<String,String> map){
        String email = map.get("email");
        userService.updateEmail(email);
        return "Success";

    }

    @ResponseBody
    @RequestMapping(path = "/ajax/changePassword", method = RequestMethod.POST)
    public String changePassword(@RequestBody Map<String,String> map){
        String password = map.get("password");
        userService.updatePassword(password);
        return "Success";
    }

    @ResponseBody
    @RequestMapping(path = "/ajax/recoverAccount", method = RequestMethod.POST)
    public String recoverAccount(@RequestBody Map<String,String> map){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String username = map.get("username");
        String email = map.get("email");
        userService.recoverAccount(username,email);
        return "Success";
    }

    @ResponseBody
    @RequestMapping(path = "/ajax/retrieveTodaySubscribedVoyages", method = RequestMethod.GET)
    public List<Voyage> retrieveSubscribedVoyages(){

        return voyageService.filterVoyagesArrivingByDate(userService.subscribedVoyages(),0);
    }

    @ResponseBody
    @RequestMapping(path = "/ajax/retrieveSubscribedAlerts", method = RequestMethod.GET)
    public List<Alert> retrieveSubscribeAlerts(){
        List<Voyage> voyageList = userService.subscribedVoyages();
        ArrayList<Alert> alertList = new ArrayList<>();
        for(Voyage voyage: voyageList){
            alertList.addAll(alertService.retrieveLatestAlertsOfVoyage(voyage));
        }
        List<Alert> filteredList = alertService.filterAlertsByDate(alertList,0);
        return filteredList;

    }

    @ResponseBody
    @RequestMapping(path = "/ajax/retrieveTodayAlerts", method = RequestMethod.GET)
    public List<Alert> retrieveTodayAlerts(){
        List<Voyage> voyageList = voyageService.allVoyagesFromToday();
        List<Voyage> voyagesArrivingToday = voyageService.filterVoyagesArrivingByDate(voyageList,0);
        ArrayList<Alert> alertList = new ArrayList<>();
        for(Voyage voyage: voyageList){
            alertList.addAll(alertService.retrieveLatestAlertsOfVoyage(voyage));
        }
        List<Alert> filteredList = alertService.filterAlertsByDate(alertList,0);
        return filteredList;

    }

    @ResponseBody
    @RequestMapping(path = "/ajax/retrieveTodayAlertsFiltered", method = RequestMethod.POST)
    public List<Alert> retrieveTodayAlertsFiltered(@RequestBody Map<String,String> map){
        String filter = map.get("filter");
        List<Voyage> voyageList = voyageService.allVoyagesFromToday();
        List<Voyage> voyagesArrivingToday = voyageService.filterVoyagesArrivingByDate(voyageList,0);
        ArrayList<Alert> alertList = new ArrayList<>();
        for(Voyage voyage: voyageList){
            alertList.add(alertService.retrieveLatestAlertOfVoyageOfType(voyage,filter));
        }
        List<Alert> filteredList = alertService.filterAlertsByDate(alertList,0);
        return filteredList;

    }






}
