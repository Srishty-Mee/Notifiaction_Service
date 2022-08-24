package com.Assignment.notification.controllers;

import com.Assignment.notification.model.MessageModel;
import com.Assignment.notification.model.requests.SendSMSRequestModel;

import com.Assignment.notification.model.response.SMSSucessResponse;
import com.Assignment.notification.services.MessageService.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/v1/sms")


public class MessageController {

    @Autowired
    private MessageService theMessageService;

    Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @PostMapping("/send")
    public ResponseEntity<?> SendSMS(@RequestBody @Valid SendSMSRequestModel theRequest) {
            SMSSucessResponse res = theMessageService.sendSMS(theRequest);

            HashMap<String,SMSSucessResponse> response= new HashMap<>();
            response.put("data", res);
            LOGGER.info("Message sent from controller");
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetDetailsById(@PathVariable String id) {
            LOGGER.info("searching for the id: "+  id);
            Optional<MessageModel> res=  theMessageService.getDetailsById(id);

            HashMap<String,Optional<MessageModel>> response= new HashMap<>();
            response.put("data", res);

            return  new ResponseEntity<>(response, HttpStatus.OK);

    }

}
