package com.Assignment.notification.controllers;

import com.Assignment.notification.customExceptions.ServiceException;
import com.Assignment.notification.model.MessageModel;
import com.Assignment.notification.model.requests.SendSMSRequestModel;
import com.Assignment.notification.model.response.Error;
import com.Assignment.notification.model.response.ErrorResponse;
import com.Assignment.notification.model.response.SMSSucessResponse;
import com.Assignment.notification.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/v1/sms")


public class MessageController {

    @Autowired
    private MessageService theMessageService;


    @PostMapping("/send")
    public ResponseEntity<?> SendSMS(@RequestBody SendSMSRequestModel theRequest) {
        try{
            SMSSucessResponse res = theMessageService.sendSMS(theRequest);
            HashMap<String,SMSSucessResponse> response= new HashMap<>();
            response.put("data", res);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (ServiceException ex){
            ErrorResponse theError= new ErrorResponse(new Error(ex.getErrorCode(), ex.getErrorMessage()));
            return new ResponseEntity<>(theError, HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex){
            ErrorResponse theError = new ErrorResponse(new Error("612","Something went wrong in controller"));
            return new ResponseEntity<>(theError, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetDetailsById(@PathVariable String id) {
        try {
            Optional<MessageModel> res=  theMessageService.getDetailsById(id);

            HashMap<String,Optional<MessageModel>> response= new HashMap<>();
            response.put("data", res);

            return  new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (ServiceException e) {
            ErrorResponse theError= new ErrorResponse(new Error(e.getErrorCode(), e.getErrorMessage()));
            return new ResponseEntity<>(theError, HttpStatus.BAD_REQUEST);

        }catch (Exception e) {
            ErrorResponse theError = new ErrorResponse(new Error("612","Something went wrong in controller"));
            return new ResponseEntity<>(theError, HttpStatus.BAD_REQUEST);
        }

    }

}
