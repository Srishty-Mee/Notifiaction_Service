package com.Assignment.notification.utils.enums;

public enum CustomErrorCodes {

    BLACKLIST("Blacklisted Number"),
    AUTH_FAILED("authentication failed"),
    INVALID_REQUEST(" heck your request"),
    KAFKA_ERROR("problem in kafka"),
    NO_DATA_FOUND("check page number"),
    REDIS_ERROR("error in redis cache"),
    EMPTY_LIST("the input list is empty"),
    INVALID_ID("check the id"),
    INVALID_ARGUMENT("check the arguments"),
    ES_ERROR("error in elasticsearch"),
    SERVICE_ERROR("some service layer error"),
    DATABASE_ERROR("error in database")    ;


    private String message;

    CustomErrorCodes(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}
