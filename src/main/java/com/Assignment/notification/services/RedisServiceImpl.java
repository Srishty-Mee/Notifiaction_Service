package com.Assignment.notification.services;

import com.Assignment.notification.repositories.BlacklistRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RedisServiceImpl implements RedisService {

    Logger LOGGER = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Autowired
    private RedisTemplate theRedisTemplate;

//    @Autowired
//    private BlacklistRepo theBlacklistDB;

    @Value("${redis.set}")
    private String KEY;

    @Override
    public String addToBlacklistRedis(String thePhoneNumber) {
        return null;
    }

    @Override
    public String whiteListRedis(String thePhoneNumber) {
        theRedisTemplate.opsForSet().remove(KEY,thePhoneNumber);
        return "Successfully Whitelisted";
    }

    @Override
    public Set getAllRedis() {
        return theRedisTemplate.opsForSet().members(KEY) ;
    }

    @Override
    public Boolean CheckIfBlacklistedRedis(String thePhoneNumber) {
        return  theRedisTemplate.opsForSet().isMember(KEY, thePhoneNumber);
    }
}
