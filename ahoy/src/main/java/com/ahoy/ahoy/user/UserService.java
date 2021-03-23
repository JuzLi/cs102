package com.ahoy.ahoy.user;

import com.ahoy.ahoy.vessel.Vessel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    VesselPreferencesRepository vesselPreferencesRepository;
    private User user;


    public User getCurrentUser(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return  userRepository.findByUsername(username);
    }

    public Boolean doesUserExist(String username){
        User u = userRepository.findByUsername(username);
        if(u == null){
            return false;
        }
        return true;

    }

    public void createVesselPreference(Vessel vessel){
        User user = getCurrentUser();
        if(vesselPreferencesRepository.findVesselPreferences(user, vessel) == null){
            VesselPreferences vesselPreferences = new VesselPreferences();
            vesselPreferences.setVessel(vessel);
            vesselPreferences.setUser(user);
            vesselPreferencesRepository.save(vesselPreferences);
        }
    }

    public List<Vessel> subscribedVessels(){
        User user = getCurrentUser();
        List<VesselPreferences> vesselPreferencesList = vesselPreferencesRepository.allVesselPreferences(user);
        List<Vessel> subscribedVessels = new ArrayList<Vessel>();
        for(VesselPreferences vp: vesselPreferencesList){
            Vessel vessel = vp.getVessel();
            subscribedVessels.add(vessel);
        }
        return subscribedVessels;
    }
}
