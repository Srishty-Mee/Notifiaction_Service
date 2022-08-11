package com.Assignment.notification.controllers;

import com.Assignment.notification.services.BlacklistService;
import com.Assignment.notification.services.BlacklistServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/blacklist/")
public class BlacklistController
{
    @Autowired
    private BlacklistServiceImpl theBlacklistService;

    @GetMapping
    public Set GetAllBlacklistNumbers()
    {
        return theBlacklistService.getAll();
    }

    @PostMapping
    public  String AddToBlacklist (@RequestBody HashMap<String, List<String>> theRequest)
    {
        List<String>theList= theRequest.get("phoneNumbers");
        for(String theNumber: theList) {
            String res= theBlacklistService.addToBlacklist(theNumber);
        }
        return "Successfully blacklisted";
    }


    @DeleteMapping
    public String WhiteList(@RequestBody HashMap<String, List<String>> theRequest)
    {
        List<String>theList= theRequest.get("phoneNumbers");
        for(String theNumber: theList) {
            String res = theBlacklistService.whitelist(theNumber);
        }
        return "Successfully whitelisted";
    }

}
