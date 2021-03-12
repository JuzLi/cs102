package com.ahoy.ahoy.vessel;

import com.ahoy.ahoy.portnet.PortnetConnector;
import com.ahoy.ahoy.repo.VesselRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VesselService {

    @Autowired
    private VesselRepository vesselRepository;

//    public List<Vessel> doubleVessels(List<Vessel> l1){
//        List<Vessel> returnList = new ArrayList<Vessel>();
//        for(int i = 0; i < 2; i++){
//            for(int j = 0; j < l1.size(); j++){
//                returnList.add(l1.get(j));
//            }
//        }
//        return returnList;
//    }

    public void createVessel(String abbrvsim, String fullvsim){
        Vessel v  = vesselRepository.findByFullName(fullvsim);
        if(v==null){
            vesselRepository.save(new Vessel(abbrvsim,fullvsim));
        }
    }


    @Scheduled(cron = "${vessel.timing}")
    public void hello(){
        System.out.println("Hello");
    }
    //main function, calls crud
}
