package com.Assignment.notification.controllers;


import com.Assignment.notification.model.MessageModelES;
import com.Assignment.notification.services.ESService.ESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/search")
public class ESController {

    @Autowired
    private ESService messageESService;

    @GetMapping
    public Page<MessageModelES> getAll() {
        return messageESService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<MessageModelES> getById(@PathVariable String id) {
        try {
            if (!messageESService.getById(id).isPresent()) {
            }
            return messageESService.getById(id);
        } catch (Exception ex) {
            return  null;
        }
    }

    @GetMapping("/number/{number}")
    public List<MessageModelES> getByNumber(@PathVariable String number) {
        try {
            return messageESService.getByPhoneNumber(number);
        } catch (Exception ex) {
            return  null;
        }
    }
    @GetMapping("/message/{theMessage}")
    public SearchPage<MessageModelES> getByMessage(@PathVariable String theMessage) {
        try {
            return messageESService.getByMessage(theMessage);
        } catch (Exception ex) {
            return  null;
        }
    }
}
