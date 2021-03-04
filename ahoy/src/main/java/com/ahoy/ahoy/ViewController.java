package com.ahoy.ahoy;

import com.ahoy.ahoy.vessel.Vessel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ViewController {
    @GetMapping("/homepage")
    public String homePage(){
        return "homepage";
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
