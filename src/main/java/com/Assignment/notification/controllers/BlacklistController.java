package com.Assignment.notification.controllers;

import com.Assignment.notification.customExceptions.ServiceException;
import com.Assignment.notification.model.response.Error;
import com.Assignment.notification.model.response.ErrorResponse;
import com.Assignment.notification.services.BlacklistRedisService.BlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> GetAllBlacklistNumbers()
    {
        try {
            Set res= theBlacklistService.getAll();

            HashMap<String,Set>response= new HashMap<>();
            response.put("data", res);

            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (ServiceException ex)
        {
            ErrorResponse theError= new ErrorResponse(new Error(ex.getErrorCode(), ex.getErrorMessage()));
            return new ResponseEntity<>(theError, HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex)
        {
            ErrorResponse theError= new ErrorResponse(new Error(ex.getMessage(),"Something went wrong in controller"));
            return new ResponseEntity<>(theError, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping
    public ResponseEntity<?> AddToBlacklist (@RequestBody HashMap<String, List<String>> theRequest)
    {
        List<String>theList= theRequest.get("phoneNumbers");
        try{
            String res= theBlacklistService.addToBlacklist(theList);

            HashMap<String,String>response= new HashMap<>();
            response.put("data", res);
            return new ResponseEntity<>(response, HttpStatus.OK);

        }
        catch (ServiceException ex)
        {
            ErrorResponse theError= new ErrorResponse(new Error(ex.getErrorCode(), ex.getErrorMessage()));
            return new ResponseEntity<>(theError, HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex)
        {
            ErrorResponse theError= new ErrorResponse(new Error(ex.getMessage(),"Something went wrong in controller"));
            return new ResponseEntity<>(theError, HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping
    public ResponseEntity<?> WhiteList(@RequestBody HashMap<String, List<String>> theRequest)
    {
        try {
            List<String>theList= theRequest.get("phoneNumbers");
            String res= theBlacklistService.whitelist(theList);

            HashMap<String,String>response= new HashMap<>();
            response.put("data", res);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (ServiceException ex)
        {
            ErrorResponse theError= new ErrorResponse(new Error(ex.getErrorCode(), ex.getErrorMessage()));
            return new ResponseEntity<>(theError, HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex)
        {
            ErrorResponse theError= new ErrorResponse(new Error(ex.getMessage(),"Something went wrong in controller"));
            return new ResponseEntity<>(theError, HttpStatus.BAD_REQUEST);
        }

    }

}
