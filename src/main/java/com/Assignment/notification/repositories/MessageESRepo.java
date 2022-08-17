package com.Assignment.notification.repositories;

import com.Assignment.notification.model.MessageModelES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MessageESRepo extends ElasticsearchRepository<MessageModelES, String> {
    List<MessageModelES> findAllByPhoneNumber(String phoneNumber);
    List<MessageModelES> findAllByCreatedAtBetween(Date date1, Date date2);



}
