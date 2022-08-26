package com.Assignment.notification.controllers;

import com.Assignment.notification.model.requests.BlacklistRequestModel;
import com.Assignment.notification.services.blacklistRedisService.BlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/blacklist")
public class BlacklistController
{
    @Autowired
    private BlacklistService theBlacklistService;

    @GetMapping
    public ResponseEntity<?> GetAllBlacklistNumbers() {
        Set res= theBlacklistService.getAll();
        HashMap<String,Set>response= new HashMap<>();
        response.put("data", res);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> AddToBlacklist (@RequestBody @Valid  BlacklistRequestModel theRequest) {
        List<String>theList= theRequest.getPhoneNumbers();
        String res= theBlacklistService.addToBlacklist(theList);

        HashMap<String,String>response= new HashMap<>();
        response.put("data", res);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    @DeleteMapping
    public ResponseEntity<?> WhiteList(@RequestBody @Valid BlacklistRequestModel theRequest) {
            List<String> theList = theRequest.getPhoneNumbers();
            String res = theBlacklistService.whitelist(theList);

            HashMap<String, String> response = new HashMap<>();
            response.put("data", res);
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
