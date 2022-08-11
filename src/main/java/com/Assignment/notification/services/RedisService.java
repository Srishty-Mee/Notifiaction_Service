package com.Assignment.notification.services;

public interface RedisService {

    public Boolean CheckIfBlacklisted(String thePhoneNumber);
}
