package com.ahoy.ahoy.vessel;


import com.ahoy.ahoy.repo.VesselRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VesselController {

    @Autowired
    VesselRepository vesselRepository;

    @Autowired
    private VesselService vesselService;

    @GetMapping("/queryDB")
    public List<Vessel> vesselsWithName(){
        List<Vessel> l1 = vesselRepository.findByFullName("RMS Titanic");
        return vesselService.doubleVessels(l1);
    }



}
