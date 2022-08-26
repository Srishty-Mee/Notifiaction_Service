package com.Assignment.notification.utils.thirdPartyAPI;

import com.Assignment.notification.model.extSMS.ThirdPartySMSModel;
import com.Assignment.notification.model.response.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class IMISMSSender {

    @Value("${external_api_url}")
    private String theURL;

    @Autowired
    private IMISMSBuilder theSMSBuilder;

    public RestTemplate theRestTemplate= new RestTemplateBuilder()
            .defaultHeader("key","93ceffda-5941-11ea-9da9-025282c394f2")
            .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .build();

    public String ExternalAPICall(String theId, String thePhoneNumber, String theMessage){

        ThirdPartySMSModel theSMSModel = theSMSBuilder.buildExternalRequestModel(theId,thePhoneNumber, theMessage );

        try{

            String response = theRestTemplate.postForObject(theURL,theSMSModel,String.class);

            ObjectMapper theMapper = new ObjectMapper();

            JsonNode rootNode = theMapper.readTree(response);
            JsonNode responseNode = rootNode.path("response");
            JsonNode transId = responseNode.path("transid");

            if(transId.toString().length()>0){
                return theMapper.treeToValue(responseNode, Response.class).toString();
            }else
            {
                return responseNode.get(0).toString();
            }
        } catch (Exception ex){ex.printStackTrace();}

        return "External API Failure";
    }

}
