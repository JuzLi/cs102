package com.ahoy.ahoy.voyage;


import com.ahoy.ahoy.repo.VesselRepository;
import com.ahoy.ahoy.repo.VoyageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoyageController {

    @Autowired
    VesselRepository vesselRepository;
    @Autowired
    VoyageRepository voyageRepository;

}
