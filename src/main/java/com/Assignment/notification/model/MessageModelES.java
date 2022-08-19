package com.Assignment.notification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;
import java.util.Date;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "sms_services")
@Builder
public class MessageModelES implements Serializable{

    @Id
    private String id;
    private String phoneNumber;
    private String message;
    private int status;
    private int failureCode;
    private String failureComments;
    private Date createdAt;
    private Date updatedAt;

    //private String thirdPartyResponse;
}


