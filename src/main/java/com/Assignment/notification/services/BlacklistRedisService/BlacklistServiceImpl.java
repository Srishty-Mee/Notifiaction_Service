package com.Assignment.notification.services.blacklistRedisService;

import com.Assignment.notification.customExceptions.BadRequestException;
import com.Assignment.notification.services.messageService.MessageServiceImpl;
import com.Assignment.notification.services.otherServices.HelperService;
import com.Assignment.notification.utils.enums.CustomErrorCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class BlacklistServiceImpl implements BlacklistService {

    @Autowired
    private RedisService theRedisService;

    @Autowired
    private HelperService theHelperService;

    private static final Logger LOGGER =
            LoggerFactory.getLogger(MessageServiceImpl.class);
    @Override
    public Set getAll() {
        LOGGER.info("get complete blacklist called.");
        return  theRedisService.getAllRedis();
    }

    @Override
    public String addToBlacklist(List<String> theList) {
        LOGGER.info("add to blacklist called.");

        if(theList.size()==0)throw new BadRequestException(CustomErrorCodes.EMPTY_LIST, "can not process an empty list");
        int validCount=0, invalidCount=0, inBlacklist=0;
        for(String theNumber: theList) {
            if(!theHelperService.validNumber(theNumber) )
                invalidCount++;
            else if(checkIfBlackListed(theNumber))
                inBlacklist++;
            else
            {
                validCount++;
                theRedisService.addToBlacklistRedis(theNumber);
            }
        }
        return String.valueOf(validCount)+ " number(s) blacklisted, " + String.valueOf(invalidCount)+ " invalid number(s) found, "
                +String.valueOf(inBlacklist)+ " number(s) were already in blacklist." ;
    }


    @Override
    public String whitelist(List<String> theList){
        LOGGER.info("remove from blacklist called.");
        if(theList.size()==0)
            throw new BadRequestException(CustomErrorCodes.EMPTY_LIST, "Can not process an empty list.");

        int validCount=0, invalidCount=0, inWhitelist=0;
        for(String theNumber: theList) {
            if(theHelperService.validNumber(theNumber) ==false )
                invalidCount++;
            else if(checkIfBlackListed(theNumber)==false)
                inWhitelist++;
            else {
                validCount++;
                theRedisService.whiteListRedis(theNumber);
            }
        }
        return String.valueOf(validCount)+ " number(s) whitelisted, " + String.valueOf(invalidCount)+ " invalid number(s) found "
                +String.valueOf(inWhitelist)+ " number(s) were already in whitelist" ;

    }

    @Override
    public boolean checkIfBlackListed(String theNumber) {
            LOGGER.info("check if blacklisted called.");
        return theRedisService.CheckIfBlacklistedRedis(theNumber);
    }
}
