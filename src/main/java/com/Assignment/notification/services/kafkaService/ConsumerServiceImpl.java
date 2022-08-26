package com.Assignment.notification.services.kafkaService;

import com.Assignment.notification.customExceptions.BadRequestException;
import com.Assignment.notification.customExceptions.NotFoundException;
import com.Assignment.notification.customExceptions.ServiceException;
import com.Assignment.notification.model.MessageModel;
import com.Assignment.notification.model.MessageModelES;
import com.Assignment.notification.repositories.MessageDBRepo;
import com.Assignment.notification.repositories.MessageESRepo;
import com.Assignment.notification.utils.enums.CustomErrorCodes;
import com.Assignment.notification.utils.thirdPartyAPI.IMISMSSender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
public class ConsumerServiceImpl implements ConsumerService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ConsumerServiceImpl.class);

    @Autowired
    private MessageDBRepo theMessageDBRepo;

    @Autowired
    private MessageESRepo theESRepo;

    @Autowired
    private IMISMSSender theSMSService;

    @Override
    @KafkaListener(topics = "notification.send_sms", groupId = "myGroup")
    public  void consumeMessage(String theId) throws BadRequestException, ServiceException, NotFoundException{

        LOGGER.info("Consumer received message with id  -> " + theId);
        MessageModel theMessageModel= theMessageDBRepo.findById(theId).orElse(null);

        if(theMessageModel==null){
            throw new ServiceException(CustomErrorCodes.KAFKA_ERROR, "Id not received by consumer.");
        }
        else
        {
            // 3rd party API Call
            try {

                String response = theSMSService.ExternalAPICall
                        (theMessageModel.getId(), theMessageModel.getPhoneNumber(), theMessageModel.getMessage());
                LOGGER.info("third Part Response " + response);
            }
            catch (Exception ex) {throw  ex;}

            //Database Update
            try {
                theMessageModel.setStatus(200);
                theMessageDBRepo.save(theMessageModel);
                LOGGER.info("Updated in Database");
            }catch (Exception ex) {
                throw new ServiceException( CustomErrorCodes.DATABASE_ERROR, "Status not updated in Database");
            }

            //Elasticsearch Update
            try{

                MessageModelES theESMessage= new MessageModelES();
                BeanUtils.copyProperties(theMessageModel, theESMessage);
                MessageModelES temp= theESRepo.save(theESMessage);
                LOGGER.info("Message saved in ES with below id");
                LOGGER.info(theESMessage.getId());

            }
            catch(Exception ex){
                //throw new ServiceException( CustomErrorCodes.ES_ERROR, "Message not saved in Elastic Search.");
            }

        }

    }
}
