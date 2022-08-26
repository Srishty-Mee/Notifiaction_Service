package com.Assignment.notification.services.esService;

import com.Assignment.notification.model.MessageModelES;
import com.Assignment.notification.model.requests.SearchByDateAndNumberModel;
import com.Assignment.notification.model.requests.SearchByTextModel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ESService {

    Optional<MessageModelES> getById(String id);
    Page<MessageModelES> getAll();

    List<MessageModelES> getByPhoneNumberAndBetweenDates(SearchByDateAndNumberModel theRequest) throws Exception;

    List<MessageModelES> getByText(SearchByTextModel theRequest) throws Exception;
}
