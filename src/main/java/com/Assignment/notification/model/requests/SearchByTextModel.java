package com.Assignment.notification.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchByTextModel {

    @NotNull(message = "Enter a valid search text.")
    String text;

    @Min(value = 1, message = "Enter a valid page number.")
    int pageNo;
}
