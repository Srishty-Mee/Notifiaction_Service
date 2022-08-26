package com.Assignment.notification.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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
@Builder
@Table(name = "sms_requests")
public class MessageModel implements Serializable{
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

    public MessageModel(String id, String phone_number, String message) {
        this.id=id;
        this.phoneNumber=phone_number;
        this.message=message;
    }

    public void setCreatedAt() {
        this.createdAt =Calendar.getInstance().getTime();
    }
    public MessageModel()
    {
        setCreatedAt();
    }

    //private String thirdPartyResponse;
}
