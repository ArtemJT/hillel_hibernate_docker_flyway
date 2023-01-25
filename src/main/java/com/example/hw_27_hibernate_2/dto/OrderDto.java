package com.example.hw_27_hibernate_2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Integer id;
    private List<OrderItemDto> orderItemDtoList;
}
