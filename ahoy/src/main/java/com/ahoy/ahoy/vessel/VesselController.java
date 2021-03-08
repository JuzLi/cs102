package com.ahoy.ahoy.vessel;


import com.ahoy.ahoy.portnet.PortnetConnector;
import com.ahoy.ahoy.repo.VesselRepository;
import com.google.gson.JsonArray;
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

    @Autowired
    PortnetConnector portnetConnector;

    @GetMapping("/queryDB")
    public List<Vessel> vesselsWithName(){
        List<Vessel> l1 = vesselRepository.findByFullName("RMS Titanic");
        return vesselService.doubleVessels(l1);
    }

    @GetMapping("/getRequest")
    public List<Vessel> allVesselsName(){
        List<Vessel> l1 = portnetConnector.getUpdate("2021-03-02","2021-03-02");
        return vesselService.doubleVessels(l1);
    }



}
