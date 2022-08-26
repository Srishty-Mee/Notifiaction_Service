package com.Assignment.notification.utils.thirdPartyAPI;

import com.Assignment.notification.model.extSMS.Channels;
import com.Assignment.notification.model.extSMS.Destination;
import com.Assignment.notification.model.extSMS.SMS;
import com.Assignment.notification.model.extSMS.ThirdPartySMSModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IMISMSBuilder {

    public ThirdPartySMSModel buildExternalRequestModel(String theId, String theNumber, String theMessage)
    {
        String theDeliveryChannel="sms";

        SMS theSMS= new SMS(theMessage);
        Channels theChannels= new Channels(theSMS);

        List<String>theMsisdnList = new ArrayList<String>();
        theMsisdnList.add(theNumber);
        Destination theDestination = new Destination(theMsisdnList, theId);

        List<Destination> theDestinations= new ArrayList<Destination>();
        theDestinations.add(theDestination);

        ThirdPartySMSModel theThirdPartyModel= new ThirdPartySMSModel(theDeliveryChannel, theChannels, theDestinations);

        return  theThirdPartyModel;

    }


}
