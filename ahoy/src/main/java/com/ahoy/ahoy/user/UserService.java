package com.ahoy.ahoy.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User getCurrentUser(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return  userRepository.findByUsername(username);
    }

    public Boolean doesUserExist(String username){
        User u = userRepository.findByUsername(username);
        if(u == null){
            return false;
        }
        return true;

    }
}
