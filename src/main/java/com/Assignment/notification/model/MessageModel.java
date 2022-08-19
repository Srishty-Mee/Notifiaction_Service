package com.Assignment.notification.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Calendar;
import java.util.Date;
import java.io.Serializable;


@Data
@AllArgsConstructor
@Entity
@Table(name = "sms_requests")
public class MessageModel implements Serializable{
    Logger LOGGER = LoggerFactory.getLogger(MessageModel.class);

    @Id
    @Column(nullable = false)
    private String id;
    private String phoneNumber;
    private String message;
    private int status;
    private int failureCode;
    private String failureComments;


    @JsonFormat(timezone = "dd/MM/yyyy HH:mm")
    private Date createdAt;

    @JsonFormat(timezone = "dd/MM/yyyy HH:mm")
    private Date updatedAt;

    public void setCreatedAt() {
        this.createdAt =Calendar.getInstance().getTime();
    }
    public MessageModel()
    {
        setCreatedAt();
        LOGGER.info(String.valueOf(createdAt));
    }

    //private String thirdPartyResponse;
}
