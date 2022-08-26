package com.Assignment.notification.model.extSMS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Destination {

    private List<String> msisdn;
    private  String correlationid;
}
