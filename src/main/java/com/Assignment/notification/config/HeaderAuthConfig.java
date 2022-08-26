package com.Assignment.notification.config;

import com.Assignment.notification.services.otherServices.HeaderAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Component
public class HeaderAuthConfig extends WebMvcConfigurationSupport {

    @Autowired
    HeaderAuthService theHeaderAuthService;

    @Override
    protected void addInterceptors(InterceptorRegistry registry){
            registry.addInterceptor(theHeaderAuthService);
    }
}