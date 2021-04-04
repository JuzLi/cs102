package com.ahoy.ahoy.user;

import com.ahoy.ahoy.alert.Alert;
import com.ahoy.ahoy.alert.AlertRepository;
import com.ahoy.ahoy.email.Email;
import com.ahoy.ahoy.email.EmailService;
import com.ahoy.ahoy.vessel.Vessel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    VesselPreferenceRepository vesselPreferencesRepository;
    @Autowired
    AlertPreferenceRepository alertPreferenceRepository;
    @Autowired
    AlertRepository alertRepository;
    @Autowired
    EmailService emailService;

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

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
    public String createTempPass(){
        Random random = new Random();
        int targetStringLength = 8 + random.nextInt(8);
        int leftLimit = 48;
        int rightLimit = 122;
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

    public void recoverAccount(String username, String email){
        User user = userRepository.findByUsernameAndEmail(username, email);
        if(user != null) {
            String tempPass = createTempPass();
            emailService.createRecoveryEmail(username, email, tempPass);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encoded_pass = passwordEncoder.encode(tempPass);
            userRepository.updatePassword(username, encoded_pass);
        }

    }

    public void updatePassword(String password){
        User user = getCurrentUser();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encoded_pass = passwordEncoder.encode(password);
        userRepository.updatePassword(user.getUsername(), encoded_pass);
    }

    public void updateEmail(String email){
        User user = getCurrentUser();
        userRepository.updateEmail(user.getUsername(), email);
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
        List<String> alertTypeList = alertPreferenceRepository.allSubscribedAlertTypes(user.getUsername());
        return alertTypeList;
    }

    public List<String> unsubscribedAlertTypes(){
        List<String> subscribedAlerts = subscribedAlertTypes();
        List<String> returnList = new ArrayList<String>();
        returnList.addAll(alertRepository.allAlertType());
        returnList.removeAll(subscribedAlerts);
        return returnList;
    }

    public void removeAlertPreference(String type){
        User user = getCurrentUser();
        alertPreferenceRepository.deleteAlertPreference(user,type);
    }

    public List<Alert> retrieveAlerts(User user, String date){
        List<String> alertTypeList = alertPreferenceRepository.allSubscribedAlertTypes(user.getUsername());
        List<VesselPreference> vesselPreferencesList = vesselPreferencesRepository.allVesselPreferences(user);
        List<Alert> alertList = new ArrayList<Alert>();
        List<String> alertPreference = alertPreferenceRepository.allSubscribedAlertTypes(user.getUsername());
        for(VesselPreference vesselPreference : vesselPreferencesList) {
            String abbrvslm = vesselPreference.getVesselPreferencePK().getAbbrvslm();
            alertList.addAll(alertRepository.retrieveAlerts(abbrvslm, alertPreference, date));
        }

//        for(String alertType : alertTypeList) {
//            for(VesselPreference vesselPreference : vesselPreferencesList) {
//                String abbrvslm = vesselPreference.getVesselPreferencePK().getAbbrvslm();
//                alertList.addAll(alertRepository.retrieveAlerts(abbrvslm, alertType, date));
//            }
//        }

        return alertList;
    }


}
