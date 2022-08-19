package com.Assignment.notification.services;

import com.Assignment.notification.model.MessageModel;
import com.Assignment.notification.model.requests.SendSMSRequestModel;
import com.Assignment.notification.model.response.SMSSucessResponse;

import java.util.List;
import java.util.Optional;

public interface MessageService {

    public SMSSucessResponse sendSMS(SendSMSRequestModel theRequest);

    Optional<MessageModel> getDetailsById(String id);
}
