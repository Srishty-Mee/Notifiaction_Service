package com.Assignment.notification.services.BlacklistRedisService;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

public interface RedisService {

    public  void addToBlacklistRedis(String thePhoneNumber);
    public  void whiteListRedis(String thePhoneNumber);
    public  Set getAllRedis();
    public Boolean CheckIfBlacklistedRedis(String thePhoneNumber);


}
