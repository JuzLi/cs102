package com.ahoy.ahoy;

import com.ahoy.ahoy.vessel.Vessel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

public class ViewController {

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
