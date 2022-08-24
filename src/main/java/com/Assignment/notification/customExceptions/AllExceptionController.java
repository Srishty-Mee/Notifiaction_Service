package com.Assignment.notification.customExceptions;

import com.Assignment.notification.model.response.Error;
import com.Assignment.notification.model.response.ErrorResponse;
import com.Assignment.notification.utils.enums.CustomErrorCodes;
import org.apache.kafka.common.errors.InvalidRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
@RestController
public class AllExceptionController extends RuntimeException{


    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(NotFoundException ex, WebRequest request){
        ErrorResponse exceptionResponse = new ErrorResponse(new Error(ex.getErrorCode().toString(),ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<Object> handleServiceUnavailableException(ServiceException ex, WebRequest request){
        ErrorResponse exceptionResponse = new ErrorResponse(new Error(ex.getErrorCode().toString(),ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }


    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleInvalidRequestException(BadRequestException ex, WebRequest request){
        ErrorResponse exceptionResponse = new ErrorResponse(new Error(ex.getErrorCode().toString(),ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public final ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest request){
        ErrorResponse exceptionResponse = new ErrorResponse(new Error(ex.getErrorCode().toString(),ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(InvalidRequestException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(InvalidRequestException ex, WebRequest request) {
        String details= ex.getMessage();
        ErrorResponse error = new ErrorResponse(new Error(CustomErrorCodes.INVALID_REQUEST.toString(), details));
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Object> handleValidatorException(MethodArgumentNotValidException ex, WebRequest request){
        ErrorResponse error = new ErrorResponse(
                new Error(CustomErrorCodes.INVALID_ARGUMENT.toString(),ex.getFieldError().getDefaultMessage()));
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request){
        ErrorResponse exceptionResponse = new ErrorResponse(new Error(HttpStatus.BAD_REQUEST.toString(),ex.getMessage()));
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


}
