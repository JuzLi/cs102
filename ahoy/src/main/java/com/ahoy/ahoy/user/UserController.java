package com.ahoy.ahoy.user;

import com.ahoy.ahoy.vessel.Vessel;
import com.ahoy.ahoy.vessel.VesselRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private VesselRepository vesselRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @ResponseBody
    @RequestMapping(path = "/ajax/createVesselPreference", method = RequestMethod.POST)
    public String createVesselPreference(@RequestBody Map<String,String> map){
        Vessel v = vesselRepository.findByShortName(map.get("abbrvslm"));
        userService.createVesselPreference(v);
        return "Success";
    }


    @ResponseBody
    @RequestMapping(path = "/ajax/searchVessels", method = RequestMethod.POST)
    public List<Vessel> searchVessels(@RequestBody Map<String,String> map){
        List<Vessel> vesselList = vesselRepository.findVesselsLike(map.get("abbrvslm"));
        return userService.removeSubscribedVesselsFromList(vesselList);
    }

    @ResponseBody
    @RequestMapping(path = "/ajax/createAlertPreference", method = RequestMethod.POST)
    public String createAlertPreference(@RequestBody Map<String,String> map){
        userService.createAlertPreference(map.get("alertType"));
        return "Success";
    }

    @ResponseBody
    @RequestMapping(path = "/ajax/searchAlerts", method = RequestMethod.GET)
    public List<String> searchAlerts(){
        System.out.println(userService.unsubscribedAlertTypes());
        return userService.unsubscribedAlertTypes();
    }

    @ResponseBody
    @RequestMapping(path = "/ajax/changeEmail", method = RequestMethod.POST)
    public String changeEmail(@RequestBody Map<String,String> map){
        User u = userService.getCurrentUser();
        userRepository.updateEmail(u.getUsername(),map.get("email"));
        return "Success";

    }

    @ResponseBody
    @RequestMapping(path = "/ajax/changePassword", method = RequestMethod.POST)
    public String changePassword(@RequestBody Map<String,String> map){
        User u = userService.getCurrentUser();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encoded_pass = passwordEncoder.encode(map.get("password"));
        userRepository.updatePassword(u.getUsername(), encoded_pass);
        return "Success";
    }
}
