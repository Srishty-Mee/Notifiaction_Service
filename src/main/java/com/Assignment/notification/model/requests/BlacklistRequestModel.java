package com.Assignment.notification.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlacklistRequestModel {

    @NotNull(message = "Enter a valid list of phone numbers.")
    List<String> phoneNumbers;
}
