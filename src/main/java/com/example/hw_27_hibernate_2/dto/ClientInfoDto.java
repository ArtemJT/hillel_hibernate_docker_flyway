package com.example.hw_27_hibernate_2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientInfoDto {

    private Integer id;
    private String name;
    private String email;
    private String phone;
    private AddressDto addressDto;
}
