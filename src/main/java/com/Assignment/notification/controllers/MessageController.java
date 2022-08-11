package com.Assignment.notification.controllers;

import com.Assignment.notification.model.MessageModel;
import com.Assignment.notification.model.MessageRequestModel;
import com.Assignment.notification.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/v1/sms/send")


public class MessageController {

    @Autowired
    private KafkaTemplate<String, String> theKafkaTemplate;

    private static final String TOPIC= "theKafkaTopic";

    @Autowired
    private MessageService theMessageService;


    @PostMapping
    public String sendSMSHandler(@RequestBody MessageRequestModel theRequest)
    {
        theKafkaTemplate.send(TOPIC, "theRequest");
        System.out.println("Kafka tried");
        return theMessageService.sendSMS(theRequest);
    }

    @GetMapping ("/{id}")
    public  Optional<MessageModel> getDetailsHandler(@PathVariable String id)
    {
        return  theMessageService.getDetailsById(id);
    }


}
