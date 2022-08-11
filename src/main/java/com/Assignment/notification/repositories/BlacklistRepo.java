package com.Assignment.notification.repositories;

import com.Assignment.notification.model.BlacklistModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistRepo extends JpaRepository<BlacklistModel,String> {


}
