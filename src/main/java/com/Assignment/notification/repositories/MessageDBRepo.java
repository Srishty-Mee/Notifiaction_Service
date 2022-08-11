package com.Assignment.notification.repositories;

import com.Assignment.notification.model.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageDBRepo  extends JpaRepository<MessageModel,String> {

}