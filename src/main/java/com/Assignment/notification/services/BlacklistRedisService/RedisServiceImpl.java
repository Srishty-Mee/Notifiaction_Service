package com.Assignment.notification.services.BlacklistRedisService;

import com.Assignment.notification.customExceptions.ServiceException;
import com.Assignment.notification.utils.enums.CustomErrorCodes;
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

    @Value("${redis.set}")
    private String KEY;

    @Override
    public void addToBlacklistRedis(String thePhoneNumber) {
        try {
            theRedisTemplate.opsForSet().add(KEY, thePhoneNumber);
        }
        catch (Exception ex)
        {
            throw new ServiceException(CustomErrorCodes.REDIS_ERROR, "Error occurred at redis service");
        }

    }

    @Override
    public void whiteListRedis(String thePhoneNumber) {
        try {
            theRedisTemplate.opsForSet().remove(KEY,thePhoneNumber);
        }
        catch (Exception ex)
        {
            throw new ServiceException(CustomErrorCodes.REDIS_ERROR, "Error occurred at redis service");
        }

    }

    @Override
    public Set getAllRedis() {
        try {
            return theRedisTemplate.opsForSet().members(KEY) ;
        }
        catch (Exception ex)
        {
            throw new ServiceException(CustomErrorCodes.REDIS_ERROR, "Error occurred at redis service");
        }

    }

    @Override
    public Boolean CheckIfBlacklistedRedis(String thePhoneNumber) {
        try {
            return  theRedisTemplate.opsForSet().isMember(KEY, thePhoneNumber);
        }
        catch (Exception ex)
        {
            throw new ServiceException(CustomErrorCodes.REDIS_ERROR, "Error occurred at redis service");
        }
    }
}
