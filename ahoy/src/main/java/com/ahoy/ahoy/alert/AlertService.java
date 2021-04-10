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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class AlertService {
    private List<DateTimeFormatter> formatterList;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private AlertRepository alertRepository;
    @Autowired
    BerthRepository berthRepository;
    @Autowired
    private VoyageService voyageService;

    public AlertService(){
        initialiseFormatters();
    }
    public void initialiseFormatters(){
        List<String> formatStrings = new ArrayList<String>();
        formatStrings.add("yyyy-MM-dd HH:mm:ss");
        formatStrings.add("yyyy-MM-dd'T'HH:mm:ss");
        formatStrings.add("yyyy-MM-dd HH:mm");
        List<DateTimeFormatter> formatterList= new ArrayList<DateTimeFormatter>();
        for(String format:formatStrings){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
            formatterList.add(dtf);
        }
        this.formatterList = formatterList;

    }

    public LocalDateTime parseDateTimeString(String datestring){
        for(DateTimeFormatter dtf: this.formatterList){
            try{
                return LocalDateTime.parse(datestring, dtf);
            }
            catch (DateTimeParseException e){
            }
        }
        return null;
    }

    public int generateNewAlertCount(Voyage voyage, String alertType){
        int count = alertRepository.countAlertsOfVoyageAndType(voyage, alertType);
        count++;
        return count;

    }

    public void createSpeedAlert(VoyageDetails secondLast, VoyageDetails last){
        String content = null;

        String alertType = "Change in Average Speed";

        double last_average_speed = last.getAvg_speed();
        double secondLast_average_speed = secondLast.getAvg_speed();
        if(last_average_speed - secondLast_average_speed == 0){
            return;
        }
        Alert alert = new Alert();
        alert.setVoyage(last.getVoyage());

        alert.setAlerttype(alertType);
        alert.setAlertCount(generateNewAlertCount(last.getVoyage(),alertType));

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

        String alertType = "Change in Average Speed";
        Alert alert = new Alert();
        alert.setVoyage(last.getVoyage());
        alert.setAlerttype(alertType);
        alert.setAlertCount(generateNewAlertCount(last.getVoyage(),alertType));

        alert.setAlertdatetime(java.time.LocalDateTime.now().format(this.formatter));
        String content = "Average speed is " + last_average_speed;
        alert.setAlertcontent(content);
        alertRepository.save(alert);
    }

    public void createBtrAlert(VoyageDetails secondLast, VoyageDetails last){
        String secondLast_Btr_String = voyageService.berthingTime(secondLast);
        String last_Btr_String = voyageService.berthingTime(last);

        LocalDateTime last_Btr = parseDateTimeString(last_Btr_String);
        LocalDateTime secondLast_Btr = parseDateTimeString(secondLast_Btr_String);



        if(last_Btr.isEqual(secondLast_Btr)){
            return;
        }
        else{
            createBtrAlert(last);
        }
    }

    public void createBtrAlert(VoyageDetails last){
        String content = null;

        String alertType = "Change in Berthing Time";
        String last_Btr_String = voyageService.berthingTime(last);
        Voyage voyage = last.getVoyage();
        String voyage_Btr_String = voyage.getBtrdt();
        LocalDateTime last_Btr = parseDateTimeString(last_Btr_String);
        LocalDateTime voyage_Btr = parseDateTimeString(voyage_Btr_String);

        Alert alert = new Alert();
        alert.setVoyage(last.getVoyage());
        alert.setAlerttype(alertType);
        alert.setAlertCount(generateNewAlertCount(last.getVoyage(),alertType));

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

        String alertType = "Change in Berthing Time";
        String voyage_Btr_String = voyage.getBtrdt();

        LocalDateTime updatedBtr = parseDateTimeString(updatedBtrString);
        LocalDateTime voyage_Btr = parseDateTimeString(voyage_Btr_String);



        if(updatedBtr.isEqual(voyage_Btr)){
            return;
        }
        Alert alert = new Alert();
        alert.setVoyage(voyage);

        alert.setAlerttype(alertType);
        alert.setAlertCount(generateNewAlertCount(voyage,alertType));

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
        String alertType = "Change in Unberthing Time";
        String voyage_Unbtr_String = voyage.getBtrdt();
        LocalDateTime updatedUnbtr = parseDateTimeString(updatedUnbtrString);
        LocalDateTime voyage_Unbtr = parseDateTimeString(voyage_Unbtr_String);
        if(updatedUnbtr.isEqual(voyage_Unbtr)){
            return;
        }
        Alert alert = new Alert();
        alert.setVoyage(voyage);
        alert.setAlerttype(alertType);
        alert.setAlertCount(generateNewAlertCount(voyage, alertType));
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
        String alertType = "Change in Berth";
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
        alert.setAlerttype(alertType);
        alert.setAlertCount(generateNewAlertCount(voyage,alertType));
        alert.setAlertdatetime(java.time.LocalDateTime.now().format(this.formatter));
        alert.setAlertcontent(content);
        alertRepository.save(alert);
    }

    public void createStatusAlert(Voyage voyage, String updatedStatus){
        String content = null;
        String alertType = "Change in Status";
        String voyage_status = voyage.getStatus();
        if (voyage_status.equals(updatedStatus)){
            return ;
        }
        Alert alert = new Alert();
        alert.setVoyage(voyage);
        alert.setAlerttype(alertType);
        alert.setAlertCount(generateNewAlertCount(voyage,alertType));

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
  
    public void generateVoyageDetailsAlerts(VoyageDetails second_latest, VoyageDetails latest){
        try {
            if (second_latest == null) {
                createBtrAlert(latest);
            } else {
                createBtrAlert(second_latest, latest);
                createSpeedAlert(second_latest, latest);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<Alert> filterAlertsByDate(List<Alert> alertList, int days){
        //for voyages arriving today, days = 0
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE, days);
        Date relevantDay = c.getTime();
        String relevantDayString = dateFormat.format(relevantDay);
        List<Alert> filteredList = new ArrayList<>();
        for(Alert alert: alertList) {
            if(alert == null){
                continue;
            }
            String alertDay = alert.getAlertdatetime().substring(0, 10);
            if (alertDay.equals(relevantDayString)) {
                filteredList.add(alert);
            }
        }
        return filteredList;

    }

    public List<Alert> retrieveLatestAlertsOfVoyage(Voyage voyage){
        List<String> alertTypeList = alertRepository.allAlertType();
        List<Alert> alertList = new ArrayList<>();
        for(String s: alertTypeList){
            Alert a = retrieveLatestAlertOfVoyageOfType(voyage, s);
            alertList.add(a);
        }
        return alertList;
    }

    public Alert retrieveLatestAlertOfVoyageOfType(Voyage voyage, String alertType){
        int count = alertRepository.countAlertsOfVoyageAndType(voyage,alertType);
        return alertRepository.retrieveLatestAlertOfTypeAndVoyage(voyage,alertType,count);
    }

}
