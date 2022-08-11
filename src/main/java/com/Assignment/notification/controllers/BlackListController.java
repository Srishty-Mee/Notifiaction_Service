package com.Assignment.notification.controllers;

import com.Assignment.notification.model.BlackListModel;
import com.Assignment.notification.repositories.BlackListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/v1/blacklist/redis")
public class BlackListController
{

    @Autowired
    private BlackListRepo theRepo;


    @GetMapping
    public List<BlackListModel> findAllBlackListRedis()
    {
        return theRepo.findAll();
    }


    @PostMapping
    public  List<String> saveRedis (@RequestBody HashMap<String, List<String>> theList)
    {
        return theRepo.save(theList.get("phoneNumbers"));
    }

    @GetMapping("/{theNumber}")
    public  BlackListModel checkRedis (@PathVariable String theNumber)
    {
        return theRepo.check(theNumber);
    }

    @DeleteMapping
    public String deleteRedis(@RequestBody HashMap<String, List<String>> theList)
    {
        return theRepo.deleteNumber(theList.get("phoneNumbers"));
    }

}
