package com.ahoy.ahoy.vessel;


import com.ahoy.ahoy.portnet.PortnetConnector;
import com.ahoy.ahoy.repo.VesselRepository;
import com.ahoy.ahoy.user.User;
import com.ahoy.ahoy.user.UserService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    UserService userService;

    @GetMapping("/getRequest")
    public String allVesselsName(){
        JsonObject j = portnetConnector.getVesselDetails("APLCALIFORNIA", "E8PW1M");
        return j.get("NEXT_PORT_NAME").getAsString();
    }




}
