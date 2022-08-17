package com.Assignment.notification.services.ESService;

import com.Assignment.notification.model.MessageModelES;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchPage;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ESService {

    Optional<MessageModelES> getById(String id);
    Page<MessageModelES> getAll();

    List<MessageModelES> getByPhoneNumber(String theNumber1);

    List<MessageModelES> getByPhoneNumberAndBetweenDates(String theNumber, Date date1, Date date2);

    SearchPage<MessageModelES> getByMessage(String phrase);
}
