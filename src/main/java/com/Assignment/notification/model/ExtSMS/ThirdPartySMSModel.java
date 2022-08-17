package com.Assignment.notification.model.ExtSMS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThirdPartySMSModel {

    private String deliveryChannel;
    private Channels channels;
    private List<Destination> destination;

}
