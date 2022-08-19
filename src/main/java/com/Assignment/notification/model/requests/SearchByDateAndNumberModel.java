package com.Assignment.notification.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchByDateAndNumberModel {

    String startDate;
    String endDate;
    String phoneNumber;
    int pageNo;
}
