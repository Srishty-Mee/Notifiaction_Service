package com.Assignment.notification.services;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

public interface RedisService {

    public  String addToBlacklistRedis(String thePhoneNumber);
    public  String whiteListRedis(String thePhoneNumber);
    public  Set getAllRedis();
    public Boolean CheckIfBlacklistedRedis(String thePhoneNumber);


}
