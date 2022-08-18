package com.Assignment.notification.services.kafkaService;

import com.Assignment.notification.model.MessageModel;
import com.Assignment.notification.model.MessageModelES;
import com.Assignment.notification.repositories.MessageDBRepo;
import com.Assignment.notification.repositories.MessageESRepo;
import com.Assignment.notification.services.RedisService;
import com.Assignment.notification.utils.thirdPartyAPI.IMISMSSender;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsumerServiceImpl implements ConsumerService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ConsumerServiceImpl.class);

    @Autowired
    private MessageDBRepo theMessageDBRepo;

    @Autowired
    private RedisService theRedisService;

    @Autowired
    private MessageESRepo theESRepo;

    @Autowired
    private IMISMSSender theSMSService;

    @KafkaListener(topics = "notification.send_sms", groupId = "myGroup")
    public  void consumeMessage(String theId){

        LOGGER.info("Message Received -> " + theId);
        MessageModel theMessageModel= theMessageDBRepo.findById(theId).orElse(null);

        if(theMessageModel==null){
            LOGGER.info("null id received by consumer");
        }
        else if(theRedisService.CheckIfBlacklistedRedis(theMessageModel.getPhoneNumber())){
            LOGGER.info("BlackListed Number");
        }
        else
        {
            try {

                //3rd party API Call
                String response = theSMSService.ExternalAPICall
                        (theMessageModel.getId(), theMessageModel.getPhoneNumber(), theMessageModel.getMessage());
                LOGGER.info(response);

                //Database Update
                theMessageModel.setStatus(200);
                theMessageDBRepo.save(theMessageModel);
                LOGGER.info("Updated in Database");


                //Elasticsearch Update
                MessageModelES theESMessage= new MessageModelES();
                BeanUtils.copyProperties(theMessageModel, theESMessage);
                MessageModelES temp= theESRepo.save(theESMessage);
                LOGGER.info("Message saved in ES with below id");
                LOGGER.info(theESMessage.getId());

            }
            catch(Exception ex){
//                theMessageModel.setStatus(400);
//                theMessageDBRepo.save(theMessageModel);
//                ex.printStackTrace();
            }

        }

    }
}
