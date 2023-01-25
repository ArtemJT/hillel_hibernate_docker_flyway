package com.example.hw_27_hibernate_2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    private Integer id;
    private String name;
    private String email;
    private String phone;
    private AddressDto addressDto;
    private List<OrderDto> orderDtoList;
}
