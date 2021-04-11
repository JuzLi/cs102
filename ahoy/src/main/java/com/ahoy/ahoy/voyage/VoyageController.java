package com.ahoy.ahoy.voyage;

import com.ahoy.ahoy.user.UserService;
import com.ahoy.ahoy.vessel.Vessel;
import com.ahoy.ahoy.vessel.VesselRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.*;

@Controller
@RestController
@CrossOrigin
public class VoyageController {
    @Autowired
    VoyageService voyageService;

    @Autowired
    VesselRepository vesselRepository;

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
        System.out.println(v);
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
