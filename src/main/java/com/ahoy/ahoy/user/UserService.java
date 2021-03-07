package com.ahoy.ahoy.user;

import com.ahoy.ahoy.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Boolean doesUserExist(String username){
        User u = userRepository.findByUsername(username);
        if(u == null){
            return false;
        }
        return true;

    }
}
