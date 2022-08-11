package com.Assignment.notification.services;


import java.util.Set;

public interface BlacklistService {

    Set getAll();

    String addToBlacklist(String theNumber);

    String whitelist(String theNumber);

    boolean checkIfBlackListed(String theNumber);
}


