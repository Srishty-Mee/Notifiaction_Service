package com.Assignment.notification.repositories;

import com.Assignment.notification.model.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageDBRepo extends JpaRepository<MessageModel,String> {

}