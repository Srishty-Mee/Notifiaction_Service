package com.Assignment.notification.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequestModel {

    @NonNull
    private String phoneNumber;

    @NonNull
    private String message;

}
