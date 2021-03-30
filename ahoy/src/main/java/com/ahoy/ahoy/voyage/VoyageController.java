package com.ahoy.ahoy.voyage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class VoyageController {
    @Autowired
    VoyageService voyageService;

    @Autowired
    VoyageRepository voyageRepository;

    @ResponseBody
    @RequestMapping(path = "/ajax/retrieveTodayVoyage", method = RequestMethod.GET)
    public List<Voyage> retrieveTodayVoyage(){
        String today = java.time.LocalDate.now().toString();
        String startDT = today+" 00:00:00";
        String endDT = today+" 23:59:59";
        return voyageRepository.retrieveVoyagesBetweenDates(startDT,endDT);
    }



}
