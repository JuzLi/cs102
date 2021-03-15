package com.ahoy.ahoy.vessel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
