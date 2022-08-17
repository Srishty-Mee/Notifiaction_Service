package com.Assignment.notification.services.kafkaService;

import com.Assignment.notification.model.MessageModel;
import com.Assignment.notification.model.MessageModelES;
import com.Assignment.notification.repositories.MessageDBRepo;
import com.Assignment.notification.repositories.MessageESRepo;
import com.Assignment.notification.services.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

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
                MessageModelES theESMessage= new MessageModelES();
                BeanUtils.copyProperties(theMessageModel, theESMessage);
                //theMessageDBRepo.save(theMessageModel);
                System.out.println("Message saved in DB");
                theESRepo.save(theESMessage);
                System.out.println("Message saved in ES with below id");
                System.out.println(theESMessage.getId());

            }
            catch(Exception ex){
                System.out.println(ex);
            }

        }

    }
}
