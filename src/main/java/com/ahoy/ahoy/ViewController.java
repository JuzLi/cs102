package com.ahoy.ahoy;

import com.ahoy.ahoy.repo.UserRepository;
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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;


    @GetMapping("/")
    public String landing(){
        return "landing";
    }

    @GetMapping("/homepage")
    public String homePage(){
        return "homepage";
    }

    @RequestMapping("/signup")
    public String signup(){
        return "signup";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
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

}
