package com.ahoy.ahoy.voyage;

import com.ahoy.ahoy.alert.Alert;
import com.ahoy.ahoy.alert.AlertService;
import com.ahoy.ahoy.berth.Berth;
import com.ahoy.ahoy.berth.BerthRepository;
import com.ahoy.ahoy.vessel.Vessel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class VoyageService {
    @Autowired
    BerthRepository berthRepository;
    @Autowired
    VoyageRepository voyageRepository;
    @Autowired
    VoyageDetailsRepository voyageDetailsRepository;
    @Autowired
    AlertService alertService;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void generateAlertsAndUpdateVoyage(Voyage voyage, String updatedBtr, String updatedUnbtr, Berth updatedBerth, String updatedStatus){
        alertService.generateVoyageAlerts(voyage, updatedBtr, updatedUnbtr, updatedBerth, updatedStatus);
        updateVoyage(voyage, updatedBtr,updatedUnbtr, updatedBerth, updatedStatus);

    }

    public void updateVoyage(Voyage voyage, String updatedBtr, String updatedUnbtr, Berth updatedBerth, String updatedStatus){
        VoyagePK voyagePK = voyage.getVoyagePK();
        voyage.setBtrdt(updatedBtr);
        voyage.setUnbthgdt(updatedUnbtr);
        voyage.setBerth(updatedBerth);
        voyage.setStatus(updatedStatus);
        voyageRepository.updateBtrdt(voyagePK, voyage.getBtrdt());
        voyageRepository.updateUnbthgdt(voyagePK,voyage.getUnbthgdt());
        voyageRepository.updateBerth(voyagePK,voyage.getBerth());
        voyageRepository.updateStatus(voyagePK,voyage.getStatus());
    }

    public void createVoyage(Vessel vessel, String fullinvoyn, String invoyn, String outvoyn, String updatedBtr, String updatedUnbtr, Berth updatedBerth, String updatedStatus){
        Voyage voyage = voyageRepository.findByPrimarykey(vessel.getAbbrvslm(), invoyn);
        if(voyage == null) {
            voyage = new Voyage();
            voyage.setVessel(vessel);
            voyage.setInvoyn(invoyn);
            voyage.setFullinvoyn(fullinvoyn);
            voyage.setOutvoyn(outvoyn);
            voyage.setBerth(updatedBerth);
            voyage.setBtrdt(updatedBtr);
            voyage.setUnbthgdt(updatedUnbtr);
            voyage.setStatus(updatedStatus);
            voyageRepository.save(voyage);
        }
        else{
            generateAlertsAndUpdateVoyage(voyage,updatedBtr,updatedUnbtr, updatedBerth, updatedStatus);
        }
    }

    public List<Voyage> allVoyagesFromToday(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        String dateFrom = dateFormat.format(today);
        return voyageRepository.retrieveVoyagesFromDates(dateFrom);
    }
    public List<Voyage> filterVoyagesArrivingByDate(List<Voyage> voyageList, int days){
        //for voyages arriving today, days = 0
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE, days);
        Date arrivalDay = c.getTime();
        String arrivalDayString = dateFormat.format(arrivalDay);
        List<Voyage> filteredList = new ArrayList<Voyage>();
        for(Voyage voyage: voyageList){
            String berthingDay = voyage.getBtrdt().substring(0,10);
            if(berthingDay.equals(arrivalDayString)){
                filteredList.add(voyage);
            }



        }

        return filteredList;
    }

    public String generateVslvoy(Voyage voyage){
        VoyagePK voyagePK = voyage.getVoyagePK();
        String temp = voyagePK.getAbbrvslm() + voyagePK.getInvoyn();
        String temp1 = temp.replaceAll("/", "");
        String vslvoy = temp1.replaceAll("\\s", "");
        return vslvoy;
    }

    public void createVoyageDetails(Voyage voyage, float avg, int distance, float max, int patching, String patching_predicted, String predicted){
        int alertCount = voyageDetailsRepository.countDetailsOfVoyage(voyage);
        alertCount++;
        try {
            VoyageDetails voyageDetails = new VoyageDetails();
            voyageDetails.setVoyage(voyage);
            voyageDetails.voyageDetailsPK.setVoyageDetailsID(alertCount);
            voyageDetails.setVslvoy(generateVslvoy(voyage));
            voyageDetails.setAvg_speed(avg);
            voyageDetails.setDistance_to_go(distance);
            voyageDetails.setMax_speed(max);
            voyageDetails.setIs_patching_activated(patching);
            voyageDetails.setTimestamp(java.time.LocalDateTime.now().format(this.formatter));
            if (patching_predicted.equals("NaT") == false) {
                voyageDetails.setPatching_predicted_btr(patching_predicted);
            }
            voyageDetails.setPredicted_btr(predicted);
            voyageDetailsRepository.save(voyageDetails);
            generateVoyageDetailsAlert(voyageDetails);
        } catch (Exception e){
            e.printStackTrace(System.out);
            System.out.println(e.getMessage());
        }

    }

    public void generateVoyageDetailsAlert(VoyageDetails latest){
        Voyage latest_voyage = latest.getVoyage();
        VoyageDetails second_latest = findSecondLatestDetails(latest_voyage);
        alertService.generateVoyageDetailsAlerts(second_latest,latest);
    }

    public String berthingTime(VoyageDetails voyageDetails){
        int patching = voyageDetails.getIs_patching_activated();
        if(patching == 1){
            return voyageDetails.getPatching_predicted_btr();
        }
        else{
            return voyageDetails.getPredicted_btr();
        }
    }
    public VoyageDetails findLatestDetails(Voyage voyage){
        int latest = voyageDetailsRepository.countDetailsOfVoyage(voyage);
        return voyageDetailsRepository.findDetails(voyage, latest);
    }

    public VoyageDetails findSecondLatestDetails(Voyage voyage){
        int latest = voyageDetailsRepository.countDetailsOfVoyage(voyage) - 1;
        return voyageDetailsRepository.findDetails(voyage, latest);
    }

    public VoyageDetails findingOneDetail(Voyage voyage){
        System.out.println(voyage.getInvoyn());
        for(VoyageDetails a: voyageDetailsRepository.findOneDetail(voyage)){
            return a;
        }
        return null;
    }
}
