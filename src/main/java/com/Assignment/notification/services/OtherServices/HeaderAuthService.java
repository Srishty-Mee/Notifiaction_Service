package com.Assignment.notification.services.otherServices;

import com.Assignment.notification.customExceptions.AuthenticationException;
import com.Assignment.notification.utils.enums.CustomErrorCodes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class HeaderAuthService implements HandlerInterceptor {

    @Value("${auth_key}")
    private String theAuthKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String authorization = request.getHeader("Auth");
        try {
            if (authorization.contentEquals(theAuthKey)) return true;
        }
        catch (Exception ex)
        {
            throw new AuthenticationException(CustomErrorCodes.AUTH_FAILED, "Authentication Token Missing");

        }
        throw new AuthenticationException( CustomErrorCodes.AUTH_FAILED, "Invalid Authentication Token");

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request,response,handler,modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request,response,handler,ex);
    }
}