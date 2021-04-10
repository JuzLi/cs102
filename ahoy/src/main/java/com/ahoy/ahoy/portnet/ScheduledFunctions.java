package com.ahoy.ahoy.portnet;


import com.ahoy.ahoy.alert.Alert;
import com.ahoy.ahoy.berth.Berth;
import com.ahoy.ahoy.berth.BerthRepository;
import com.ahoy.ahoy.berth.BerthService;
import com.ahoy.ahoy.email.Email;
import com.ahoy.ahoy.email.EmailService;
import com.ahoy.ahoy.user.User;
import com.ahoy.ahoy.user.UserRepository;
import com.ahoy.ahoy.user.UserService;
import com.ahoy.ahoy.vessel.VesselRepository;
import com.ahoy.ahoy.voyage.VoyageRepository;
import com.ahoy.ahoy.vessel.Vessel;
import com.ahoy.ahoy.vessel.VesselService;
import com.ahoy.ahoy.voyage.Voyage;
import com.ahoy.ahoy.voyage.VoyageService;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class ScheduledFunctions {

    @Autowired
    PortnetConnector portnetConnector;

    @Autowired
    VesselService vesselService;
    @Autowired
    VesselRepository vesselRepository;
    @Autowired
    BerthService berthService;
    @Autowired
    BerthRepository berthRepository;
    @Autowired
    VoyageService voyageService;
    @Autowired
    VoyageRepository voyageRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;


    @Scheduled(cron = "${post.timing}", zone = "GMT+8.00")
    public void retrieveVesselsBerthing() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String date_from = dateFormat.format(today);
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        //c.add(Calendar.DATE, 1);
        Date temp = c.getTime();
        String dateFrom = dateFormat.format(temp);
        c.add(Calendar.DATE, 6);
        temp = c.getTime();
        String dateTo = dateFormat.format(temp);
        JsonArray results = portnetConnector.getUpdate(dateFrom, dateTo);
        for (int i = 0; i < results.size(); i++) {
            JsonObject j = results.get(i).getAsJsonObject();
            if(j.get("shiftSeqN").getAsString().equals("2")){
                continue;
            }
            Vessel v = new Vessel();
            Berth b = new Berth();



            v.setAbbrvslm(j.get("abbrVslM").getAsString());
            v.setFullvslm(j.get("fullVslM").getAsString());
            vesselService.createVessel(v);
            Vessel vessel = vesselRepository.findByShortName(j.get("abbrVslM").getAsString());
            Berth berth = null;
            if (j.get("berthN") instanceof JsonNull == false) {
                b.setBerthnum(j.get("berthN").getAsString());
                berthService.createBerth(b);
                berth = berthRepository.findByBerthNum(j.get("berthN").getAsString());
            }


            String invoyn = j.get("inVoyN").getAsString();
            String bthgdt = j.get("bthgDt").getAsString();
            String outvoyn = j.get("outVoyN").getAsString();
            String unbthgdt = j.get("unbthgDt").getAsString();
            String status = j.get("status").getAsString();
            String fullinvoyn = null;
            if (j.get("fullInVoyN") instanceof JsonNull == false) {
                fullinvoyn = j.get("fullInVoyN").getAsString();
            }

            try{
                voyageService.createVoyage(vessel, fullinvoyn, invoyn, outvoyn, bthgdt, unbthgdt,berth, status);
            }
            catch (Exception e){
                System.out.println(e.getMessage());

            }

        }

    }

    @Scheduled(cron = "${get.timing}", zone = "GMT+8.00")
    public void getVoyageDetails(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String date_from = dateFormat.format(today);
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        //removed because we include current day
        //c.add(Calendar.DATE, 1);
        Date temp = c.getTime();
        String dateFrom = dateFormat.format(temp);
        c.add(Calendar.DATE, 2);
        temp = c.getTime();
        String dateTo = dateFormat.format(temp);
        List<Voyage> voyageList = voyageRepository.retrieveVoyagesBetweenDates(dateFrom,dateTo);

        for(int i = 0; i < voyageList.size(); i++){
            Voyage currVoyage = voyageList.get(i);
            JsonObject j = portnetConnector.getVesselDetails(currVoyage);
            if(j.get("Error") == null){
                float avg_speed = j.get("AVG_SPEED").getAsFloat();
                int distance_to_go = j.get("DISTANCE_TO_GO").getAsInt();
                float max_speed = j.get("MAX_SPEED").getAsFloat();
                int is_patching_activated = j.get("IS_PATCHING_ACTIVATED").getAsInt();
                String patching_predicted_btr = j.get("PATCHING_PREDICTED_BTR").getAsString();
                String predicted_btr = j.get("PREDICTED_BTR").getAsString();
                voyageService.createVoyageDetails(currVoyage,avg_speed,distance_to_go,max_speed,is_patching_activated,patching_predicted_btr,predicted_btr);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }


    //send alert email
    @Scheduled(cron = "${email.timing}", zone = "GMT+8.00")
    public void sendEmailAlert() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.HOUR, -6);
        Date temp = c.getTime();
        String last6hours = dateFormat.format(temp);

        List<User> allUsers = userService.findAllUsers();
        for(User user: allUsers){
            Email email = new Email();

            email.setName(user.getUsername());
            email.setEmailAddress(user.getEmail());
            email.setAlertList(userService.retrieveAlerts(user,last6hours));

            try{
                //create message to send
                emailService.sendEmail(email);
                System.out.println("alert email sent to " + user.getEmail());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}



