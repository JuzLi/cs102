package com.ahoy.ahoy.vessel;


import com.ahoy.ahoy.portnet.PortnetConnector;
import com.ahoy.ahoy.portnet.DatabaseUpdate;
import com.ahoy.ahoy.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VesselController {
    @Autowired
    DatabaseUpdate databaseUpdate;

    @Autowired
    VesselRepository vesselRepository;

    @Autowired
    private VesselService vesselService;

    @Autowired
    PortnetConnector portnetConnector;

    @Autowired
    UserService userService;



    @GetMapping("/findShip")
    public Vessel findVessel(){
        return vesselRepository.findByFullName("AAL SHANGHAI");
    }



}
