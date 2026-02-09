package com.evergreen.lib.dtos.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private Integer id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String city;
    private String country;
    private int age;

}
