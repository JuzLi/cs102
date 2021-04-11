package com.ahoy.ahoy.controllers;

import com.ahoy.ahoy.alert.Alert;
import com.ahoy.ahoy.alert.AlertService;
import com.ahoy.ahoy.user.User;
import com.ahoy.ahoy.user.UserRepository;
import com.ahoy.ahoy.user.UserService;
import com.ahoy.ahoy.vessel.Vessel;
import com.ahoy.ahoy.vessel.VesselRepository;
import com.ahoy.ahoy.voyage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class AjaxController {
    @Autowired
    VesselRepository vesselRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VoyageService voyageService;
    @Autowired
    VoyageRepository voyageRepository;
    @Autowired
    VoyageDetailsRepository voyageDetailsRepository;
    @Autowired
    AlertService alertService;


    @ResponseBody
    @RequestMapping(path = "/ajax/getCurrentUser", method = RequestMethod.GET)
    public User getCurrentUser(){
        return userService.getCurrentUser();
    }

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
        return userService.subscribedAlertTypes();
    }

    @ResponseBody
    @RequestMapping(path = "/ajax/searchUnsubscribedAlerts", method = RequestMethod.GET)
    public List<String> searchUnsubscribedAlerts(){
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
    public List<Alert> retrieveAlerts(){
        List<Voyage> subscribedVoyages = userService.subscribedVoyages();
        List<Alert> alertList = new ArrayList<>();
        for(Voyage voyage: subscribedVoyages){
            alertList.addAll(alertService.retrieveLatestAlertsOfVoyage(voyage));
        }
        return alertService.filterSubscribedAlerts(alertService.filterAlertsByDate(alertList,0));
    }


    @ResponseBody
    @RequestMapping(path = "/ajax/retrieveTodayAlerts", method = RequestMethod.GET)
    public List<Alert> retrieveTodayAlerts(){
        List<Voyage> allVoyagesFromToday = voyageService.allVoyagesFromToday();
        List<Voyage> todayVoyages = voyageService.filterVoyagesArrivingByDate(allVoyagesFromToday,0);
        List<Alert> alertList = new ArrayList<>();
        for(Voyage voyage: todayVoyages){
            alertList.addAll(alertService.retrieveLatestAlertsOfVoyage(voyage));
        }
        return alertService.filterAlertsByDate(alertList,0);
    }

    @ResponseBody
    @RequestMapping(path = "/ajax/retrieveTodayAlertsFiltered", method = RequestMethod.POST)
    public List<Alert> retrieveTodayAlertsFiltered(@RequestBody Map<String,String> map){
        String filter = map.get("filter");
        List<Voyage> allVoyagesFromToday = voyageService.allVoyagesFromToday();
        List<Voyage> todayVoyages = voyageService.filterVoyagesArrivingByDate(allVoyagesFromToday,0);
        List<Alert> alertList = new ArrayList<>();
        for(Voyage voyage: todayVoyages){
            alertList.add(alertService.retrieveLatestAlertOfVoyageOfType(voyage,filter));
        }
        return alertService.filterAlertsByDate(alertList,0);
    }

    @ResponseBody
    @RequestMapping(path = "/ajax/retrieveSubscribedAlertsFiltered", method = RequestMethod.POST)
    public List<Alert> retrieveSubscribedAlertsFiltered(@RequestBody Map<String,String> map){
        String filter = map.get("filter");
        List<Voyage> subscribedVoyages = userService.subscribedVoyages();
        List<Voyage> todayVoyages = voyageService.filterVoyagesArrivingByDate(subscribedVoyages,0);
        List<Alert> alertList = new ArrayList<>();
        boolean isSubscribed = false;

        for(Voyage voyage: todayVoyages){
            alertList.add(alertService.retrieveLatestAlertOfVoyageOfType(voyage,filter));
        }
        return alertService.filterSubscribedAlerts(alertService.filterAlertsByDate(alertList,0));
    }

    @ResponseBody
    @RequestMapping(path = "/ajax/retrieveTodayVoyage", method = RequestMethod.GET)
    public List<Voyage> retrieveTodayVoyage(){
        String today = java.time.LocalDate.now().toString();
        String startDT = today+" 00:00:00";
        String endDT = today+" 23:59:59";
        return voyageRepository.retrieveVoyagesBetweenDates(startDT,endDT);
    }

    @ResponseBody
    @RequestMapping(path = "/ajax/retrieveDetailedVoyageRecordByDay", method = RequestMethod.POST)
    public ArrayList<VoyageDetails> retrieveDetailedVoyageRecordByDay(@RequestBody Map<String,String> map){
        int days = Integer.parseInt(map.get("numDays"));
        List<Voyage> todayVoyageList = voyageService.filterVoyagesArrivingByDate(voyageService.allVoyagesFromToday(),days);

        ArrayList<VoyageDetails> todayVoyageDetailsList = new ArrayList<VoyageDetails>();
        for(Voyage v: todayVoyageList){
            VoyageDetails temp = voyageService.findLatestDetails(v);
            todayVoyageDetailsList.add(temp);
        }


        return todayVoyageDetailsList;
    }

    @ResponseBody
    @RequestMapping(path = "/ajax/retrieveSubscribedDetailedVoyageRecordByDay", method = RequestMethod.POST)
    public ArrayList<VoyageDetails> retrieveSubscribedDetailedVoyageRecordByDay(@RequestBody Map<String,String> map){
        int days = Integer.parseInt(map.get("numDays"));
        List<Voyage> todayVoyageList = voyageService.filterVoyagesArrivingByDate(userService.subscribedVoyages(),days);

        ArrayList<VoyageDetails> todayVoyageDetailsList = new ArrayList<VoyageDetails>();
        for(Voyage v: todayVoyageList){
            VoyageDetails temp = voyageService.findLatestDetails(v);
            todayVoyageDetailsList.add(temp);
        }

        return todayVoyageDetailsList;
    }

    @ResponseBody
    @RequestMapping(path = "/ajax/retrieveVoyageByVessel", method = RequestMethod.POST)
    public List<Voyage> retrieveVoyageByVessel(@RequestBody Map<String,String> map){
        Vessel v = vesselRepository.findByShortName(map.get("abbrvslm"));
        return voyageRepository.findByVessel(v);
    }


    @ResponseBody
    @RequestMapping(path = "/ajax/retrieveVoyageDetailsByVessel", method = RequestMethod.POST)
    public List<VoyageDetails> retrieveVoyageDetailsByVessel(@RequestBody Map<String,String> map){
        Voyage v = voyageRepository.findByPrimarykey(map.get("abbrvslm"),map.get("invoyn"));
        return voyageDetailsRepository.findOneDetail(v);
    }

    @ResponseBody
    @RequestMapping(path = "/ajax/retrieveVoyageAvgSpeedByVesselChart", method = RequestMethod.POST)
    public Map<String, Float> retrieveVoyageAvgSpeedByVesselChart(@RequestBody Map<String, String> map){
        Voyage v = voyageRepository.findByPrimarykey(map.get("abbrvslm"),map.get("invoyn"));
        Map<String, Float> chartData = new LinkedHashMap<>();
        for(VoyageDetails vd : voyageDetailsRepository.findOneDetail(v) ){
            chartData.put(vd.getTimestamp(),vd.getAvg_speed());
        }
        return chartData;
    }

    @ResponseBody
    @RequestMapping(path = "/ajax/retrieveVoyageMaxSpeedByVesselChart", method = RequestMethod.POST)
    public Map<String, Float> retrieveVoyageMaxSpeedByVesselChart(@RequestBody Map<String, String> map){
        Voyage v = voyageRepository.findByPrimarykey(map.get("abbrvslm"),map.get("invoyn"));
        Map<String, Float> chartData = new LinkedHashMap<>();
        for(VoyageDetails vd : voyageDetailsRepository.findOneDetail(v) ){
            chartData.put(vd.getTimestamp(),vd.getMax_speed());
        }
        return chartData;
    }





}
