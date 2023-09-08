package com.batchprocessing.customerupdate.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

        private Long id;

        private String firstName;

        private String lastName;

        private Long minutes;

        private Long dataUsage;

}