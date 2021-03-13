package com.ahoy.ahoy.berth;

import com.ahoy.ahoy.repo.BerthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BerthService {

    @Autowired
    BerthRepository berthRepository;

    public void createBerth(String berthnum){
        if(berthRepository.findByBerthNum(berthnum) == null){
            Berth b = new Berth(berthnum);
            berthRepository.save(b);
        }
    }
}
