package com.Assignment.notification.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendSMSRequestModel {

    @NotNull(message = "Enter a valid phone number.")
    private String phoneNumber;

    @NotNull(message = "Enter a valid message.")
    private String message;

}
