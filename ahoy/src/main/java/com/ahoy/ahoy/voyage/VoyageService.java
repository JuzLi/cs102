package com.ahoy.ahoy.voyage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoyageService {

    @Autowired
    VoyageRepository voyageRepository;
    @Autowired
    VoyageDetailsRepository voyageDetailsRepository;
    public void createVoyage(Voyage voyage){
        if(voyageRepository.findByPrimarykey(voyage.getVessel().getAbbrvslm(), voyage.getInvoyn()) == null){
            voyageRepository.save(voyage);
        }
    }
    public String generateVslvoy(Voyage voyage){
        VoyagePK voyagePK = voyage.getVoyagePK();
        String temp = voyagePK.getAbbrvslm() + voyagePK.getInvoyn();
        String temp1 = temp.replaceAll("/", "");
        String vslvoy = temp1.replaceAll("\\s", "");
        return vslvoy;
    }
    public void createVoyageDetails(Voyage voyage, float avg, int distance, float max, int patching, String patching_predicted, String predicted){
        int alertCount = voyageDetailsRepository.countAlertsOfVoyage(voyage);
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
            if (patching_predicted.equals("NaT") == false) {
                voyageDetails.setPatching_predicted_btr(patching_predicted);
            }
            voyageDetails.setPredicted_btr(predicted);
            voyageDetailsRepository.save(voyageDetails);
        } catch (Exception e){
            e.printStackTrace(System.out);
            System.out.println(e.getMessage());
        }

    }
}
