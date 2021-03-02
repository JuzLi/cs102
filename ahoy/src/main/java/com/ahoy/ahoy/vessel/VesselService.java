package com.ahoy.ahoy.vessel;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VesselService {
    public List<Vessel> doubleVessels(List<Vessel> l1){
        List<Vessel> returnList = new ArrayList<Vessel>();
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < l1.size(); j++){
                returnList.add(l1.get(j));
            }
        }
        return returnList;
    }
}
