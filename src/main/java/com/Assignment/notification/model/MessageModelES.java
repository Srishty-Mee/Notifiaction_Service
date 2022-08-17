package com.Assignment.notification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "sms_services")
@Setting(settingPath = "static/es-settings.json")
@Builder
public class MessageModelES implements Serializable{

    @Id
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


