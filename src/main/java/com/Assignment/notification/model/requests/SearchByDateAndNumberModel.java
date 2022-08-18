package com.Assignment.notification.model.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchByDateAndNumberModel {

    @NonNull
    String startDate;
    @NotNull
    String endDate;
    @NonNull
    String phoneNumber;
    @NonNull
    int pageNo;
}
