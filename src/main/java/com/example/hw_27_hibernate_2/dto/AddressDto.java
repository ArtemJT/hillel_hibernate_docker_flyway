package com.example.hw_27_hibernate_2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private Integer id;
    private String country;
    private String city;
    private String street;
    private String house;
}
