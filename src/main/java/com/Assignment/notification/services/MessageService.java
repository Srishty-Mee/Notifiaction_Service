package com.Assignment.notification.services;

import com.Assignment.notification.model.MessageModel;
import com.Assignment.notification.model.MessageRequestModel;

import java.util.Optional;

public interface MessageService {

    public String sendSMS(MessageRequestModel theRequest);

    Optional<MessageModel> getDetailsById(String id);
}
