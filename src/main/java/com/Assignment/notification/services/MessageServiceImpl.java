package com.Assignment.notification.services;

import com.Assignment.notification.model.MessageModel;
import com.Assignment.notification.model.MessageRequestModel;
import com.Assignment.notification.repositories.MessageDBRepo;
import com.Assignment.notification.services.kafkaService.ProducerServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
@Primary
public class MessageServiceImpl implements MessageService{

    @Autowired
    private MessageDBRepo theMessageDBRepo;

    @Autowired
    private ProducerServiceImpl theProducerService;

    @Override
    public String sendSMS(MessageRequestModel theRequest) {

        MessageModel msg= new MessageModel();
        BeanUtils.copyProperties(theRequest, msg);
        msg.setId(UUID.randomUUID().toString());

        try{
            //theMessageDBRepo.save(msg);
            theProducerService.publishMessage(msg.getId());
        } catch (Exception theException){
            System.out.println("not published");
//            throw new ServiceUnavailableException(theException.getMessage(), ErrorCodes.SERVICE_UNAVAILABLE_ERROR);
        }
        return msg.getId();
    }

    @Override
    public Optional<MessageModel> getDetailsById(String id) {

        return theMessageDBRepo.findById(id);
    }
}
