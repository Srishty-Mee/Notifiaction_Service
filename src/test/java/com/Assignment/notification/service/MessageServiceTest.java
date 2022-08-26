package com.Assignment.notification.service;

import com.Assignment.notification.model.MessageModel;
import com.Assignment.notification.model.requests.SendSMSRequestModel;
import com.Assignment.notification.model.response.SMSSucessResponse;
import com.Assignment.notification.repositories.MessageDBRepo;
import com.Assignment.notification.services.blacklistRedisService.BlacklistService;
import com.Assignment.notification.services.kafkaService.ProducerServiceImpl;
import com.Assignment.notification.services.messageService.MessageServiceImpl;
import com.Assignment.notification.services.otherServices.HelperService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;


@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {


    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    public MessageDBRepo userrepository;

    @Mock
    private ProducerServiceImpl theProducerService;

    @Mock
    private BlacklistService theBlacklistService;

    @Mock
    private HelperService theHelperService;

    @InjectMocks
    public MessageServiceImpl underTest;

    @Test
    void sendSMSTest() {
        SendSMSRequestModel map=new SendSMSRequestModel();
        map.setPhoneNumber("+919191919191");
        map.setMessage("Welcome To Meesho!");
        MessageModel user=new MessageModel("5",map.getPhoneNumber(),map.getMessage());
        Mockito.when(userrepository.save(any())).thenReturn(null);
        Mockito.doNothing().when(theProducerService).publishMessage(anyString());
        Mockito.when(theHelperService.validNumber(any())).thenReturn(true);
        SMSSucessResponse res= underTest.sendSMS(map);
        assertEquals(res.getComments(),"Successfully Sent");
    }


    @Test
    void getDetailsByIdTest() {
        MessageModel user = new MessageModel("1", "+919191919191", "Welcome To Meesho!");
        Mockito.when(userrepository.findById("2")).thenReturn(Optional.of(user));
        Optional<MessageModel> res = underTest.getDetailsById("2");
        Mockito.verify(userrepository).findById("2");
    }


}