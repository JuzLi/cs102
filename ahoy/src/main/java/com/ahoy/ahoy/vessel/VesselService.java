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



    public void createVessel(Vessel v){
        if(vesselRepository.findByFullName(v.getFullvslm()) == null){
            vesselRepository.save(v);
        }
    }


}
