package com.ahoy.ahoy.alert;

import com.ahoy.ahoy.berth.Berth;
import com.ahoy.ahoy.berth.BerthRepository;
import com.ahoy.ahoy.voyage.Voyage;
import com.ahoy.ahoy.voyage.VoyageDetails;
import com.ahoy.ahoy.voyage.VoyageService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Service
public class AlertService {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private DateTimeFormatter alternate_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    @Autowired
    private AlertRepository alertRepository;
    @Autowired
    BerthRepository berthRepository;
    @Autowired
    private VoyageService voyageService;

    public int generateNewAlertCount(Voyage voyage, String alertType){
        int count = alertRepository.countAlertsOfVoyageAndType(voyage, alertType);
        count++;
        return count;

    }

    public void createSpeedAlert(VoyageDetails secondLast, VoyageDetails last){
        String content = null;
        double last_average_speed = last.getAvg_speed();
        double secondLast_average_speed = secondLast.getAvg_speed();
        if(last_average_speed - secondLast_average_speed == 0){
            return;
        }
        Alert alert = new Alert();
        alert.setVoyage(last.getVoyage());
        alert.setAlerttype("Speed");
        alert.setAlertCount(generateNewAlertCount(last.getVoyage(),"Speed"));
        alert.setAlertdatetime(java.time.LocalDateTime.now().format(this.formatter));

        if(last_average_speed > secondLast_average_speed){
            content = "Average speed has increased to " + last_average_speed;
        }
        else{
            content = "Average speed has decreased to " + last_average_speed;
        }
        alert.setAlertcontent(content);
        alertRepository.save(alert);

    }

    public void createSpeedAlert(VoyageDetails last){
        double last_average_speed = last.getAvg_speed();
        Alert alert = new Alert();
        alert.setVoyage(last.getVoyage());
        alert.setAlerttype("Speed");
        alert.setAlertCount(generateNewAlertCount(last.getVoyage(),"Speed"));
        alert.setAlertdatetime(java.time.LocalDateTime.now().format(this.formatter));
        String content = "Average speed is " + last_average_speed;
        alert.setAlertcontent(content);
        alertRepository.save(alert);
    }

    public void createBtrAlert(VoyageDetails secondLast, VoyageDetails last){
        String secondLast_Btr_String = voyageService.berthingTime(secondLast);
        String last_Btr_String = voyageService.berthingTime(last);
        LocalDateTime last_Btr;
        LocalDateTime secondLast_Btr;
        try{
            last_Btr  = LocalDateTime.parse(last_Btr_String, this.formatter);
        }
        catch(Exception e){
            last_Btr = LocalDateTime.parse(last_Btr_String, this.alternate_formatter);
        }

        try{
            secondLast_Btr = LocalDateTime.parse(secondLast_Btr_String, this.formatter);
        }
        catch(Exception e){
            secondLast_Btr = LocalDateTime.parse(secondLast_Btr_String, this.alternate_formatter);
        }

        if(last_Btr.isEqual(secondLast_Btr)){
            return;
        }
        else{
            createBtrAlert(last);
        }
    }

    public void createBtrAlert(VoyageDetails last){
        String content = null;
        String last_Btr_String = voyageService.berthingTime(last);
        Voyage voyage = last.getVoyage();
        String voyage_Btr_String = voyage.getBtrdt();
        LocalDateTime last_Btr;
        LocalDateTime voyage_Btr;
        try{
            last_Btr  = LocalDateTime.parse(last_Btr_String, this.formatter);
        }
        catch(Exception e){
            last_Btr = LocalDateTime.parse(last_Btr_String, this.alternate_formatter);
        }
        try{
            voyage_Btr = LocalDateTime.parse(voyage_Btr_String, this.formatter);
        }
        catch(Exception e){
            voyage_Btr = LocalDateTime.parse(voyage_Btr_String, this.alternate_formatter);
        }
        Alert alert = new Alert();
        alert.setVoyage(last.getVoyage());
        alert.setAlerttype("Berthing Time");
        alert.setAlertCount(generateNewAlertCount(last.getVoyage(),"Berthing Time"));
        alert.setAlertdatetime(java.time.LocalDateTime.now().format(this.formatter));

        if(last_Btr.isEqual(voyage_Btr)){
            content = "Voyage is on time, arriving at " + last_Btr_String;
        }
        else if(last_Btr.isAfter(voyage_Btr)){
            content = "Voyage is delayed, arriving at " + last_Btr_String;
        }
        else{
            content = "Voyage is early, arriving at " + last_Btr_String;
        }
        alert.setAlertcontent(content);
        alertRepository.save(alert);

    }

    public void createBtrAlert(Voyage voyage, String updatedBtrString){
        String content = null;
        String voyage_Btr_String = voyage.getBtrdt();

        LocalDateTime updatedBtr;
        LocalDateTime voyage_Btr;

        try{
            updatedBtr = LocalDateTime.parse(updatedBtrString, this.formatter);
        }
        catch(Exception e){
            updatedBtr = LocalDateTime.parse(updatedBtrString, this.alternate_formatter);
        }
        try{
            voyage_Btr = LocalDateTime.parse(voyage_Btr_String, this.formatter);
        }
        catch(Exception e){
            voyage_Btr = LocalDateTime.parse(voyage_Btr_String, this.alternate_formatter);
        }
        if(updatedBtr.isEqual(voyage_Btr)){
            return;
        }
        Alert alert = new Alert();
        alert.setVoyage(voyage);
        alert.setAlerttype("Berthing Time");
        alert.setAlertCount(generateNewAlertCount(voyage,"Berthing Time"));
        alert.setAlertdatetime(java.time.LocalDateTime.now().format(this.formatter));


        if(updatedBtr.isAfter(voyage_Btr)){
            content = "Voyage is delayed, arriving at " + updatedBtrString;
        }
        else{
            content = "Voyage is delayed, arriving at " + updatedBtrString;
        }
        alert.setAlertcontent(content);
        alertRepository.save(alert);
    }

    public void createUnberthingAlert(Voyage voyage, String updatedUnbtrString){
        String content = null;
        String voyage_Unbtr_String = voyage.getBtrdt();
        LocalDateTime updatedUnbtr;
        LocalDateTime voyage_Unbtr;
        try{
            updatedUnbtr = LocalDateTime.parse(updatedUnbtrString, this.formatter);
        }
        catch(Exception e){
            updatedUnbtr = LocalDateTime.parse(updatedUnbtrString, this.alternate_formatter);
        }
        try{
            voyage_Unbtr = LocalDateTime.parse(voyage_Unbtr_String, this.formatter);
        }
        catch(Exception e){
            voyage_Unbtr = LocalDateTime.parse(voyage_Unbtr_String, this.alternate_formatter);
        }
        if(updatedUnbtr.isEqual(voyage_Unbtr)){
            return;
        }
        Alert alert = new Alert();
        alert.setVoyage(voyage);
        alert.setAlerttype("Unberthing Time");
        alert.setAlertCount(1);
        alert.setAlertdatetime(java.time.LocalDateTime.now().format(this.formatter));


        if(updatedUnbtr.isAfter(voyage_Unbtr)){
            content = "Voyage is leaving later, leaving at " + updatedUnbtrString;
        }
        else{
            content = "Voyage is leaving earlier, leaving at " + updatedUnbtrString;
        }
        alert.setAlertcontent(content);
        alertRepository.save(alert);
    }

    public void createBerthNumAlert(Voyage voyage, Berth berth){
        String content = null;
        if(berth == null){
            if(voyage.getBerth() == null){
                return;
            }
            content = "Vessel is no longer assigned a berth";
        }
        else{
            if(voyage.getBerth() == null){
                content = "Vessel is now assigned to Berth : " + berth.getBerthnum();
            }
            else if(voyage.getBerth().equals(berth)){
                return;
            }
            else{
                content = "Vessel is reassigned to Berth : " + berth.getBerthnum();
            }
        }

        Alert alert = new Alert();
        alert.setVoyage(voyage);
        alert.setAlerttype("Unberthing Time");
        alert.setAlertCount(generateNewAlertCount(voyage,"Unberthing Time"));
        alert.setAlertdatetime(java.time.LocalDateTime.now().format(this.formatter));
        alert.setAlertcontent(content);
        alertRepository.save(alert);
    }

    public void createStatusAlert(Voyage voyage, String updatedStatus){
        String content = null;

        String voyage_status = voyage.getStatus();
        if (voyage_status.equals(updatedStatus)){
            return ;
        }
        Alert alert = new Alert();
        alert.setVoyage(voyage);
        alert.setAlerttype("Change in Status");
        alert.setAlertCount(generateNewAlertCount(voyage,"Change in Status"));
        alert.setAlertdatetime(java.time.LocalDateTime.now().format(this.formatter));
        content = "Vessel is now " + updatedStatus;
        alert.setAlertcontent(content);
        alertRepository.save(alert);
    }

    public void generateVoyageAlerts(Voyage voyage, String updatedBtr, String updatedUnbtr, Berth updatedBerth, String updatedStatus){
        createBtrAlert(voyage, updatedBtr);
        createUnberthingAlert(voyage, updatedUnbtr);
        createBerthNumAlert(voyage, updatedBerth);
        createStatusAlert(voyage,updatedStatus);
    }
}
