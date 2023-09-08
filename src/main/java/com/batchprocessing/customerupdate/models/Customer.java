package com.batchprocessing.customerupdate.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private Long id;

    private String firstName;

    private String lastName;

    private Long dataUsage;

    private Long minutes;

    private Double billAmount;


}
