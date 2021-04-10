package com.ahoy.ahoy.voyage;

import com.ahoy.ahoy.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.*;

@RestController
@CrossOrigin
public class VoyageController {
    @Autowired
    VoyageService voyageService;
    @Autowired
    UserService userService;
    @Autowired
    VoyageRepository voyageRepository;

    @Autowired
    VoyageDetailsRepository voyageDetailsRepository;

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





}
