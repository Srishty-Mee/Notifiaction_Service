package com.Assignment.notification.services.BlacklistRedisService;

import com.Assignment.notification.customExceptions.ServiceException;
import com.Assignment.notification.services.HelperService;
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

    @Override
    public Set getAll() {
        try {
            return  theRedisService.getAllRedis();
        }
        catch (ServiceException ex)
        {
            throw new ServiceException(ex.getErrorCode(), ex.getErrorMessage());
        }
    }

    @Override
    public String addToBlacklist(List<String> theList) {

        if(theList.size()==0)throw new ServiceException("EMPTY_LIST", "can not process an empty list");
        int validCount=0, invalidCount=0, inBlacklist=0;
        for(String theNumber: theList) {
            if(theHelperService.validNumber(theNumber) ) invalidCount++;
            else if(!checkIfBlackListed(theNumber))inBlacklist++;
            else
            {
                validCount++;
                try{
                    theRedisService.addToBlacklistRedis(theNumber);
                }
                catch (ServiceException ex)
                {
                    throw new ServiceException(ex.getErrorCode(), ex.getErrorMessage());
                }
            }
        }
        return String.valueOf(validCount)+ " number(s) blacklisted, " + String.valueOf(invalidCount)+ " invalid number(s) found, "
                +String.valueOf(inBlacklist)+ " number(s) were already in blacklist." ;
    }


    @Override
    public String whitelist(List<String> theList) {
        if(theList.size()==0)throw new ServiceException("EMPTY_LIST", "can not process an empty list");
        int validCount=0, invalidCount=0, inWhitelist=0;
        for(String theNumber: theList) {
            if(theHelperService.validNumber(theNumber) ) invalidCount++;
            else if(!checkIfBlackListed(theNumber))inWhitelist++;
            else
                validCount++;
                try{
                    theRedisService.whiteListRedis(theNumber);
                }
                catch (ServiceException ex)
                {
                    throw new ServiceException(ex.getErrorCode(), ex.getErrorMessage());
                }
            }
        return String.valueOf(validCount)+ " number(s) blacklisted, " + String.valueOf(invalidCount)+ " invalid number(s) found "
                +String.valueOf(inWhitelist)+ " number(s) were already in whitelist" ;

    }

    @Override
    public boolean checkIfBlackListed(String theNumber) { return theRedisService.CheckIfBlacklistedRedis(theNumber); }
}
