package com.Assignment.notification.customExceptions;

import com.Assignment.notification.utils.enums.CustomErrorCodes;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@Data
@NoArgsConstructor
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ServiceException extends RuntimeException{

    private CustomErrorCodes errorCode;
    private String message;

    public ServiceException( CustomErrorCodes customCode, String message){
        super();
        this.errorCode = customCode;
        this.message = message;
    }
}
