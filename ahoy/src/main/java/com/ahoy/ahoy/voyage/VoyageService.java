package com.ahoy.ahoy.voyage;

import com.ahoy.ahoy.repo.VoyageDetailsRepository;
import com.ahoy.ahoy.repo.VoyageRepository;
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
    public void createVoyageDetails(Voyage voyage){
        int alertCount = voyageDetailsRepository.countAlertsOfVoyage(voyage);
        alertCount++;
        VoyageDetails voyageDetails = new VoyageDetails();
        voyageDetails.setVoyage(voyage);
        voyageDetails.voyageDetailsPK.setVoyageDetailsID(alertCount);
        voyageDetails.setVslvoy(generateVslvoy(voyage));
        voyageDetailsRepository.save(voyageDetails);

    }
}
