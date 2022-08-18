package com.Assignment.notification.services;

import com.Assignment.notification.model.MessageModel;
import com.Assignment.notification.model.requests.SendSMSRequestModel;

import java.util.Optional;

public interface MessageService {

    public String sendSMS(SendSMSRequestModel theRequest);

    Optional<MessageModel> getDetailsById(String id);
}
