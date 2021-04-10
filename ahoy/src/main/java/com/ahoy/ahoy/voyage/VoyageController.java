package com.ahoy.ahoy.voyage;

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
    @RequestMapping(path = "/ajax/retrieveDetailedVoyageRecordByDay", method = RequestMethod.GET)
    public ArrayList<VoyageDetails> retrieveDetailedVoyageRecordByDay(){
        String today = java.time.LocalDate.now().toString();
        String startDT = today+" 00:00:00";
        String endDT = today+" 23:59:59";
        ArrayList<Voyage> todayVoyageList = (ArrayList<Voyage>) voyageRepository.retrieveVoyagesBetweenDates(startDT,endDT);

        ArrayList<VoyageDetails> todayVoyageDetailsList = new ArrayList<VoyageDetails>();
        for(Voyage v: todayVoyageList){
            VoyageDetails temp = voyageService.findingOneDetail(v);
            System.out.println(temp);
            todayVoyageDetailsList.add(temp);
        }
        return todayVoyageDetailsList;
//        ArrayList<VoyageDetails> todayVoyageDetailsList = new ArrayList<VoyageDetails>();
//        Voyage v = voyageRepository.findByPrimarykey("MAJD","KE110R");
//        ArrayList<Voyage> todayVoyageList = new ArrayList<>();
//        todayVoyageList.add(v);
//        for(Voyage voy: todayVoyageList){
//            VoyageDetails temp = voyageService.findingOneDetail(voy);
//            todayVoyageDetailsList.add(temp);
//        }
//        return todayVoyageDetailsList;


    }




}
