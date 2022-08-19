package com.Assignment.notification.repositories;

import com.Assignment.notification.model.MessageModelES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MessageESRepo extends ElasticsearchRepository<MessageModelES, String> {

}
