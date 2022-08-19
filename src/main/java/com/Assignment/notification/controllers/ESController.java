package com.Assignment.notification.controllers;

import com.Assignment.notification.customExceptions.ServiceException;
import com.Assignment.notification.model.MessageModelES;
import com.Assignment.notification.model.requests.SearchByDateAndNumberModel;
import com.Assignment.notification.model.requests.SearchByTextModel;
import com.Assignment.notification.model.response.Error;
import com.Assignment.notification.model.response.ErrorResponse;
import com.Assignment.notification.services.ESService.ESService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        try {
            Page<MessageModelES> res= messageESService.getAll();

            HashMap<String,Page<MessageModelES>> response= new HashMap<>();
            response.put("data", res);

            return  new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (ServiceException ex) {
            ErrorResponse theError= new ErrorResponse(new Error(ex.getErrorCode(), ex.getErrorMessage()));
            return new ResponseEntity<>(theError, HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex) {
            ErrorResponse theError = new ErrorResponse(new Error("612","Something went wrong in controller"));
            return new ResponseEntity<>(theError, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
            try {
                Optional<MessageModelES> res= messageESService.getById(id);

                HashMap<String,Optional<MessageModelES>> response= new HashMap<>();
                response.put("data", res);

                return  new ResponseEntity<>(response, HttpStatus.OK);
            }
            catch (ServiceException ex) {
                ErrorResponse theError= new ErrorResponse(new Error(ex.getErrorCode(), ex.getErrorMessage()));
                return new ResponseEntity<>(theError, HttpStatus.BAD_REQUEST);
            }
            catch (Exception ex) {
                ErrorResponse theError = new ErrorResponse(new Error("612","Something went wrong in controller"));
                return new ResponseEntity<>(theError, HttpStatus.BAD_REQUEST);
            }
    }

    @GetMapping("/numberAndDate")
    public ResponseEntity<?> getByNumberAndDate(@RequestBody SearchByDateAndNumberModel theRequest) {
        try {
            List<MessageModelES> res= messageESService.getByPhoneNumberAndBetweenDates(theRequest);

            HashMap<String,List<MessageModelES>> response= new HashMap<>();
            response.put("data", res);

            return  new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (ServiceException ex) {
            ErrorResponse theError= new ErrorResponse(new Error(ex.getErrorCode(), ex.getErrorMessage()));
            return new ResponseEntity<>(theError, HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex) {
            ErrorResponse theError = new ErrorResponse(new Error("612","Something went wrong in controller"));
            return new ResponseEntity<>(theError, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/text")
    public ResponseEntity<?> getByMessage(@RequestBody SearchByTextModel theRequest) {
        try {
            List<MessageModelES> res =messageESService.findSmsContainsText(theRequest);

            HashMap<String,List<MessageModelES>> response= new HashMap<>();
            response.put("data", res);

            return  new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (ServiceException ex) {
            ErrorResponse theError= new ErrorResponse(new Error(ex.getErrorCode(), ex.getErrorMessage()));
            return new ResponseEntity<>(theError, HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            ErrorResponse theError = new ErrorResponse(new Error("612","Something went wrong in controller"));
            return new ResponseEntity<>(theError, HttpStatus.BAD_REQUEST);
        }
    }
}
