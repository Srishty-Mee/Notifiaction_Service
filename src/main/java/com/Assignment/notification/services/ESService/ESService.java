package com.Assignment.notification.services.ESService;

import com.Assignment.notification.model.MessageModelES;
import com.Assignment.notification.model.requests.SearchByDateAndNumberModel;
import com.Assignment.notification.model.requests.SearchByTextModel;
import com.Assignment.notification.model.requests.SendSMSRequestModel;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchPage;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ESService {

    Optional<MessageModelES> getById(String id);
    Page<MessageModelES> getAll();

    List<MessageModelES> getByPhoneNumber(String theNumber1);

    List<MessageModelES> getByPhoneNumberAndBetweenDates(SearchByDateAndNumberModel theRequest) throws Exception;

    List<MessageModelES> findSmsContainsText(SearchByTextModel theRequest) throws Exception;
}
