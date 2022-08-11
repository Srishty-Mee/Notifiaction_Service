package com.Assignment.notification.services;

import com.Assignment.notification.repositories.BlacklistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class BlacklistServiceImpl implements BlacklistService{


    @Autowired
    private  RedisService theRedisService;

    @Autowired
    private BlacklistRepo theBlacklistDB;

    @Override
    public Set getAll() {
        return  theRedisService.getAllRedis();
    }

    @Override
    public String addToBlacklist(String theNumber) {
        return  theRedisService.addToBlacklistRedis(theNumber);
    }

    @Override
    public String whitelist(String theNumber) {
        return  theRedisService.whiteListRedis(theNumber);
    }

    @Override
    public boolean checkIfBlackListed(String theNumber) {
        //return theBlacklistDB.existsById(theNumber);
        return  true;
    }
}
