package com.ahoy.ahoy.berth;

import com.ahoy.ahoy.repo.BerthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BerthService {

    @Autowired
    BerthRepository berthRepository;

    public void createBerth(Berth b){
        if(berthRepository.findByBerthNum(b.getBerthnum()) == null){
            berthRepository.save(b);
        }
    }
}
