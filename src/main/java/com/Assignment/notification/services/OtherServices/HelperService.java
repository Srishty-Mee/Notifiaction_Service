package com.Assignment.notification.services.OtherServices;

import org.springframework.stereotype.Service;

@Service
public class HelperService {

    public boolean validNumber(String theString)
    {
        if(theString.length()!=13)return  false;
        else {
            if(theString.charAt(0)!='+' || theString.charAt(1)!='9' || theString.charAt(2)!='1')return  false;
            for (int i = 3; i <13 ; i++) {
                if(theString.charAt(i)>'9' || theString.charAt(i)<'0')return  false;
            }return true;
        }
    }

}
