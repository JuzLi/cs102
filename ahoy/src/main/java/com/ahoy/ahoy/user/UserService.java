package com.ahoy.ahoy.user;

import com.ahoy.ahoy.alert.AlertRepository;
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
    VesselPreferenceRepository vesselPreferencesRepository;
    private User user;
    @Autowired
    AlertPreferenceRepository alertPreferenceRepository;
    @Autowired
    AlertRepository alertRepository;

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
            VesselPreference vesselPreference = new VesselPreference();
            vesselPreference.setVessel(vessel);
            vesselPreference.setUser(user);
            vesselPreferencesRepository.save(vesselPreference);
        }
    }

    public List<Vessel> subscribedVessels(){
        User user = getCurrentUser();
        List<VesselPreference> vesselPreferenceList = vesselPreferencesRepository.allVesselPreferences(user);
        List<Vessel> subscribedVessels = new ArrayList<Vessel>();
        for(VesselPreference vp: vesselPreferenceList){
            Vessel vessel = vp.getVessel();
            subscribedVessels.add(vessel);
        }
        return subscribedVessels;
    }

    public List<Vessel> removeSubscribedVesselsFromList(List<Vessel> vesselList){
        List<Vessel> returnList = new ArrayList<Vessel>();
        List<Vessel> subscribedVessels = subscribedVessels();
        returnList.addAll(vesselList);
        returnList.removeAll(subscribedVessels);
        return returnList;

    }

    public void removeSubscribedVessel(Vessel vessel){
        User user = getCurrentUser();
        vesselPreferencesRepository.deleteVesselPreference(user, vessel);
    }

    public void createAlertPreference(String alerttype){
        User user = getCurrentUser();
        AlertPreference alertPreference = new AlertPreference();
        alertPreference.setUser(user);
        alertPreference.setAlerttype(alerttype);
        alertPreferenceRepository.save(alertPreference);
    }

    public List<String> subscribedAlertTypes(){
        User user = getCurrentUser();
        List<String> alertTypeList = alertPreferenceRepository.allSubscribedAlertTypes(user);
        return alertTypeList;
    }

    public List<String> unsubscribedAlertTypes(){
        List<String> subscribedAlerts = subscribedAlertTypes();
        return alertRepository.allAlertTypeNotLike(subscribedAlerts);
    }

    public void removeAlertPreference(String type){
        User user = getCurrentUser();
        alertPreferenceRepository.deleteAlertPreference(user,type);
    }


}
