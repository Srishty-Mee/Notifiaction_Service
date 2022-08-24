package com.Assignment.notification.services.kafkaService;

import com.Assignment.notification.customExceptions.BadRequestException;
import com.Assignment.notification.customExceptions.NotFoundException;
import com.Assignment.notification.customExceptions.ServiceException;

public interface ConsumerService {
    void consumeMessage(String theId) throws BadRequestException, ServiceException, NotFoundException;
}
