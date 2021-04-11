package com.ahoy.ahoy.controllers;

import com.ahoy.ahoy.user.UserRepository;
import com.ahoy.ahoy.user.User;
import com.ahoy.ahoy.user.UserService;
import com.ahoy.ahoy.vessel.Vessel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ViewController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String landing(){return "landing";}

    @GetMapping("/homepage")
    public String homePage(){
        return "homepage";
    }

    @GetMapping("/vesselSubscribe")
    public String vesselSubscribe(){
        return "vesselSubscribe";
    }

    @GetMapping("/schedule")
    public String schedule(){
        return "schedule";
    }
    @GetMapping("/alerts")
    public String alerts(){
        return "alerts";
    }

    @GetMapping("/alertconfigurations")
    public String alertconfigurations(){
        return "alertconfigurations";
    }

    @GetMapping("/settings")
    public String settings(){
        return "settings";
    }


    @GetMapping("/deleteAccount")
    public String deleteAccount(){
        userService.deleteCurrentUser();
        return "landing";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("userSignUp", new User());
        return "register";
    }

    @PostMapping("/register")
    public String createNewAccount(@ModelAttribute("userSignUp") User u){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encoded_pass = passwordEncoder.encode(u.getPassword());
        u.setPassword(encoded_pass);
        if(userService.doesUserExist(u.getUsername()) == false){
        userRepository.save(u);
        return "landing";}
        else{
            return "signupError";
        }
    }

    @GetMapping("/vpref")
    public String vpref(){
        return "vesselSubscriptions";
    }

    @GetMapping("/apref")
    public String apref(){
        return "alertSubscriptions";
    }

    @GetMapping("/changeAccountDetails")
    public String changeAccountDetails(){
        return "changeAccountDetails";
    }

    @GetMapping("/forgotPassword")
    public String recoverAccount(){
        return "forgotPassword";
    }

    @GetMapping("/voyageDetails")
    public String voyageDetails(){
        return "voyageDetails";
    }

}
