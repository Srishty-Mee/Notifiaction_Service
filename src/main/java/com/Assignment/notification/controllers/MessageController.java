package com.Assignment.notification.controllers;

import com.Assignment.notification.model.MessageModel;
import com.Assignment.notification.model.requests.SendSMSRequestModel;
import com.Assignment.notification.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@RequestMapping("/v1/sms/send")


public class MessageController {

    @Autowired
    private MessageService theMessageService;


    @PostMapping
    public String SendSMS(@RequestBody SendSMSRequestModel theRequest) {
        String theId = theMessageService.sendSMS(theRequest);
        return theId;
    }

    @GetMapping("/{id}")
    public  Optional<MessageModel> GetDetailsById(@PathVariable String id) {
        return  theMessageService.getDetailsById(id);
    }

}
