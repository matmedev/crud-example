package com.meightsoft.crudexample.model;

import com.meightsoft.crudexample.constants.OrderStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotNull
    private String address;

    @NotNull
    @Positive
    private Integer price;

    private OrderStatus status;

    private Restaurant restaurant;
}
