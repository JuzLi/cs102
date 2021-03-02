package com.ahoy.ahoy.vessel;


import com.ahoy.ahoy.repo.VesselRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VesselController {

    @Autowired
    VesselRepository vesselRepository;

    @Autowired
    private VesselService vesselService;

    @GetMapping("/queryDB")
    public String vesselsWithName(){
        System.out.println("Finding!");
        System.out.println(vesselRepository.findByFullName("Titanic").toString());
        return "vesselFinder";
    }

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("vesselDetails", new Vessel());
        return "index";
    }

    @PostMapping("/index")
    public String indexSubmit(@ModelAttribute("vesselDetails") Vessel v){
        return "result";
    }

}
