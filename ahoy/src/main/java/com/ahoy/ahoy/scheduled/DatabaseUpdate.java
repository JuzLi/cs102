package com.ahoy.ahoy.scheduled;


import com.ahoy.ahoy.berth.BerthService;
import com.ahoy.ahoy.portnet.PortnetConnector;
import com.ahoy.ahoy.vessel.VesselService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@Service
public class DatabaseUpdate {

    @Autowired
    PortnetConnector portnetConnector;

    @Autowired
    VesselService vesselService;

    @Autowired
    BerthService berthService;

    @Scheduled(cron = "${post.timing}")
    public void retrieveVesselsBerthing(){

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String date_from = dateFormat.format(today);
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE, 1);
        Date temp = c.getTime();
        String dateFrom = dateFormat.format(temp);
        c.add(Calendar.DATE,6);
        temp = c.getTime();
        String dateTo = dateFormat.format(temp);
        JsonArray results = portnetConnector.getUpdate(dateFrom, dateTo);
        for(int i = 0; i < results.size(); i++){
            JsonObject j = results.get(i).getAsJsonObject();
            vesselService.createVessel(j.get("abbrVslM").getAsString(),j.get("fullVslM").getAsString());

            if(j.get("berthN") instanceof JsonNull == false){
                berthService.createBerth(j.get("berthN").getAsString());
            }
            System.out.println(j);
        }
    }

//    @Scheduled(cron = "$(get.Timing)")
//    public void retrieveVesselDetails(){
//        ArrayList<Voyage> voyages = voyageRepository.findAllWithin3Days();
//        for(int i = 0; i < voyages.size(); i++){
//            Voyage v = voyages.get(i);
//            String fullVslM = v.getFullVslM();
//            String inVoyN = v.getInVoyN();
//            JsonObject vesselDetails = portnetConnector.getVesselDetails(fullVslM,inVoyN);
//
//        }
//    }
}
