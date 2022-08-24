package com.Assignment.notification.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SearchByDateAndNumberModel {

    @NotNull(message = "Enter a valid start date.")
    String startDate;

    @NotNull(message = "Enter a valid end date.")
    String endDate;

    @NotNull(message = "Enter a valid phone number.")
    String phoneNumber;

    @Min(value = 1,message="Enter a valid page Number")
    int pageNo;
}
