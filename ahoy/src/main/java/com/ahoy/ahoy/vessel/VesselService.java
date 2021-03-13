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

    @Autowired
    PortnetConnector portnetConnector;


    public void createVessel(String abbrvsim, String fullvsim){
        Vessel v  = vesselRepository.findByFullName(fullvsim);
        if(v==null){
            vesselRepository.save(new Vessel(abbrvsim,fullvsim));
        }
    }


//    @Scheduled(cron = "${post.timing}")
//    public void hello(){
//        portnetConnector.getUpdate("2021-03-02", "2021-03-05");
//        System.out.println("Hello");
//    }

}
