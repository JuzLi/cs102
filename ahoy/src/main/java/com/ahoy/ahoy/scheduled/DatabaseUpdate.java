package com.ahoy.ahoy.scheduled;


import com.ahoy.ahoy.berth.Berth;
import com.ahoy.ahoy.berth.BerthService;
import com.ahoy.ahoy.portnet.PortnetConnector;
import com.ahoy.ahoy.vessel.Vessel;
import com.ahoy.ahoy.vessel.VesselService;
import com.ahoy.ahoy.voyage.Voyage;
import com.ahoy.ahoy.voyage.VoyageService;
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

    @Autowired
    VoyageService voyageService;

    @Scheduled(cron = "${post.timing}")
    public void retrieveVesselsBerthing() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String date_from = dateFormat.format(today);
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE, 1);
        Date temp = c.getTime();
        String dateFrom = dateFormat.format(temp);
        c.add(Calendar.DATE, 6);
        temp = c.getTime();
        String dateTo = dateFormat.format(temp);
        JsonArray results = portnetConnector.getUpdate(dateFrom, dateTo);

        for (int i = 0; i < results.size(); i++) {
            JsonObject j = results.get(i).getAsJsonObject();
            Vessel v = new Vessel();
            Berth b = new Berth();
            Voyage voyage = new Voyage();


            v.setAbbrvslm(j.get("abbrVslM").getAsString());
            v.setFullvslm(j.get("fullVslM").getAsString());
            vesselService.createVessel(v);


            if (j.get("berthN") instanceof JsonNull == false) {
                b.setBerthnum(j.get("berthN").getAsString());
                berthService.createBerth(b);
            }


            voyage.setVessel(v);
            voyage.setInvoyn(j.get("inVoyN").getAsString());
            voyage.setBtrdt(j.get("bthgDt").getAsString());
            voyage.setOutvoyn(j.get("outVoyN").getAsString());
            voyage.setUnbthgdt(j.get("unbthgDt").getAsString());
            voyage.setStatus(j.get("status").getAsString());
            if (j.get("fullInVoyN") instanceof JsonNull == false) {
                voyage.setFullinvoyn(j.get("fullInVoyN").getAsString());
            }
            if(b.getBerthnum() != null){
                voyage.setBerth(b);
            }
            voyageService.createVoyage(voyage);
            System.out.println(j);
        }
    }
}



