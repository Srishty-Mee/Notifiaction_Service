package com.Assignment.notification.services.kafkaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProducerServiceImpl implements ProducerService {

    Logger LOGGER = LoggerFactory.getLogger(ProducerServiceImpl.class);

    @Autowired
    private KafkaTemplate<String, String>theKafkaTemplate;

    public void publishMessage(String theMessage)
    {
        theKafkaTemplate.send("notification.send_sms", theMessage);
        LOGGER.info("Message Sent -> " + theMessage);
    }
}
