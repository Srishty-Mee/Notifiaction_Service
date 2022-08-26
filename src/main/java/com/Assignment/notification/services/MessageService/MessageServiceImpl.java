package com.Assignment.notification.services.messageService;

import com.Assignment.notification.customExceptions.BadRequestException;
import com.Assignment.notification.customExceptions.NotFoundException;
import com.Assignment.notification.customExceptions.ServiceException;
import com.Assignment.notification.model.MessageModel;
import com.Assignment.notification.model.requests.SendSMSRequestModel;
import com.Assignment.notification.model.response.SMSSucessResponse;
import com.Assignment.notification.repositories.MessageDBRepo;
import com.Assignment.notification.services.blacklistRedisService.BlacklistService;
import com.Assignment.notification.services.otherServices.HelperService;
import com.Assignment.notification.services.kafkaService.ProducerServiceImpl;

import com.Assignment.notification.utils.enums.CustomErrorCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDBRepo theMessageDBRepo;

    @Autowired
    private ProducerServiceImpl theProducerService;

    @Autowired
    private BlacklistService theBlacklistService;

    @Autowired
    private HelperService theHelperService;

    private static final Logger LOGGER =
            LoggerFactory.getLogger(MessageServiceImpl.class);

    @Override
    public SMSSucessResponse sendSMS(SendSMSRequestModel theRequest) {

        //////////checking the request

        if(theHelperService.validNumber(theRequest.getPhoneNumber())==false) {
            throw new BadRequestException(CustomErrorCodes.INVALID_REQUEST, "Invalid Phone Number");
        }

        if(theRequest.getMessage().length()==0)
            throw new BadRequestException(CustomErrorCodes.INVALID_REQUEST, "Empty message can not be sent.");

        boolean isNumberBlacklisted= theBlacklistService.checkIfBlackListed(theRequest.getPhoneNumber());
        if(isNumberBlacklisted)
            throw new BadRequestException(CustomErrorCodes.INVALID_REQUEST, "Number is blacklisted");


        ////////////////////////

        MessageModel msg= new MessageModel();
        BeanUtils.copyProperties(theRequest, msg);
        msg.setId(UUID.randomUUID().toString());


        try {theMessageDBRepo.save(msg);}
        catch (Exception ex) {
            throw new ServiceException(CustomErrorCodes.DATABASE_ERROR, "Details not updated in database.");
        }
        theProducerService.publishMessage(msg.getId());
        LOGGER.info("Message published from message service");


        return  new SMSSucessResponse(msg.getId(), "Successfully Sent");
    }

    @Override
    public Optional<MessageModel> getDetailsById(String id) {
        Optional<MessageModel> res;
        try {
            res = theMessageDBRepo.findById(id);
        } catch (Exception ex) {
            throw new ServiceException(CustomErrorCodes.SERVICE_ERROR, "Could not fetch details from DB " + ex.getMessage());
        }
        if (res.isEmpty())
            throw new NotFoundException(CustomErrorCodes.INVALID_ID, "No details for the given id found");
        return res;
    }
}
