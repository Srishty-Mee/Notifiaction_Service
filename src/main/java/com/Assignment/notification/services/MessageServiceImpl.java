package com.Assignment.notification.services;

import com.Assignment.notification.customExceptions.ServiceException;
import com.Assignment.notification.model.MessageModel;
import com.Assignment.notification.model.requests.SendSMSRequestModel;
import com.Assignment.notification.model.response.SMSSucessResponse;
import com.Assignment.notification.repositories.MessageDBRepo;
import com.Assignment.notification.services.BlacklistRedisService.BlacklistService;
import com.Assignment.notification.services.kafkaService.ProducerServiceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    private MessageDBRepo theMessageDBRepo;

    @Autowired
    private ProducerServiceImpl theProducerService;

    @Autowired
    private BlacklistService theBlacklistService;

    @Autowired
    private HelperService theHelperService;

    @Override
    public SMSSucessResponse sendSMS(SendSMSRequestModel theRequest) {

        //checking the request

        boolean isNumberValid= theHelperService.validNumber(theRequest.getPhoneNumber());
        if(!isNumberValid)
            throw new ServiceException("INVALID_REQUEST", "Invalid Phone Number");

        boolean isNumberBlacklisted= theBlacklistService.checkIfBlackListed(theRequest.getPhoneNumber());
        if(isNumberBlacklisted) throw new ServiceException("INVALID_REQUEST", "Number is blacklisted");

        if(theRequest.getMessage().length()==0) throw new ServiceException("INVALID_REQUEST", "Can not send empty message");

        MessageModel msg= new MessageModel();
        BeanUtils.copyProperties(theRequest, msg);
        msg.setId(UUID.randomUUID().toString());


        try{
            theMessageDBRepo.save(msg);
            theProducerService.publishMessage(msg.getId());

        } catch (Exception theException){
            throw new ServiceException("SERVICE_ERROR", "Message not published by producer");
        }
        return  new SMSSucessResponse(msg.getId(), "Successfully Sent");
    }

    @Override
    public Optional<MessageModel> getDetailsById(String id) {

        try {
            Optional<MessageModel>res= theMessageDBRepo.findById(id);
            if(res.isEmpty())throw new ServiceException("INVALID_ID", "No details for the given id found");
            return  res;
        }
        catch (ServiceException ex){
            throw ex;
        }
        catch (IllegalArgumentException e) {
            throw new ServiceException("INVALID_ID","given id is null " + e.getMessage());
        }
        catch (java.util.NoSuchElementException e) {
            throw new ServiceException("INVALID_ID","given employee id does not exist in DB" + e.getMessage());
        }catch (Exception e) {
            throw new ServiceException("SERVICE_ERROR","Something went wrong in Service layer while fetching all details" + e.getMessage());
        }
    }
}
