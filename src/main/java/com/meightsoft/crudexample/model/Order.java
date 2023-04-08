package com.meightsoft.crudexample.model;

import com.meightsoft.crudexample.constants.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;

    private String address;

    private Integer price;

    private OrderStatus status;

    private Restaurant restaurant;
}
