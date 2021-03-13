package com.ahoy.ahoy.voyage;

import com.ahoy.ahoy.repo.VoyageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoyageService {

    @Autowired
    VoyageRepository voyageRepository;

    public void createVoyage(Voyage v){
        if(voyageRepository.findByVoyageID(v.getVoyageID()) == null){
            voyageRepository.save(v);
        }
    }
}
