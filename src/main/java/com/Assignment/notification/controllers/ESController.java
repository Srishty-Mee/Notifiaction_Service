package com.Assignment.notification.controllers;

import com.Assignment.notification.model.MessageModelES;
import com.Assignment.notification.model.requests.SearchByDateAndNumberModel;
import com.Assignment.notification.model.requests.SearchByTextModel;

import com.Assignment.notification.services.esService.ESService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/search")
public class ESController {

    @Autowired
    private ESService messageESService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        Page<MessageModelES> res= messageESService.getAll();

        HashMap<String,Page<MessageModelES>> response= new HashMap<>();
        response.put("data", res);

        return  new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {

        Optional<MessageModelES> res= messageESService.getById(id);

        HashMap<String,Optional<MessageModelES>> response= new HashMap<>();
        response.put("data", res);

        return  new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/numberAndDate")
    public ResponseEntity<?> getByNumberAndDate(@RequestBody @Valid SearchByDateAndNumberModel theRequest) throws Exception {

        List<MessageModelES> res = messageESService.getByPhoneNumberAndBetweenDates(theRequest);

        HashMap<String, List<MessageModelES>> response = new HashMap<>();
        response.put("data", res);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/text")
    public ResponseEntity<?> getByMessage(@RequestBody @Valid SearchByTextModel theRequest) throws Exception {
            List<MessageModelES> res =messageESService.getByText(theRequest);

            HashMap<String,List<MessageModelES>> response= new HashMap<>();
            response.put("data", res);

            return  new ResponseEntity<>(response, HttpStatus.OK);

    }
}
