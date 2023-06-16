package com.meightsoft.crudexample.model;

import com.meightsoft.crudexample.constants.OrderStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String id;

    @NotNull
    private String address;

    @NotNull
    @Positive
    private Integer price;

    private OrderStatus status;

    private Restaurant restaurant;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
