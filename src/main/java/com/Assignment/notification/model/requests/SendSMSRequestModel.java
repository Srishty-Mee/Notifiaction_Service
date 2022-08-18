package com.Assignment.notification.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendSMSRequestModel {

    @NonNull
    private String phoneNumber;

    @NonNull
    private String message;

}
