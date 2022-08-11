package com.Assignment.notification.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sms_requests")
public class MessageModel {

    @Id
    @Column(nullable = false)
    private String id;
    private String phoneNumber;
    private String message;
    private int status;
    private int failureCode;
    private String failureComments;
    private Date createdAt=new Date();
    private Date updatedAt;

    //private String thirdPartyResponse;
}
