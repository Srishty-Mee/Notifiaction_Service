package com.Assignment.notification.services.blacklistRedisService;


import java.util.List;
import java.util.Set;

public interface BlacklistService {

    Set getAll();

    String addToBlacklist(List<String> theList);

    String whitelist(List<String> theList);

    boolean checkIfBlackListed(String theNumber);
}


