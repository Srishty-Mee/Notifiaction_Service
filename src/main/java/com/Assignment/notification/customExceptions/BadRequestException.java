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
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{
    private CustomErrorCodes errorCode;
    private String message;

    public BadRequestException( CustomErrorCodes errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message=message;
    }

}
