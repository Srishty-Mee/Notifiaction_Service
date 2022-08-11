package com.Assignment.notification.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseModel {
    private Date time;
    private int code;
    private String message;
    private String details;
}
