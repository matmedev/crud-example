package com.meightsoft.crudexample.model.graphql;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Restaurant {

    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    private String phoneNumber;

    private Boolean isVegan = false;

    private Boolean isOnWolt = false;

    private List<Order> orders;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
